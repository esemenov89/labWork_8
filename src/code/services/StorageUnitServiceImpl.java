package code.services;

import code.model.dao.StorageUnitRepository;
import code.model.dto.ElcatalogDTO;
import code.model.hibernate.ElcatalogEntity;
import code.model.pojo.NewStorageUnit;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Service for working with storage units
 */
@Service
@Transactional
public class StorageUnitServiceImpl implements StorageUnitService {

    private MapperFacade mapperBooks;
    private StorageUnitRepository bookRepository;

    @Autowired
    public void setBookRepository(StorageUnitRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setMapperFacade(MapperFacade mapperFacade) {
        this.mapperBooks = mapperFacade;
    }
    /**
     *
     * @return all storage units from database
     */
    @Override
    public ArrayList<ElcatalogDTO> getAllStorageUnits(){
        ArrayList<ElcatalogDTO> users = null;
        users=(ArrayList)(mapperBooks.mapAsList(bookRepository.findAll(),ElcatalogDTO.class));
        return users;
    }

    /**
     *
     * @param isn - identifier of storage unit
     * @return storage unit with isn identifier from database
     */
    @Override
    public ElcatalogDTO getStorageUnitByISN(String isn) {
        ElcatalogDTO book = mapperBooks.map(bookRepository.findByIsn(isn),ElcatalogDTO.class);
        return book;
    }

    /**
     * Add storage unit in database
     * @param storageUnit
     */
    @Override
    public void addStorageUnit(ElcatalogDTO storageUnit) {
        bookRepository.save(mapperBooks.map(storageUnit,ElcatalogEntity.class));
    }

    /**
     * delete storage unit with isn identifier from database
     * @param isn
     */
    @Override
    public void delStorageUnitByISN(String isn) {
        bookRepository.deleteByIsn(isn);
    }

    /**
     * Validate storage unit
     * Use it before added storage unit in database
     * @param newStorageUnit - new storage unit
     * @return validated storage unit or storage this errors
     */
    @Override
    public ElcatalogDTO validateStorageUnit(NewStorageUnit newStorageUnit){

        ElcatalogDTO storageUnit = new ElcatalogDTO(newStorageUnit.getAuthor(),newStorageUnit.getTitle(),
                newStorageUnit.getPublishingHouse(),newStorageUnit.getCity(),Long.valueOf(1),Long.valueOf(1),
                newStorageUnit.getIsn(),newStorageUnit.getText());
        Pattern p = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ0-9-\\s.,_]{1,50}$+");
        Matcher m = p.matcher(newStorageUnit.getAuthor());
        if (!m.matches()){
            storageUnit.setAuthor("@Error1");
        }
        p = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ.,0-9-\\s_]{1,50}$+");
        m = p.matcher(newStorageUnit.getTitle());
        if (!m.matches()){
            storageUnit.setTitle("@Error1");
        }
        m = p.matcher(newStorageUnit.getPublishingHouse());
        if (!m.matches()){
            storageUnit.setPublishingHouse("@Error1");
        }
        m = p.matcher(newStorageUnit.getCity());
        if (!m.matches()){
            storageUnit.setCity("@Error1");
        }
        p = Pattern.compile("^[0-9]{1,4}$+");
        m = p.matcher(newStorageUnit.getYear());
        if (!m.matches()){
            storageUnit.setYear(Long.valueOf(-1));
        }
        else{
            storageUnit.setYear(Long.parseLong(newStorageUnit.getYear()));
        }
        m = p.matcher(newStorageUnit.getPagesCount());
        if (!m.matches()){
            storageUnit.setPagesCount(Long.valueOf(-1));
        }
        else{
            storageUnit.setPagesCount(Long.parseLong(newStorageUnit.getPagesCount()));
        }
        p = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ0-9-]{1,50}$+");
        m = p.matcher(newStorageUnit.getIsn());
        if (!m.matches()){
            storageUnit.setIsn("@Error1");
        }
        if (newStorageUnit.getText().replaceAll(" ","").equals("") || newStorageUnit.getText().length()>1_000_000){
            storageUnit.setText("@Error1");
        }

        return storageUnit;
    }
}

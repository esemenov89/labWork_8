package code.services;

import code.model.dto.ElcatalogDTO;
import code.model.pojo.NewStorageUnit;
import java.util.ArrayList;

/**
 * Created by admin on 22.04.2017.
 */
public interface StorageUnitService {
    ArrayList<ElcatalogDTO> getAllStorageUnits();
    ElcatalogDTO getStorageUnitByISN(String isn);
    void delStorageUnitByISN(String isn);
    ElcatalogDTO validateStorageUnit(NewStorageUnit newStorageUnit);
    void addStorageUnit(ElcatalogDTO storageUnit);
}

package code.model.dao;

import code.model.hibernate.ElcatalogEntity;
import code.model.hibernate.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Artem Panasyuk on 16.05.2017.
 */

/*@Repository*/
public interface StorageUnitRepository extends CrudRepository<ElcatalogEntity,Long>{
    ElcatalogEntity findByIsn(String isn);
    void deleteByIsn(String isn);
}

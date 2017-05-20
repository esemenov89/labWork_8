package code.model.dao;

import code.model.hibernate.UsersEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*@Repository*/
public interface UserRepository extends CrudRepository<UsersEntity,Long>{
    UsersEntity findByNick(String login);
    UsersEntity findByMail(String mail);

    @Modifying //??
    @Query("UPDATE UsersEntity c SET c.enabled = :lock WHERE c.nick = :login")
    void lockOrUnlockUser(@Param("login") String login, @Param("lock") Long lock);
}

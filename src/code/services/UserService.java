package code.services;

import code.model.dto.UserDTO;
import code.model.hibernate.UsersEntity;
import java.util.ArrayList;

/**
 *
 */
public interface UserService {

    UsersEntity findByNick(String login);
    ArrayList<UserDTO> getAllUsers();
    UsersEntity validateUser(String login, String password, String mail);
    UserDTO findByMail(String mail);
    void addUser(UsersEntity user);
    void lockOrUnlockUser(String nick,Long lock);
}

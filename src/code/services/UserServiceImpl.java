package code.services;

import code.model.dao.UserRepository;
import code.model.dto.UserDTO;
import code.model.hibernate.UsersEntity;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for working with users
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private MapperFacade mapperUsers;
    private UserRepository userRepository;

    @Autowired
    public void setMapperFacade(MapperFacade mapperFacade) {
        this.mapperUsers = mapperFacade;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UsersEntity findByNick(String login){
        return userRepository.findByNick(login);
    }

    /**
     * Validate user
     * Use it before added user in database
     * @param login - of new user
     * @param password - of new user
     * @param mail - of new user
     * @return validated user or user with errors
     */
    @Override
    public UsersEntity validateUser(String login, String password, String mail){
        UsersEntity user = new UsersEntity(login,mail,password,"ROLE_USER",Long.valueOf(1));
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{2,16}$+");
        Matcher m = p.matcher(login);
        if (!m.matches()) {
            user.setNick("@Error1");
        }
        if (findByNick(login) != null) {
            user.setNick("@Error2");
        }
        p = Pattern.compile("^[a-zA-Z0-9]{8,16}$+");
        m = p.matcher(password);
        if (!m.matches()) {
            user.setPassword("@Error1");
        }
        p = Pattern.compile("^[a-zA-Z0-9.-@]{8,30}$+");
        m = p.matcher(mail);
        if (!m.matches()) {
            user.setMail("@Error1");
        }
        if (findByMail(mail) != null) {
            user.setMail("@Error2");
        }
        return user;
    }

    @Override
    public UserDTO findByMail(String mail) {
        return mapperUsers.map(userRepository.findByMail(mail),UserDTO.class);
    }

    /**
     * Add user in database
     * @param user
     */
    @Override
    public void addUser(UsersEntity user){
        userRepository.save(user);
    }

    /**
     * Method for lock or unlock user
     * @param nick - of user
     * @param lock - if 1, then user will be locked, if 0, then user will be unlocked
     */
    @Override
    public void lockOrUnlockUser(String nick,Long lock){
        userRepository.lockOrUnlockUser(nick,lock);
    }

    /**
     * Get all user from database
     * @return
     */
    @Override
    public ArrayList<UserDTO> getAllUsers(){
        ArrayList<UserDTO> users = null;
        users=(ArrayList)(mapperUsers.mapAsList(userRepository.findAll(),UserDTO.class));
        return users;
    }
}

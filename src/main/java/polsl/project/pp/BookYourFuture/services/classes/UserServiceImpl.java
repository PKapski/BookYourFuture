package polsl.project.pp.BookYourFuture.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.interfaces.UserDAO;
import polsl.project.pp.BookYourFuture.entities.User;
import polsl.project.pp.BookYourFuture.services.interfaces.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO theUserDAO){
        userDAO = theUserDAO;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(int theId) {
        return userDAO.findById(theId);
    }

    @Override
    public void save(User theUser) {
        userDAO.save(theUser);
    }

    @Override
    public void deleteById(int theId) {
        userDAO.deleteById(theId);
    }

    @Override
    public User findByUsername(String theUsername) {return userDAO.findByUsername(theUsername);}

    @Override
    public void updateUser(int id,User user){userDAO.updateUser(id,user);}

    @Override
    public User findByEmail(String email){return userDAO.findByEmail(email);}

    @Override
    public User findByPhone(String phone){return userDAO.findByPhone(phone);}

    @Override
    public boolean hasEmptyValues(User user){return userDAO.hasEmptyValues(user);}
}

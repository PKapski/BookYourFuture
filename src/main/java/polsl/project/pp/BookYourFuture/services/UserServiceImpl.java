package polsl.project.pp.BookYourFuture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.project.pp.BookYourFuture.dao.UserDAO;
import polsl.project.pp.BookYourFuture.entities.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

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
}

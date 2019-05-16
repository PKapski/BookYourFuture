package polsl.project.pp.BookYourFuture.dao;

import polsl.project.pp.BookYourFuture.entities.User;

import java.util.List;

public interface UserDAO {

    public List<User> findAll();

    public User findById(int theId);

    public void save(User theUser);

    public void deleteById(int theId);
}

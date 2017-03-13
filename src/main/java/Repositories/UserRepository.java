package Repositories;

import model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ShubU on 3/13/2017.
 */

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    public User get(String username){
        User user = entityManager.getReference(User.class, username);
        return user;
    }

    public void add(User user){
        entityManager.persist(user);
    }

    public void remove(User user){
        entityManager.remove(user);
    }

}

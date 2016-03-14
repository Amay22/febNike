package nike.dao;

import nike.model.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<User> findAllUsers() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.email ASC", User.class);
    	List<User> users = query.getResultList();
    	return users;
	}

	@Override
	public User findUserById(int id) {
		User user = em.find(User.class, id);
		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
    	query.setParameter("pEmail", email);
    	List<User> users = query.getResultList();
    	if(users != null && users.size() == 1) {
    		return users.get(0);
    	}else {
    		return null;
    	}
	}

	@Override
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		return em.merge(user);
	}

	@Override
	public void deleteUser (User user) {
		em.remove(user);
	}

	
}

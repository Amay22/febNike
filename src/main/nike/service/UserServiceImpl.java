package nike.service;

import nike.model.User;
import nike.dao.UserDAO;
import nike.exception.UserAlreadyExistsException;
import nike.exception.UserNotFoundException;

import java.util.List;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;
	
	@Override
	public List<User> findAllUsers() {
		return userDAO.findAllUsers();
	}
	
	@Override
	public User login(String email, String password)throws UserNotFoundException {
		User user = findUserByEmail(email);
		if(user == null) {
			throw new UserNotFoundException();
		}else if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public User findUserById(int id) throws UserNotFoundException {
		User user =  userDAO.findUserById(id);
		if(user == null) {
			throw new UserNotFoundException();
		}else{
			return user;
		}
	}

	@Override
	public User findUserByEmail(String email) throws UserNotFoundException {
		User user = userDAO.findUserByEmail(email);
		if(user == null) {
			throw new UserNotFoundException();
		}else{
			return user;
		}
	}

	@Override
	public User createUser(User user) throws UserAlreadyExistsException {
		User existing =  userDAO.findUserByEmail(user.getEmail());
		if(existing == null) {
			// Password salting
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			return userDAO.createUser(user);
		}else{
			throw new UserAlreadyExistsException();
		}
	}

	@Override
	public User updateUser(int id, User user) throws UserNotFoundException {
		User existing =  userDAO.findUserById(id);
		if(existing == null) {
			throw new UserNotFoundException();
		}else{
			return userDAO.updateUser(user);
		}
	}

	@Override
	public void deleteUser(int id) throws UserNotFoundException {
		User existing =  userDAO.findUserById(id);
		if(existing == null) {
			throw new UserNotFoundException();
		}else{
			userDAO.deleteUser(existing);
		}
	}
}

package nike.service;

import nike.model.User;
import nike.exception.UserAlreadyExistsException;
import nike.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

	List<User> findAllUsers();

	User findUserById(int id) throws UserNotFoundException;

	User findUserByEmail(String email) throws UserNotFoundException;

	User createUser(User user) throws UserAlreadyExistsException;

	User updateUser(int id, User user) throws UserNotFoundException;

	void deleteUser(int id) throws UserNotFoundException;

}

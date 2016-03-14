package nike.dao;

import nike.model.User;

import java.util.List;

public interface UserDAO {

	public List<User> findAllUsers ();
	public User findUserById(int id);
	public User findUserByEmail(String email);
	public User createUser(User user);
	public User updateUser(User user);
	public void deleteUser(User user);
}

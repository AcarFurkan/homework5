package assigment5.dataAccess.abstracts;

 
 

import java.util.List;

import assigment5.entities.concrete.User;

public interface UserDao {
	void add(User user);
	void update(User user);
	void delete(User user);
	List<User> getAll();
	User getById(int id); 
	User getByEmail(String email);
	User getByEmailAndPassword(String email,String password);
	int getUserCount();
	 
}

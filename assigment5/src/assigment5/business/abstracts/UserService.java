package assigment5.business.abstracts;

import java.util.List;

import assigment5.entities.concrete.User;

public interface UserService {
	void registerWithGoogle(User user);
	void loginWithGoogle(String email, String password);
	void register(User user);
	void login(String email, String password);
	void update(User user);
	void delete(User user);
	List<User> getAll();
	User get(); 
}

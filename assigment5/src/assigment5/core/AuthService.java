package assigment5.core;

import assigment5.entities.concrete.User;

public interface AuthService {
	
	void register(User user);
	void login(String email, String password);

}

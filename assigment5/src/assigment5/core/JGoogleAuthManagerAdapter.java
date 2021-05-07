package assigment5.core;

import assigment5.JAuth.JGoogleAuthManager;
import assigment5.entities.concrete.User;

public class JGoogleAuthManagerAdapter implements AuthService{
	
	

	@Override
	public void register(User user) {
		
		JGoogleAuthManager jGoogleAuthManager = new JGoogleAuthManager();
		jGoogleAuthManager.register(user);
		
		
	}

	@Override
	public void login(String email, String password) {
		JGoogleAuthManager jGoogleAuthManager = new JGoogleAuthManager();
		jGoogleAuthManager.login(email,password);
		
	}

}

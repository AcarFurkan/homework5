package assigment5.JAuth;

 
import assigment5.entities.concrete.User;

public class JGoogleAuthManager {

	
	
	public void register(User user) { 
		System.out.println(user.getFirstName() + " jgoogle auth manager ile register olundu."  );
		
		
		
	}
 
	public void login(String email, String password) {
		System.out.println(email + " jgoogle auth manager ile login olundu."  );
		
		
	}
}

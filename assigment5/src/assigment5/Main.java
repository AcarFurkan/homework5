package assigment5;

import java.util.Scanner;

import assigment5.business.abstracts.UserService;
import assigment5.business.concrete.UserManager;
import assigment5.core.JGoogleAuthManagerAdapter;
import assigment5.dataAccess.concrete.InMemoryUserDao;
import assigment5.entities.concrete.User;

public class Main {
	public static UserService userManager = new UserManager(new InMemoryUserDao());
	public static UserService userManager2 = new UserManager(new JGoogleAuthManagerAdapter());

	public static void main(String[] args) { 
		String firstName = "furkan";
		String lastName = "ozcan";
		String email = "facarr00@gmai.com";
		String password = "165465546";
		User user = new User(1,firstName,lastName,email,password);
		while(true) {
			
			 Scanner scanner = new Scanner(System.in);
			 System.out.println("email ile kayıt yapmak için 0 e basınız");
			 System.out.println("email ile giriş yapmak için 1 e basınız");
			 System.out.println("google ile giriş yapmak için 2 e basınız");
			 System.out.println("google ile kayıt yapmak için 3 e basınız");
			 int enteredNumber = scanner.nextInt();
			 
			switch(enteredNumber){
				case 0: 
					registerWithEmail();
					break;
				case 1: 
					 loginWithEmail();
					break;
				case 2: 
					 loginWithGoogle();
					break;
				case 3: 
					 registerWithGoogle();
					break;
			}
			 
			}
		 
	}
	
	private static void registerWithGoogle() {
		Scanner scanner = new Scanner(System.in);
		String firstName ;
		String lastName ;
		String email ;
		String password ;
		System.out.println("enter your name");
		firstName = scanner.next();
		System.out.println("enter your surname");
		lastName = scanner.next();
		System.out.println("enter your email");
		email = scanner.next();
		System.out.println("enter your password");
		password = scanner.next();
		User user = new User(1,firstName,lastName,email,password);
		userManager2.register(user);
	}

	private static void loginWithGoogle() {
		Scanner scanner = new Scanner(System.in); 
		String email ;
		String password ;  
		 System.out.println("enter your email");
		 email = scanner.next();
		 System.out.println("enter your password");
		 password = scanner.next();
		 userManager2.login(email,password);
		
		
	}

	public static void loginWithEmail() {
		Scanner scanner = new Scanner(System.in); 
		String email ;
		String password ;  
		 System.out.println("enter your email");
		 email = scanner.next();
		 System.out.println("enter your password");
		 password = scanner.next();
		 userManager.login(email,password);
	}
	public static void registerWithEmail() {
		Scanner scanner = new Scanner(System.in);
		String firstName ;
		String lastName ;
		String email ;
		String password ;
		System.out.println("enter your name");
		firstName = scanner.next();
		System.out.println("enter your surname");
		lastName = scanner.next();
		System.out.println("enter your email");
		email = scanner.next();
		System.out.println("enter your password");
		password = scanner.next();
		User user = new User(1,firstName,lastName,email,password);
		userManager.register(user);
	}

}

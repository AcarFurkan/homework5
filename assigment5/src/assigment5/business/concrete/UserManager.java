package assigment5.business.concrete;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


import java.util.regex.Matcher;

import assigment5.business.abstracts.UserService;
import assigment5.core.AuthService;
import assigment5.dataAccess.abstracts.UserDao;
import assigment5.entities.concrete.User;

public class UserManager implements UserService{
	
	UserDao _userDao;
	AuthService _authService;
	 

	public UserManager(UserDao _userDao) {
		super();
		this._userDao = _userDao;
	}
	
	public UserManager(AuthService _authService) {
		super();
		this._authService = _authService;
	}
	public UserManager(UserDao _userDao,AuthService _authService) {
		super();
		this._userDao = _userDao;
		this._authService = _authService;
	}
	public void registerWithGoogle(User user) {
		_authService.register(user);
		user.setActive(false); 
		System.out.println(" kullanıcı sayısı = " + _userDao.getAll().size());
		confirmationTransaction(user); 
		System.out.println(" kullanıcı başarıyla eklendi");
		_userDao.add(user);
		  
	}
	
	public void loginWithGoogle(String email, String password) {
		_authService.login(email,password);
		  
	}
	
	@Override
	public void login(String email,String password) {   
	   List<User> userTemp;
	   userTemp = _userDao.getAll(); 
	   for(int i = 0; i<userTemp.size();i++) { 
		 if (email.equals(userTemp.get(i).getEmail()) && password.equals(userTemp.get(i).getPassword())) {
			System.out.println("Basarasıyla giriş yapıldı");
			return;
		 } else {
			 System.out.println("giris basarisiz.!!!!!!!!!!!!!!!!!!!!!!!");
		 }
	   }  
	}

	@Override
	public void register(User user) {
		 List<User> userTemp; 
		 String regex3 = "^(.+)@(.+)$";
		 String regex4 = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 Pattern pattern = Pattern.compile(regex4, Pattern.CASE_INSENSITIVE);
		 
		 
		 Matcher matcher = pattern.matcher(user.getEmail());
	     boolean matchFound = matcher.find();
	     /////////////////////// 
	     if(matchFound) {
		   System.out.println(" Regex onay verdi ");
	     }
	     else {
		  System.out.println("REGEX ONAY VERMEDI");
		  System.out.println("LUTFEN GECERLI BIR MAIL GIRINIZ");
		  return;
	     }
	   
	     /////////////////////////
	   
	   
	   
	   if(user.getFirstName().length()<=2 && user.getLastName().length()<=2) {
		   System.out.println(" ad ve soyad en az 2 karakter olmalıdır. ");
		   return;
	   }
	   
	   if(user.getPassword().length() < 6  ) { 
		   System.out.println(" password en az 6 karakter olmalidir. ");
		   return; 
	   }  
	   userTemp = _userDao.getAll(); 
	   for(int i = 0; i<userTemp.size();i++) { 
		 if (user.getEmail().equals(userTemp.get(i).getEmail())) {
			System.out.println(" BU MAİİLLE DAHA ÖNCEDEN KAYIT YAPILDI.");
			return;
		 } 
	   } 
	   user.setActive(false);
	   _userDao.add(user);
	   System.out.println(" kullanıcı başarıyla eklendi");
	   System.out.println(" kullanıcı sayısı = " + _userDao.getAll().size());
	   
	   confirmationTransaction(user); 
	}

	private void confirmationTransaction(User user) {
		boolean verify = sendVerificationCode();
		   if (verify) {
			   user.setActive(true);
			  System.out.println("kullanıcı emaili onayladı");
		   }
		   else {
			  System.out.println("kullanıcı emaili onaylamadı kullanıcı hala pasif konumda");
		   }
		
	}

	private boolean sendVerificationCode() {
		 boolean whileControl = true;
		 boolean verificationCode = false; 
		 Scanner scanner = new Scanner(System.in);
		 while(whileControl==true) {
			 
			 System.out.println("şuan onay kodunu görüyorsunuz onaylamak için 1 e iptal için 0 basınız.");
			 int number = scanner.nextInt();
			 if(number == 1) { //  
				 whileControl =false;
				 verificationCode = true;
			 }else if(number == 0 ) {
				 whileControl =false;
				 verificationCode = false; 
			 }else {
				 System.out.println("GECERSIZ BIR NUMARA GIRDINIZ");
			 }
			 
		 }
		 
		 
		 
		 return verificationCode; 
	}

	@Override
	public void update(User user) {
		   List<User> userTemp;
		   if(user.getFirstName().length()>2 && user.getLastName().length()>2) {
			   System.out.println(" ad ve soyad en az 2 karakter olmalıdır. ");
			   return;
		   }
		   
		   if(user.getPassword().length() > 6  ) { 
			   System.out.println(" password en az 6 karakter olmalidir. ");
			   return; 
		   }  
		   userTemp = _userDao.getAll(); 
		   for(int i = 0; i<userTemp.size();i++) { 
			 if (user.getEmail().equals(userTemp.get(i))) {
				System.out.println(" BU MAİİLLE DAHA ÖNCEDEN KAYIT YAPILDI.");
				return;
			 } 
		   } 
		   
		   _userDao.update(user);
		   System.out.println(user.getFirstName() + " kullanıcı başarıyla güncellendi");
		   System.out.println(" kullanıcı sayısı = " + _userDao.getAll().size());
		
	}

	@Override
	public void delete(User user) { 
		   _userDao.delete(user);
		   System.out.println( user.getFirstName() + " kullanıcı başarıyla silindi");
		   System.out.println(" kullanıcı sayısı = " + _userDao.getAll().size()); 
	}

	@Override
	public List<User> getAll() {
		System.out.println("getall methodu çalıştı");
		return _userDao.getAll();
	}

	@Override
	public User get() {
		System.out.println("get methodu çalıştı");
		 var result = _userDao.get();
		return result;
	}

}

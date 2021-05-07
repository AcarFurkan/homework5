package assigment5.business.concrete;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


import java.util.regex.Matcher;

import assigment5.business.abstracts.UserService;
import assigment5.core.AuthService;
import assigment5.core.business.BusinessRules;
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
		boolean validation = BusinessRules.Run(checkIfUserExist(user)); 	 
		   if (!validation) return; 
		user.setActive(false);  
		confirmationTransaction(user); 
		System.out.println(" kullanıcı başarıyla eklendi");
		_userDao.add(user); // google onaydan geçtikten sonra kendi database mize ekledik.
		System.out.println(" kullanıcı sayısı = " + _userDao.getAll().size());
		_authService.register(user); //google maili olduğu için kontrolü google yapan bir service implement ettiğimizi düşünüyoruz ve google giriş yapılan maiileri kendimiz kontrol etmiyoruz.
	}
	
	public void loginWithGoogle(String email, String password) {
		
		var result = _userDao.getByEmailAndPassword(email, password); 
		if(result ==null) {
			System.out.println("giris basarisiz.!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}else if(!isActive(email)) {
			System.out.println("giris basarisiz. cunku kullanıcı aktif değil giriş yapmadan önec kullanıcıyı aktifleştirin.");
			return;
		}
		else {
			_authService.login(email,password);// bunu dışardan sistem eklemeye bildiğimizi sümile ettik altaki kod gerçekten database ekliyor.
			System.out.println(result.getFirstName() + " kullanıcı başarıyla giriş yaptı");
		}  
		  
	}
	 
	@Override
	public void login(String email,String password) {    
		var result = _userDao.getByEmailAndPassword(email, password); 
		if(result ==null) {
			System.out.println("giris basarisiz.!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}else if(!isActive(email)) {
			System.out.println("giris basarisiz. cunku kullanıcı aktif değil giriş yapmadan önec kullanıcıyı aktifleştirin.");
			return;
		}
		else {
			System.out.println(result.getFirstName() + " kullanıcı başarıyla giriş yaptı");
		}  
	}

	private boolean verifyByEmail(User user) { 

		 String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE); 
		 Matcher matcher = pattern.matcher(user.getEmail());
	     boolean matchFound = matcher.find(); 
	     
	     if(matchFound) {
		   System.out.println(" Regex onay verdi ");
		   return true;
	     } 
	     else {
		  System.out.println("REGEX ONAY VERMEDI");
		  System.out.println("LUTFEN GECERLI BIR MAIL GIRINIZ");
		  return false;
	     }  
	}
	 
	private boolean verifyByNameAndSurname(User user) { 
		
		if(user.getFirstName().length()<=2 && user.getLastName().length()<=2) {
			   System.out.println(" ad ve soyad en az 2 karakter olmalıdır. ");
			   return false;
		} 
		return true;
		
	}
	
	private boolean verifyByPasswordLenght(User user){

		   if(user.getPassword().length() < 6  ) { 
			   System.out.println(" password en az 6 karakter olmalidir. ");
			   return false; 
		   } 
		   return true;
	}
  
	private boolean checkIfUserExist(User user) { // kullanıvı önceden var sa false yoksa true donuyo false ile hata kontrolü yaptığımız için bunları yaptık.
		User tempUser = _userDao.getByEmail(user.getEmail()); 
		if(tempUser == null){
		 return true;  
		}
		System.out.println(" BU MAİİLLE DAHA ÖNCEDEN KAYIT YAPILDI.");
		return false; 
	}
	 
	@Override
	public void register(User user) {    
	   boolean validation = BusinessRules.Run(verifyByEmail(user),verifyByNameAndSurname(user),
			   verifyByPasswordLenght(user),checkIfUserExist(user)); 	 
	   if (!validation) return;  
	   user.setActive(false);
	   _userDao.add(user); 
	   System.out.println(" kullanıcı başarıyla eklendi");
	   System.out.println(" kullanıcı sayısı = " + _userDao.getUserCount()); 
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
		 boolean validation = BusinessRules.Run(verifyByEmail(user),verifyByNameAndSurname(user),
				   verifyByPasswordLenght(user),checkIfUserExist(user)); 	 
		   if (!validation) return; 
		   
		   _userDao.update(user);
		   System.out.println(user.getFirstName() + " kullanıcı başarıyla güncellendi"); 
		
	}

	@Override
	public void delete(User user) { 
		   _userDao.delete(user);
		   System.out.println( user.getFirstName() + " kullanıcı başarıyla silindi"); 
	}

	@Override
	public List<User> getAll() {
		System.out.println("getall methodu çalıştı");
		return _userDao.getAll();
	}

	
	
	
	@Override
	public User getById(int id) { 
		return _userDao.getById(id);
	}

	@Override
	public int getUserCount() { 
		return _userDao.getUserCount();
	}

	@Override
	public boolean isActive(String email) {
		User user = _userDao.getByEmail(email);
		return user.isActive();
	}

	@Override
	public void activateUser(String email) {
		if(isActive(email)) {
			System.out.println("kullanıcı zaten aktif");
			return;
		}
		else {
			User user = _userDao.getByEmail(email);
			user.setActive(true);
			System.out.println("kullanıcı aktif hale getirildi.");
			return;
		}
		
	}
 
	 

}

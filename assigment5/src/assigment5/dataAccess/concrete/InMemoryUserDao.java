package assigment5.dataAccess.concrete;

import java.util.ArrayList;
import java.util.List;

import assigment5.dataAccess.abstracts.UserDao;
import assigment5.entities.concrete.User;

public class InMemoryUserDao implements UserDao{
	
	List<User> _user; //fake veri db
	
	public InMemoryUserDao() {
		super();
		this._user = new ArrayList<User>();
		
	}

	@Override
	public void add(User user) { 
		_user.add(user);
		System.out.println(user.getFirstName() +"  kullanıcısı db ye eklendi.");
	
	}

	@Override
	public void update(User user) {
		System.out.println(user.getFirstName() +"  kullanıcısı db den güncellendi.");
		
		int value = _user.indexOf(user);
	    _user.set(value, user);  
	}

	@Override
	public void delete(User user) {
		
		_user.remove(user);
		System.out.println(user.getFirstName() +"  kullanıcısı db den silindi.");
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return _user;
	}

	@Override
	public User get() {
		// TODO Auto-generated method stub
		return _user.get(0);
	}
	 

}

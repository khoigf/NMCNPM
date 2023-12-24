package org.airline.configuration;


import org.airline.Dao.UserRepository;
import org.airline.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepositery;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetching user from database
		User user = userRepositery.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could not found user !!");
		}
		CustomUserDetails userDetails = new CustomUserDetails(user);
		
		return userDetails;
	}

}

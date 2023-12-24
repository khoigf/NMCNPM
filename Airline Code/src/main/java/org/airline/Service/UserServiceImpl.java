package org.airline.Service;

import java.util.List;

import org.airline.Dao.UserRepository;
import org.airline.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepositery;

	@Transactional
	public User saveData(User user) {
		try {
			// Your save logic here
			return this.userRepositery.save(user);
		} catch (Exception e) {
			// Handle the exception or log it
			throw new RuntimeException("Error saving user data", e);
		}
	}

	@Override
	public User getUserByUserName(String email) {

		return this.userRepositery.getUserByUserName(email);
	}

	@Override
	public User findAdminAll() {
		List<User> admin = this.userRepositery.findAll();
		User user1 = null;
		for (User user : admin) {
			if (user.getRole().equals("ROLE_ADMIN")) {
				user1 = user;
				break;
			}
		}
		return user1;
	}

	@Override
	public Page<User> findAllUser(PageRequest pageRequest) {
		return this.userRepositery.findAll(pageRequest);
	}

	@Override
	public List<User> findByUserName(String query) {
		return this.userRepositery.findByNameContaining(query);
	}

	@Override
	public List<User> findAllUsers() {
		return this.userRepositery.findAll();
	}

}

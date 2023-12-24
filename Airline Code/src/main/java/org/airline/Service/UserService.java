package org.airline.Service;

import java.util.List;

import org.airline.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

	User saveData(User user);

	User getUserByUserName(String email);

	User findAdminAll();

	Page<User> findAllUser(PageRequest pageRequest);

	List<User> findByUserName(String query);

	List<User> findAllUsers();
}

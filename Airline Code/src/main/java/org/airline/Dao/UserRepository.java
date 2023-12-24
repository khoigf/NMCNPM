package org.airline.Dao;

import java.util.List;

import org.airline.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);

	List<User> findByNameContaining(String query);
}

package org.airline.Dao;

import java.util.List;

import org.airline.Entity.Round_Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Round_TripRepositery extends JpaRepository<Round_Trip, Long> {

	@Query("from Round_Trip as t where t.user.uId=:id")
	public List<Round_Trip> findRound_TripsByUser(@Param("id") Long id);
}

package org.airline.Dao;

import java.util.List;

import org.airline.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddFlightRepository extends JpaRepository<Flight, Long> {

	List<Flight> findByFlightNameContaining(String query);

}

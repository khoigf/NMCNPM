package org.airline.Service;

import java.util.List;

import org.airline.Entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AddFlightService {

	Flight saveFlight(Flight addFlight);

	Page<Flight> findAllFlight(PageRequest pageRequest);

	long countRecourd();

	Flight findByFlightNo(Long fId);

	void deleteFlight(Flight addFlight);

	List<Flight> findByFlightName(String query);

	List<Flight> findAllFlights();

}

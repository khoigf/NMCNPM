package org.airline.Service;

import java.util.List;

import org.airline.Dao.AddFlightRepository;
import org.airline.Entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AddFlightServiceImpl implements AddFlightService {
	@Autowired
	private AddFlightRepository addFlightRepository;

	@Override
	public Flight saveFlight(Flight addFlight) {

		return this.addFlightRepository.save(addFlight);
	}

	@Override
	public Page<Flight> findAllFlight(PageRequest pageRequest) {

		return this.addFlightRepository.findAll(pageRequest);
	}

	@Override
	public long countRecourd() {

		return this.addFlightRepository.count();
	}

	@Override
	public Flight findByFlightNo(Long fId) {

		return this.addFlightRepository.findById(fId).get();
	}

	@Override
	public void deleteFlight(Flight addFlight) {
		this.addFlightRepository.delete(addFlight);

	}

	// search Flight
	@Override
	public List<Flight> findByFlightName(String query) {

		return this.addFlightRepository.findByFlightNameContaining(query);
	}

	// find all flight for view_flight Page
	@Override
	public List<Flight> findAllFlights() {

		return this.addFlightRepository.findAll();
	}

}

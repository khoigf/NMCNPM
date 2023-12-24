package org.airline.Service;

import java.util.List;

import org.airline.Dao.Round_TripRepositery;
import org.airline.Entity.Round_Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Round_TripServiceImpl implements Round_TripService {

	@Autowired
	private Round_TripRepositery round_TripRepositery;

	@Override
	public List<Round_Trip> findAllTicket() {

		return this.round_TripRepositery.findAll();
	}

	@Override
	public Round_Trip findById(Long tId) {

		return this.round_TripRepositery.findById(tId).get();
	}

	@Override
	public void delete(Round_Trip trip) {
		this.round_TripRepositery.delete(trip);

	}

	@Override
	public List<Round_Trip> findRoundBookingsByUser(Long getuId) {

		return this.round_TripRepositery.findRound_TripsByUser(getuId);
	}

}

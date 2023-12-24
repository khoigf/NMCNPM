package org.airline.Service;

import java.util.List;

import org.airline.Entity.Round_Trip;

public interface Round_TripService {

	List<Round_Trip> findAllTicket();

	Round_Trip findById(Long tId);

	void delete(Round_Trip trip);

	List<Round_Trip> findRoundBookingsByUser(Long getuId);

}

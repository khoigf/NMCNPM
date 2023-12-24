package org.airline.controller;

import java.util.List;

import org.airline.Entity.Flight;
import org.airline.Entity.User;
import org.airline.Service.AddFlightService;
import org.airline.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
	@Autowired
	private AddFlightService addFlightService;
	private UserService userService;

	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query) {
		List<Flight> addFlights = this.addFlightService.findByFlightName(query);
		return ResponseEntity.ok(addFlights);
	}

	@GetMapping("/searchu/{query1}")
	public ResponseEntity<?> searchu(@PathVariable("query1") String query1) {
		List<User> addUsers = this.userService.findByUserName(query1);
		return ResponseEntity.ok(addUsers);
	}

}

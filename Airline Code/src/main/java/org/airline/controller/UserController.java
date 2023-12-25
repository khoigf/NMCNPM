package org.airline.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.airline.Entity.Flight;
import org.airline.Entity.Ticket;
import org.airline.Entity.Round_Trip;
import org.airline.Entity.Seat;
import org.airline.Entity.User;
import org.airline.Service.AddFlightService;
import org.airline.Service.TicketService;
import org.airline.Service.Round_TripService;
import org.airline.Service.UserService;
import org.airline.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AddFlightService addFlightService;
	@Autowired
	private TicketService book_TicketService;
	@Autowired
	private Round_TripService round_TripService;

	@ModelAttribute
	public void addCommanData(Model model, Principal principal, HttpSession session) {
		String uname = principal.getName();
		User user = userService.getUserByUserName(uname);
		model.addAttribute("user", user);
		if (session.getAttribute("message") != null) {
			if (!session.getAttribute("message").equals("")) {
				session.removeAttribute("message");
			}
		}

	}

	@GetMapping("/user_home")
	public String userHomePage(Model model) {
		model.addAttribute("title", "User Home");
		return "Normal/home";
	}

	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");

		return "Normal/profile";
	}

	// view Flight
	@GetMapping("/view_flight")
	public String viewFlight(Model model) {
		model.addAttribute("title", "View Flight Page");
		List<Flight> addFlights = this.addFlightService.findAllFlights();
		// desable previous date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		model.addAttribute("date", dtf.format(now));

		model.addAttribute("flights", addFlights);

		return "Normal/view_flight";
	}

	HashMap<String, Double> dat = new HashMap<String, Double>();
	static Double da_P;

	// find Flight
	@GetMapping("/find_flight")
	public String selectedFlight(@RequestParam("From") String From, @RequestParam("To") String To,
			@RequestParam("date") String date, Model model, HttpSession session) {
		dat.put("Sun", 10.00);
		dat.put("Mon", 10.00);
		dat.put("Tue", 10.00);
		dat.put("Wed", 10.00);
		dat.put("Thu", 10.00);
		dat.put("Fri", 10.00);
		dat.put("Sat", 10.00);

		try {
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			String day1 = new SimpleDateFormat("EE").format(d);
			System.out.println(day1);
			boolean b = true;
			List<Flight> findAllFlights = this.addFlightService.findAllFlights();
			findAllFlights = findAllFlights.stream().filter(addFlight -> (addFlight.getFrom().equals(From)
					&& addFlight.getTo().equals(To) && (addFlight.isEnabled() == b))).collect(Collectors.toList());
			List<Flight> flights = null;
			if (findAllFlights.isEmpty()) {
				session.setAttribute("message", new Message("Flight Not Found !! ", "alert-danger"));
				return "redirect:/user/view_flight";
			} else {
				flights = new ArrayList<Flight>();
				for (int i = 0; i < findAllFlights.size(); i++) {
					c1: for (String day2 : findAllFlights.get(i).getDays()) {
						if (day1.equalsIgnoreCase(day2)) {
							flights.add(findAllFlights.get(i));
							continue c1;
						}
					}
				}
				if (flights.isEmpty()) {
					session.setAttribute("message", new Message("Flight Not Found !! ", "alert-danger"));
					return "redirect:/user/view_flight";
				}
				model.addAttribute("date1", date);
				Set<String> set = dat.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if (key.equals(day1)) {
						da_P = dat.get(key);
						System.out.println(da_P);
						break;
					}
				}
				model.addAttribute("addFlights", flights);
			}

			model.addAttribute("title", "View Flight Page");
			List<Flight> addFlights = this.addFlightService.findAllFlights();
			// desable previous date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			model.addAttribute("date", dtf.format(now));

			model.addAttribute("flights", addFlights);

			return "Normal/view_flight";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "Normal/view_flight";
		}

	}

	// book Ticket Bage
	@GetMapping("/Book_flight/{fNo}/{date}")
	public String openTicketBookingPage(@PathVariable("fNo") Long fNo, Model model, @PathVariable("date") String date) {
		Flight flightNo = this.addFlightService.findByFlightNo(fNo);
		model.addAttribute("addFlight", flightNo);
		model.addAttribute("date", date);
		return "Normal/book_Ticket";
	}

	// seat Booking
	static Ticket book_t;
	int numticket, numroundtrip;

	@PostMapping("/seat")
	public String seatOpen(@ModelAttribute Ticket book_Ticket, Model model) {
		List<Ticket> book_Tickets = this.book_TicketService.findAllTicket();
		numticket = book_Tickets.size();
		List<Round_Trip> round_Trips = this.round_TripService.findAllTicket();
		numroundtrip = round_Trips.size();
		round_Trips = round_Trips.stream()
				.filter(t -> (t.getFlightNo() == book_Ticket.getFlightNo()
						&& t.getTrivalClass().equals(book_Ticket.getTrivalClass())
						&& t.getDate().compareTo(book_Ticket.getDate()) == 0))
				.collect(Collectors.toList());

		book_Tickets = book_Tickets.stream()
				.filter(t -> (t.getFlightNo() == book_Ticket.getFlightNo()
						&& t.getTrivalClass().equals(book_Ticket.getTrivalClass())
						&& t.getDate().compareTo(book_Ticket.getDate()) == 0))
				.collect(Collectors.toList());

		if (book_Tickets.size() >= 1 || round_Trips.size() >= 1) {
			List<String> checkSeat = new ArrayList<String>();

			for (Ticket book_Ticket2 : book_Tickets) {
				for (Object seatAdd : book_Ticket2.getSeatStore()) {
					Seat dd = (Seat) seatAdd;
					checkSeat.add(dd.getItem());
				}
			}

			// RoundTrip BookTicket
			for (Round_Trip round_Trip : round_Trips) {
				for (Object seatAdd : round_Trip.getSeatStore()) {
					Seat dd = (Seat) seatAdd;
					checkSeat.add(dd.getItem());
				}
			}

			model.addAttribute("checkSeat", checkSeat);
		} else {
			model.addAttribute("checkSeat", false);
		}

		// if(book_Ticket.getTripType().equals("Round_Trip")) {
		// try {
		// Date d=new SimpleDateFormat("yyyy-MM-dd").parse(book_Ticket.getRe_date());
		// String day=new SimpleDateFormat("EE").format(d);
		//
		// Set<String> set = dat.keySet();
		// Iterator<String> iterator = set.iterator();
		// while (iterator.hasNext()) {
		// String key = iterator.next();
		// if (key.equals(day)) {
		// da_P += dat.get(key);
		// break;
		// }
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		Flight addFlight = null;
		if (book_Ticket.getTrivalClass().equals("Business")) {
			addFlight = this.addFlightService.findByFlightNo(book_Ticket.getFlightNo());
			model.addAttribute("totalSeat", addFlight.getB_Seat());
			book_Ticket.setPrice(book_Ticket.getPrice() + da_P);

			book_t = book_Ticket;
		} else if (book_Ticket.getTrivalClass().equals("Economy")) {
			addFlight = this.addFlightService.findByFlightNo(book_Ticket.getFlightNo());
			model.addAttribute("totalSeat", addFlight.getE_seat());
			book_Ticket.setPrice(book_Ticket.getPrice() + da_P);

			book_t = book_Ticket;
		}
		return "Normal/seat1";
	}

	// seat handler

	@PostMapping("/b_seat")
	@ResponseBody
	public ResponseEntity<?> seatDetail(@RequestBody Seat data[]) throws IOException {

		if (data.length > 0) {
			for (Seat d : data) {
				System.out.println(d);
			}
			if (book_t != null) {
				System.out.println(data.length + " seat");
				book_t.setSeatStore(data);
				book_t.setPrice(book_t.getPrice() * data.length);
			} else {
				System.out.println(data.length + " seat");
				roundTrip.setRound_seatStore(data);
				roundTrip.setRound_Price(roundTrip.getRound_Price() * data.length);
			}
		}
		return ResponseEntity.of(Optional.of(data));
	}

	// food Page
	@GetMapping("/food_section")
	public String foodPage() {
		System.out.println(book_t);
		return "Normal/food";
	}

	// payment recipt
	static String tempFood[];

	@PostMapping("/click_pay")
	public String selectedFood(@RequestParam("food") String f_price[], Model model) {
		if (f_price.length > 0) {

			double balance = 0.0;

			for (String price : f_price) {

				balance += Double.parseDouble(price);
				System.out.println(price);
			}
			if (book_t != null && book_t.getTripType().equals("Round_Trip")) {
				tempFood = new String[f_price.length];
				for (int i = 0; i < f_price.length; i++) {
					tempFood[i] = f_price[i];
				}

				return "redirect:/user/Round_Trip_Flight/" + book_t.getTo() + "/" + book_t.getFrom() + "/"
						+ book_t.getRe_date() + "/" + book_t.getDate();
			} else if (book_t == null) {

				Flight flight2 = this.addFlightService.findByFlightNo(roundTrip.getFlightNo());
				Flight flight3 = this.addFlightService.findByFlightNo(roundTrip.getRound_flightNo());
				model.addAttribute("before_food", roundTrip.getPrice());
				model.addAttribute("before_food_Round", roundTrip.getRound_Price());

				roundTrip.setRound_Price(roundTrip.getRound_Price() + balance);

				model.addAttribute("totalFoodRound", balance);

				balance = 0.0;
				for (String f : tempFood) {
					balance += Double.parseDouble(f);
				}
				roundTrip.setPrice(roundTrip.getPrice() + balance);
				model.addAttribute("totalFood", balance);
				model.addAttribute("numberOfFoodOneTrip", tempFood);
				model.addAttribute("numberOfFoodRoundTrip", f_price);
				model.addAttribute("book_t", roundTrip);

				if (flight2.getBusinessClass().equals(roundTrip.getTrivalClass())) {
					model.addAttribute("class_price", flight2.getB_price());
				} else {
					model.addAttribute("class_price", flight2.getE_price());
				}
				if (flight3.getBusinessClass().equals(roundTrip.getRound_trivalClass())) {
					model.addAttribute("Round_class_price", flight3.getB_price());
				} else {
					model.addAttribute("Round_class_price", flight3.getE_price());
				}

				model.addAttribute("tax", 20.00);
				model.addAttribute("seatPrice", roundTrip.getPrice() + roundTrip.getRound_Price());
				roundTrip.setPrice(roundTrip.getPrice() + 10.00);
				roundTrip.setRound_Price(roundTrip.getRound_Price() + 10.00);
				model.addAttribute("totalPrice", roundTrip.getPrice() + roundTrip.getRound_Price());
				return "Normal/payment_recipt";

			}
			Flight flight = this.addFlightService.findByFlightNo(book_t.getFlightNo());
			model.addAttribute("before_food", book_t.getPrice());

			book_t.setPrice(book_t.getPrice() + balance);
			model.addAttribute("totalFood", balance);
			model.addAttribute("numberOfFood", f_price);
			model.addAttribute("book_t", book_t);
			if (flight.getBusinessClass().equals(book_t.getTrivalClass())) {
				model.addAttribute("class_price", flight.getB_price());
			} else {
				model.addAttribute("class_price", flight.getE_price());
			}

		}
		model.addAttribute("tax", 10.00);
		model.addAttribute("seatPrice", book_t.getPrice());
		book_t.setPrice(book_t.getPrice() + 10.00);
		return "Normal/payment_recipt";
	}

	// payment page
	@GetMapping("/payment")
	public String paymentPage() {

		return "Normal/payment";
	}

	static Ticket bookticket;
	static Round_Trip round_trip;

	// pay success
	@PostMapping("/pay")
	public String paymentSuccess(@RequestParam("cardNumber") String cardNumber, @RequestParam("username") String name,
			@RequestParam("cvv") String cvv, @RequestParam("mm") String mm, @RequestParam("yy") String yy,
			Principal principal, HttpSession session) {
		if (cardNumber.length() > 10 && !name.isBlank() && cvv.length() == 3 && mm.length() <= 2 && yy.length() <= 4) {
			String email = principal.getName();
			User findAdmin = this.userService.findAdminAll();

			User user = this.userService.getUserByUserName(email);

			if (findAdmin == null) {
				System.exit(0);
			}
			if (book_t != null) {
				user.setBalance(user.getBalance() - book_t.getPrice());
				findAdmin.setBalance(findAdmin.getBalance() + book_t.getPrice());
				book_t.setStatus("Booked");
				book_t.setUser(user);
				book_t.settId(numticket + 1);
				List<Ticket> book_Tickets = new ArrayList<>();
				book_Tickets.addAll(user.getBook_Tickets());
				book_Tickets.add(book_t);
				user.setBook_Tickets(book_Tickets);
				System.out.println(user);
				System.out.println(book_t);
				User user2 = this.userService.saveData(user);

				bookticket = user2.getBook_Tickets().get(user2.getBook_Tickets().size() - 1);
				this.userService.saveData(findAdmin);
				book_t = null;
				return "Normal/payment_success";
			} else if (roundTrip != null) {
				user.setBalance(user.getBalance() - (roundTrip.getPrice() + roundTrip.getRound_Price()));
				findAdmin.setBalance(findAdmin.getBalance() + (roundTrip.getPrice() + roundTrip.getRound_Price()));
				roundTrip.setStatus("Booked");
				roundTrip.setUser(user);
				roundTrip.settId(numroundtrip + 1);
				user.getRound_Trips().add(roundTrip);
				System.out.println(user);
				User user2 = this.userService.saveData(user);

				round_trip = user2.getRound_Trips().get(user2.getRound_Trips().size() - 1);
				this.userService.saveData(findAdmin);
				roundTrip = null;
				book_t = null;
				return "Normal/payment_success";
			}
		}
		session.setAttribute("message", new Message("Please Fill up Correct Detail !! ", "alert-danger"));
		return "Normal/payment";
	}

	// open setting handler
	@GetMapping("/settings")
	public String openSettings(Model model) {
		model.addAttribute("title", "Settings");
		return "Normal/settings";
	}

	// change password handler
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {

		String userName = principal.getName();
		User currentUser = this.userService.getUserByUserName(userName);
		if (this.passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change the password
			currentUser.setPassword(this.passwordEncoder.encode(newPassword));
			this.userService.saveData(currentUser);
			session.setAttribute("message",
					new Message("Your Password Is Successfully Updated..... ", "alert-success"));
		} else {
			// error
			session.setAttribute("message", new Message("Please Enter Correct Old Password !! ", "alert-danger"));
			return "redirect:/user/settings";
		}
		return "redirect:/logout";
	}

	// view Ticket
	@GetMapping("/view_booking")
	public String viewBooking(Model model, Principal principal, HttpSession session) {
		model.addAttribute("title", "Show Ticket");

		String name = principal.getName();

		List<Ticket> book_Tickets = this.book_TicketService
				.findBookTicketsByUser(userService.getUserByUserName(name).getuId());
		if (book_Tickets.isEmpty()) {
			session.setAttribute("message", new Message("You don't have any booking !! ", "alert-danger"));
		} else {
			model.addAttribute("book_Ticket", book_Tickets);
			List<String> dd = new ArrayList<String>();
			for (int i = 0; i < book_Tickets.size(); i++) {
				Ticket da = book_Tickets.get(i);
				for (int j = 0; j < da.getSeatStore().length; j++) {
					Seat add = (Seat) da.getSeatStore()[j];
					dd.add(add.getItem());
				}
			}
			model.addAttribute("seat", dd);
		}
		return "Normal/view_Booking";
	}

	// cancel Ticket
	@GetMapping("/cancel/{tId}")
	public String cancelBooking(@PathVariable("tId") Long tId, Principal principal, HttpSession session, Model model) {

		Ticket book_Ticket = this.book_TicketService.findById(tId);
		User admin = this.userService.findAdminAll();
		User user = this.userService.getUserByUserName(principal.getName());
		if (book_Ticket != null) {
			if (book_Ticket.getUser().getuId() == user.getuId()) {
				double percent = (book_Ticket.getPrice() * 10) / 100;
				percent = book_Ticket.getPrice() - percent;
				admin.setBalance(admin.getBalance() - percent);
				user.setBalance(user.getBalance() + percent);
				book_Ticket.setStatus("Canceled");
				model.addAttribute("pass", book_Ticket);

				this.book_TicketService.delete(book_Ticket);
				this.userService.saveData(user);
				this.userService.saveData(admin);
				session.setAttribute("message", new Message("Ticket Canceled Successfully...\nYour Refundable amount :"
						+ percent + "\nIs Transferred,Please Check Your Account.", "alert-success"));
			}

		} else {
			Round_Trip trip = this.round_TripService.findById(tId);
			if (trip.getUser().getuId() == user.getuId()) {
				double percent = ((trip.getPrice() + trip.getRound_Price()) * 10) / 100;
				percent = (trip.getPrice() + trip.getRound_Price()) - percent;
				admin.setBalance(admin.getBalance() - percent);
				user.setBalance(user.getBalance() + percent);
				trip.setStatus("Canceled");
				model.addAttribute("pass", trip);

				this.round_TripService.delete(trip);
				this.userService.saveData(user);
				this.userService.saveData(admin);
				session.setAttribute("message", new Message("Ticket Canceled Successfully...\nYour Refundable amount :"
						+ percent + "\nIs Transferred,Please Check Your Account.", "alert-success"));
			}
		}
		return "Normal/eCancel";
	}

	// bording pass
	@GetMapping("/pass")
	public String bordingPass(Model model) {

		if (bookticket != null) {
			List<String> dd = new ArrayList<String>();
			for (int i = 0; i < bookticket.getSeatStore().length; i++) {
				Seat add = (Seat) bookticket.getSeatStore()[i];
				dd.add(add.getItem());
			}
			model.addAttribute("seat", dd);
			model.addAttribute("pass", bookticket);

			bookticket = null;
			return "Normal/eTicket";
		} else if (round_trip != null) {
			List<String> dd = new ArrayList<String>();
			List<String> dd1 = new ArrayList<String>();
			for (int i = 0; i < round_trip.getSeatStore().length; i++) {
				Seat add = (Seat) round_trip.getSeatStore()[i];
				dd.add(add.getItem());
			}

			for (int i = 0; i < round_trip.getRound_seatStore().length; i++) {
				Seat add = (Seat) round_trip.getRound_seatStore()[i];
				dd1.add(add.getItem());
			}

			model.addAttribute("seat", dd);
			model.addAttribute("seat1", dd1);
			model.addAttribute("pass", round_trip);
			round_trip = null;
			bookticket = null;
			return "Normal/eTicket";
		}
		return "Normal/eTicket";
	}

	// Start RoundTrip Work
	@GetMapping("/Round_Trip_Flight/{From}/{To}/{R_date}/{date}")
	public String roundTripViewFlight(@PathVariable("From") String From, @PathVariable("To") String To,
			@PathVariable("R_date") String R_date, @PathVariable("date") String date, Model model,
			HttpSession session) {
		try {

			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(R_date);
			String day1 = new SimpleDateFormat("EE").format(d);
			System.out.println(day1);
			boolean b = true;
			List<Flight> findAllFlights = this.addFlightService.findAllFlights();
			findAllFlights = findAllFlights.stream().filter(addFlight -> (addFlight.getFrom().equals(From)
					&& addFlight.getTo().equals(To) && (addFlight.isEnabled() == b))).collect(Collectors.toList());
			List<Flight> flights = null;
			if (findAllFlights.isEmpty()) {
				session.setAttribute("message",
						new Message("Flight Not Found !! \nChoose Different Date", "alert-danger"));
				model.addAttribute("From", From);
				model.addAttribute("To", To);
				model.addAttribute("date", date);
				return "Normal/Round_Trip_ViewFlight";
			} else {
				flights = new ArrayList<Flight>();
				for (int i = 0; i < findAllFlights.size(); i++) {
					c1: for (String day2 : findAllFlights.get(i).getDays()) {
						if (day1.equalsIgnoreCase(day2)) {
							flights.add(findAllFlights.get(i));
							continue c1;
						}
					}
				}
				if (flights.isEmpty()) {
					session.setAttribute("message",
							new Message("Flight Not Found !! \nChoose Different Date", "alert-danger"));
					model.addAttribute("From", From);
					model.addAttribute("To", To);
					model.addAttribute("date", date);
					return "Normal/Round_Trip_ViewFlight";
				}
				model.addAttribute("date1", R_date);
				Set<String> set = dat.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if (key.equals(day1)) {
						da_P = dat.get(key);
						System.out.println(da_P);
						break;
					}
				}
				model.addAttribute("addFlights", flights);
			}

			model.addAttribute("title", "View Flight Page");
			// desable previous date
			model.addAttribute("From", From);
			model.addAttribute("To", To);
			model.addAttribute("date", date);

			return "Normal/Round_Trip_ViewFlight";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "Normal/Round_Trip_ViewFlight";
		}

	}

	// RoundTrip Booking
	@GetMapping("/Round_Book_flight/{fNo}/{date}")
	public String openRoundBookingPage(@PathVariable("fNo") Long fNo, Model model, @PathVariable("date") String date) {
		Flight flightNo = this.addFlightService.findByFlightNo(fNo);
		model.addAttribute("addFlight", flightNo);
		model.addAttribute("date", date);
		return "Normal/Round_Trip";
	}

	// roundTrip Seat Open
	static Round_Trip roundTrip;

	@PostMapping("/Round_seat")
	public String RoundTripSeatOpen(@ModelAttribute Round_Trip roundTrip_book_Ticket, Model model) {
		List<Ticket> book_Tickets = this.book_TicketService.findAllTicket();
		numticket = book_Tickets.size();
		List<Round_Trip> round_Trips = this.round_TripService.findAllTicket();
		numroundtrip = round_Trips.size();
		round_Trips = round_Trips.stream()
				.filter(t -> (t.getFlightNo() == roundTrip_book_Ticket.getRound_flightNo()
						&& t.getTrivalClass().equals(roundTrip_book_Ticket.getRound_trivalClass())
						&& t.getDate().compareTo(roundTrip_book_Ticket.getRe_date()) == 0))
				.collect(Collectors.toList());

		book_Tickets = book_Tickets.stream()
				.filter(t -> (t.getFlightNo() == roundTrip_book_Ticket.getRound_flightNo()
						&& t.getTrivalClass().equals(roundTrip_book_Ticket.getRound_trivalClass())
						&& t.getDate().compareTo(roundTrip_book_Ticket.getRe_date()) == 0))
				.collect(Collectors.toList());

		if (book_Tickets.size() >= 1 || round_Trips.size() >= 1) {
			List<String> checkSeat = new ArrayList<String>();
			// Book_Ticket
			for (Ticket book_Ticket2 : book_Tickets) {
				for (Object seatAdd : book_Ticket2.getSeatStore()) {
					Seat dd = (Seat) seatAdd;
					checkSeat.add(dd.getItem());
				}
			}
			// RoundTrip BookTicket
			for (Round_Trip round_Trip : round_Trips) {
				for (Object seatAdd : round_Trip.getSeatStore()) {
					Seat dd = (Seat) seatAdd;
					checkSeat.add(dd.getItem());
				}
			}

			model.addAttribute("checkSeat", checkSeat);
		} else {
			model.addAttribute("checkSeat", false);
		}

		try {
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(roundTrip_book_Ticket.getRe_date());
			String day = new SimpleDateFormat("EE").format(d);

			Set<String> set = dat.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (key.equals(day)) {
					da_P = dat.get(key);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Flight addFlight = null;
		if (roundTrip_book_Ticket.getRound_trivalClass().equals("Business")) {
			addFlight = this.addFlightService.findByFlightNo(roundTrip_book_Ticket.getRound_flightNo());
			model.addAttribute("totalSeat", addFlight.getB_Seat());
			roundTrip_book_Ticket.setRound_Price(roundTrip_book_Ticket.getRound_Price() + da_P);

			roundTrip = roundTrip_book_Ticket;
			setBook_t_Data_To_RoundTrip_BookTicket();
			book_t = null;
		} else if (roundTrip_book_Ticket.getRound_trivalClass().equals("Economy")) {

			addFlight = this.addFlightService.findByFlightNo(roundTrip_book_Ticket.getRound_flightNo());
			model.addAttribute("totalSeat", addFlight.getE_seat());
			roundTrip_book_Ticket.setRound_Price(roundTrip_book_Ticket.getRound_Price() + da_P);

			roundTrip = roundTrip_book_Ticket;
			setBook_t_Data_To_RoundTrip_BookTicket();
			book_t = null;
		}
		return "Normal/seat1";
	}

	public void setBook_t_Data_To_RoundTrip_BookTicket() {
		roundTrip.setFlightNo(book_t.getFlightNo());
		roundTrip.setFlightName(book_t.getFlightName());
		roundTrip.setDate(book_t.getDate());
		roundTrip.setTo(book_t.getTo());
		roundTrip.setFrom(book_t.getFrom());
		roundTrip.setDepatureTime(book_t.getDepatureTime());
		roundTrip.setDuration(book_t.getDuration());
		roundTrip.setArivalTime(book_t.getArivalTime());
		roundTrip.setPrice(roundTrip.getPrice() + book_t.getPrice());
		roundTrip.setTrivalClass(book_t.getTrivalClass());
		roundTrip.setTripType(book_t.getTripType());
		roundTrip.setIntermediate(book_t.getIntermediate());
		roundTrip.setPrice(book_t.getPrice());
		roundTrip.setSeatStore(book_t.getSeatStore());
	}

	// RoundTrip view Ticket
	@GetMapping("/Round_view_booking")
	public String viewRoundBooking(Model model, Principal principal, HttpSession session) {
		model.addAttribute("title", "Show Ticket");

		List<Round_Trip> round_Trips = this.round_TripService
				.findRoundBookingsByUser(userService.getUserByUserName(principal.getName()).getuId());
		if (round_Trips.isEmpty()) {
			session.setAttribute("message", new Message("You don't have any booking !! ", "alert-danger"));
		} else {
			model.addAttribute("book_Ticket", round_Trips);
			List<String> dd = new ArrayList<String>();
			for (int i = 0; i < round_Trips.size(); i++) {
				Round_Trip da = round_Trips.get(i);
				for (int j = 0; j < da.getSeatStore().length; j++) {
					Seat add = (Seat) da.getSeatStore()[j];
					dd.add(add.getItem());
				}
				List<String> dd1 = new ArrayList<String>();
				for (int j = 0; j < da.getRound_seatStore().length; j++) {
					Seat add = (Seat) da.getRound_seatStore()[j];
					dd1.add(add.getItem());
				}
				dd.addAll(dd1);
			}
			model.addAttribute("seat", dd);
		}
		return "Normal/view_Booking";
	}

}

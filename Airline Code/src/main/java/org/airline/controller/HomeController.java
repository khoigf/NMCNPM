package org.airline.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.airline.Entity.Flight;
import org.airline.Entity.User;
import org.airline.Service.AddFlightService;
import org.airline.Service.UserService;
import org.airline.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller

public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AddFlightService addFlightService;

	@Autowired
	private JavaMailSender js;

	private boolean b, b1, b2 = true;
	static private String otp;

	// register page handler
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String homePage(Model model) {
		model.addAttribute("title", "Home Page");

		return "home";
	}

	@GetMapping("/signup")
	public String registrationPage(Model model) {
		model.addAttribute("title", "Registration Page");
		model.addAttribute("user", new User());
		return "userregister";
	}

	// data register handeler
	@PostMapping("/Usersignup")
	public String addUser(@ModelAttribute User user, Model model, HttpSession session) {
		try {

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setBalance(500000);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			model.addAttribute("message", "Successfully Added");
			User data = this.userService.saveData(user);
			model.addAttribute("user", new User());
			session.setAttribute("message",
					new Message(data.getName() + "\nSuccessfully Registered !! ", "alert-success"));
			return "userregister";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);

			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "userregister";
		}
	}

	@GetMapping("/book")
	public String showBook() {
		return "index";
	}

	// handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title", "Login Page");
		return "login";
	}

	@GetMapping("/check")
	public String check(Principal principal) {
		String email = principal.getName();

		User user = this.userService.getUserByUserName(email);

		if (user.getRole().equals("ROLE_ADMIN")) {
			return "redirect:/admin/show_flights/0";
		} else {
			return "redirect:/user/user_home";
		}

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

		return "view_flight";
	}

	// find Flight
	@GetMapping("/find_flight")
	public String selectedFlight(@RequestParam("From") String From, @RequestParam("To") String To,
			@RequestParam("date") String date, Model model, HttpSession session) {
		System.out.println(From + "\t" + To + "\t" + date);
		try {
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			String day1 = new SimpleDateFormat("EE").format(d);
			System.out.println(day1);

			List<Flight> findAllFlights = this.addFlightService.findAllFlights();
			boolean b = true;
			findAllFlights = findAllFlights.stream().filter(addFlight -> (addFlight.getFrom().equals(From)
					&& addFlight.getTo().equals(To) && (addFlight.isEnabled() == b))).collect(Collectors.toList());
			List<Flight> flights = null;
			if (findAllFlights.isEmpty()) {
				return "redirect:/view_flight";
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
					return "redirect:/view_flight";
				}
				model.addAttribute("date", date);
				model.addAttribute("addFlights", flights);
			}

			model.addAttribute("title", "View Flight Page");
			List<Flight> addFlights = this.addFlightService.findAllFlights();
			// desable previous date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			model.addAttribute("date", dtf.format(now));

			model.addAttribute("flights", addFlights);

			return "view_flight";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "view_flight";
		}

	}

	// forget Password
	@GetMapping("/forget")
	public String forgetPass(Model model) {
		model.addAttribute("title", "This is Forget Page");
		model.addAttribute("val", b);
		model.addAttribute("sendPass", b1);
		model.addAttribute("sendPass1", b2);
		return "forget.html";
	}

	static User u;

	@PostMapping("/forget")
	public String sendMail1(@RequestParam("email") String email, HttpSession session) {
		User user = this.userService.getUserByUserName(email);
		if (user != null) {
			Random r = new Random();
			String s = "0123456789";
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= 6; i++) {
				sb.append(s.charAt(r.nextInt(s.length())));
			}
			otp = sb.toString();
			session.setAttribute("name", user.getName());
			SimpleMailMessage sen = new SimpleMailMessage();
			sen.setTo(user.getEmail());
			sen.setSubject("OTP For Login Testing");
			sen.setText("Hi " + user.getName() + "\nYour Forget verification Code is \n" + otp);
			js.send(sen);
			b = true;
			b2 = false;
			u = user;
			session.setAttribute("message",
					new Message(user.getName() + "\nYour OTP Send Successfully !! ", "alert-success"));
		} else {
			session.setAttribute("message", new Message("You are Not Register !! ", "alert-danger"));
		}
		return "redirect:/forget";
	}

	@PostMapping("/verify")
	public String verify(@RequestParam("otp") String to, HttpSession session) {
		if (to.equals(otp)) {
			b1 = true;
			b2 = false;
			b = false;
			otp = null;
			session.setAttribute("message", new Message("\nYou Otp match successfully !! ", "alert-success"));
			return "redirect:/forget";
		} else {
			session.setAttribute("message", new Message("Invalid OTP !! ", "alert-danger"));
			return "redirect:/forget";
		}
	}

	@PostMapping("/f_pass")
	public String typePass(@RequestParam("pass") String pass, HttpSession session) {
		if (pass.length() > 4) {
			u.setPassword(this.passwordEncoder.encode(pass));
			this.userService.saveData(u);
			u = null;
			session.setAttribute("message", new Message("\nYou Password Change successfully !! ", "alert-success"));
			return "redirect:/";
		} else {
			session.setAttribute("message", new Message("\nPlease Type Correct Password !! ", "alert-danger"));
			return "redirect:/forget";
		}

	}

}

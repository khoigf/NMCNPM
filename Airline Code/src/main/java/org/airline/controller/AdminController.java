package org.airline.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import org.airline.Entity.Flight;
import org.airline.Entity.User;
import org.airline.Service.AddFlightService;
import org.airline.Service.UserService;
import org.airline.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AddFlightService addFlightService;
	User admin;

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

	@GetMapping("/das")
	public String adminDasboard(Model model) {
		model.addAttribute("title", "Admin DashBoard");
		return "Admin/admin_dasboard";
	}

	@GetMapping("/newadmin")
	public String openNewAdminForm(Model model, Principal principal) {
		model.addAttribute("title", "Update Admin");
		model.addAttribute("user1", this.userService.getUserByUserName((principal.getName())));
		return "Admin/newadmin";
	}

	@PostMapping("/do_register")
	public String createNewAdmin(@ModelAttribute User user12, Model model, HttpSession session, Principal principal) {

		try {
			User u = this.userService.getUserByUserName(principal.getName());

			user12.setRole("ROLE_ADMIN");
			user12.setEnabled(true);
			user12.setBalance(u.getBalance());
			user12.setPassword(u.getPassword());
			User data = this.userService.saveData(user12);
			admin = data;
			model.addAttribute("user1", new User());
			session.setAttribute("message",
					new Message(data.getName() + "\nSuccessfully Updated !! ", "alert-success"));
			return "redirect:/admin/newadmin";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user1", user12);

			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "redirect:/admin/newadmin";
		}
	}

	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");

		return "Admin/profile";
	}

	@GetMapping("/addFlight")
	public String openAddFlightForm(Model model) {
		model.addAttribute("title", "Add Flight");
		model.addAttribute("addFlight", new Flight());
		return "Admin/AddFlight";
	}

	@PostMapping("/add_Flight")
	public String addFlight(@ModelAttribute Flight addFlight, Model model, HttpSession session) {
		try {
			long gId = (long) (Math.random() * 500000);
			addFlight.setFlightNo(gId);
			addFlight.setEnabled(true);
			Flight add_Flight = this.addFlightService.saveFlight(addFlight);
			model.addAttribute("addFlight", new Flight());
			session.setAttribute("message",
					new Message(add_Flight.getFlightName() + "\nSuccessfully Registered !! ", "alert-success"));

			return "Admin/AddFlight";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("addFlight", addFlight);

			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "Admin/AddFlight";
		}

	}

	@GetMapping("/show_flights/{page}")
	public String showFlight(@PathVariable("page") Integer page, Model model) {
		PageRequest pageRequest = PageRequest.of(page, 5);// this is Child class of Pageable interface
		Page<Flight> addFlights = this.addFlightService.findAllFlight(pageRequest);
		model.addAttribute("totalPages", addFlights.getTotalPages());
		model.addAttribute("title", "Admin DashBoard");
		addFlights = new PageImpl<Flight>(addFlights.stream()
				.sorted((c1, c2) -> c1.getFlightName().compareToIgnoreCase(c2.getFlightName()))
				.collect(Collectors.toList()));

		model.addAttribute("addFlights", addFlights);
		model.addAttribute("currentPage", page);
		long num = this.addFlightService.countRecourd();
		model.addAttribute("numberOfFlight", num);
		return "Admin/admin_dasboard";

	}

	@GetMapping("/viewcustomer/{page}")
	public String viewDasboard(@PathVariable("page") Integer page, Model model) {
		PageRequest pageRequest = PageRequest.of(page, 5);
		Page<User> addUsers = this.userService.findAllUser(pageRequest);
		model.addAttribute("totalPages", addUsers.getTotalPages());
		model.addAttribute("title", "View DashBoard");
		addUsers = new PageImpl<User>(addUsers.stream()
				.sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
				.collect(Collectors.toList()));

		model.addAttribute("addUsers", addUsers);
		model.addAttribute("currentPage", page);
		return "Admin/viewcustomer";
	}

	@GetMapping("/delete/{fId}")
	public String deleteFlight(@PathVariable("fId") Long fId, HttpSession session) {
		Flight addFlight = this.addFlightService.findByFlightNo(fId);
		if (addFlight != null) {
			this.addFlightService.deleteFlight(addFlight);
			session.setAttribute("message", new Message("Flight Deleted Successfully...", "alert-success"));

		}
		return "redirect:/admin/show_flights/0";
	}

	// open setting handler
	@GetMapping("/settings")
	public String openSettings(Model model) {
		model.addAttribute("title", "Setting");
		return "Admin/settings";
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
			return "redirect:/admin/settings";
		}
		return "redirect:/logout";
	}

	@GetMapping("/{fId}/flight")
	public String showFlightDetail(@PathVariable("fId") Long fId, Model model) {
		Flight addFlight = this.addFlightService.findByFlightNo(fId);
		model.addAttribute("addFlight", addFlight);
		model.addAttribute("title", addFlight.getFlightName());
		return "Admin/flight_detail";
	}

	@GetMapping("/{femail}/user")
	public String showUserDetail(@PathVariable("femail") String email, Model model) {
		User addUser = this.userService.getUserByUserName(email);
		model.addAttribute("user1", addUser);
		model.addAttribute("title", addUser.getName());
		return "Admin/viewProfile";
	}

	/* flight onOff */
	@PostMapping("/flightOnOf/{fNo}/{cPage}/{onof}")
	public String flightOnOOf(@PathVariable("onof") boolean onof, @PathVariable("fNo") Long fNo,
			@PathVariable("cPage") int cPage, HttpSession session) {

		Flight byFlightNo = this.addFlightService.findByFlightNo(fNo);
		if (onof && byFlightNo.isEnabled()) {
			byFlightNo.setEnabled(false);
			this.addFlightService.saveFlight(byFlightNo);
			session.setAttribute("message",
					new Message(byFlightNo.getFlightName() + "\nSuccessfully Flight Closed!!", "alert-success"));
		} else if (!onof && !byFlightNo.isEnabled()) {
			byFlightNo.setEnabled(true);
			this.addFlightService.saveFlight(byFlightNo);
			session.setAttribute("message",
					new Message(byFlightNo.getFlightName() + "\nSuccessfully Flight Started !! ", "alert-success"));
		}

		return "redirect:/admin/show_flights/" + cPage;
	}

	@PostMapping("/userOnOf/{femail}/{cPage}/{onof}")
	public String userOnOOf(@PathVariable("onof") boolean onof, @PathVariable("femail") String email,
			@PathVariable("cPage") int cPage, HttpSession session) {

		User byEmail = this.userService.getUserByUserName(email);
		if (onof && byEmail.isEnabled()) {
			byEmail.setEnabled(false);
			this.userService.saveData(byEmail);
			session.setAttribute("message",
					new Message(byEmail.getName() + "\nSuccessfully Disable User!!", "alert-success"));
		} else if (!onof && !byEmail.isEnabled()) {
			byEmail.setEnabled(true);
			this.userService.saveData(byEmail);
			session.setAttribute("message",
					new Message(byEmail.getName() + "\nSuccessfully Enable User !! ", "alert-success"));
		}

		return "redirect:/admin/viewcustomer/" + cPage;
	}

	// update flight
	@GetMapping("/update_flight/{fNo}")
	public String openUpdateFlight(@PathVariable("fNo") Long fNo, Model model) {
		Flight addFlight = this.addFlightService.findByFlightNo(fNo);
		model.addAttribute("addFlight", addFlight);
		model.addAttribute("title", "Update Flight");
		return "Admin/update_flight";
	}

	@PostMapping("/update_flight")
	public String updateFlight(@ModelAttribute Flight addFlight, HttpSession session, Model model) {
		try {

			Flight add_Flight = this.addFlightService.saveFlight(addFlight);

			session.setAttribute("message",
					new Message(add_Flight.getFlightName() + "\nSuccessfully Updated !! ", "alert-success"));

			return "redirect:/admin/show_flights/0";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("addFlight", addFlight);

			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "Admin/update_flight";
		}

	}

	String uemail;

	@GetMapping("/editCustomer/{femail}")
	public String openeditCustomer(@PathVariable("femail") String email, Model model) {
		User user = this.userService.getUserByUserName(email);
		uemail = email;

		model.addAttribute("title", "Edit User");
		if (!email.equals("admin@gmail.com")) {
			model.addAttribute("edituser", user);
			return "Admin/editCustomer";
		} else {
			admin = user;
			model.addAttribute("user1", user);
			return "Admin/newadmin";
		}

	}

	@PostMapping("/editCustomer")
	public String editCustomer(@ModelAttribute User user, HttpSession session, Model model) {

		try {
			User user2 = this.userService.getUserByUserName(uemail);
			user2.setName(user.getName());
			user2.setAddress(user.getAddress());
			user2.setGender(user.getGender());
			user2.setMobileNo(user.getMobileNo());
			user2.setAdharNo(user.getAdharNo());
			User user3 = this.userService.saveData(user2);
			user.setName(admin.getName());
			user.setAddress(admin.getAddress());
			user.setGender(admin.getGender());
			user.setMobileNo(admin.getMobileNo());
			user.setAdharNo(admin.getAdharNo());
			this.userService.saveData(user);
			session.setAttribute("message",
					new Message(user3.getName() + "\nSuccessfully Updated !! ", "alert-success"));

			return "redirect:/admin/viewcustomer/0";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("edituser", user);

			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "Admin/editCustomer";
		}

	}

}

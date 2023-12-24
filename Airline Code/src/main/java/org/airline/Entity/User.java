package org.airline.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email_Id", unique = true)
	private String email;
	private String password;
	private String gender;
	@Column(name = "Mobile_Number")
	private String mobileNo;
	@Column(name = "Adhar_Number")
	private String adharNo;
	private String address;
	private String role;
	private boolean enabled;
	private double balance;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ticket> book_Tickets = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Round_Trip> round_Trips = new ArrayList<>();

	public User(Long uId, String name, String email, String password, String gender, String mobileNo, String adharNo,
			String address, String role, boolean enabled, double balance, List<Ticket> book_Tickets,
			List<Round_Trip> round_Trips) {
		this.uId = uId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.adharNo = adharNo;
		this.address = address;
		this.role = role;
		this.enabled = enabled;
		this.balance = balance;
		this.book_Tickets = book_Tickets;
		this.round_Trips = round_Trips;
	}

	public User() {

	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Ticket> getBook_Tickets() {
		return book_Tickets;
	}

	public void setBook_Tickets(List<Ticket> book_Tickets) {
		this.book_Tickets = book_Tickets;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Round_Trip> getRound_Trips() {
		return round_Trips;
	}

	public void setRound_Trips(List<Round_Trip> round_Trips) {
		this.round_Trips = round_Trips;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", mobileNo=" + mobileNo + ", adharNo=" + adharNo + ", address=" + address + ", role=" + role
				+ ", enabled=" + enabled + ", balance=" + balance + "]";
	}

}

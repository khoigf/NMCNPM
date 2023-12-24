package org.airline.Entity;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Flight")
public class Flight {

	@Id
	private long flightNo;
	private String To;
	private String From;
	private String flightName;
	private String departure;
	private String duration;
	private String arrival;
	private double B_price;
	private double E_price;
	private String tripType;
	private String businessClass;
	private String economyClass;
	private int B_Seat;
	private int E_seat;
	private String[] days;
	private String intermediate;
	private String int_arrival_Time;
	private String int_departure_Time;
	private boolean enabled;

	public Flight() {

	}

	public Flight(long flightNo, String to, String from, String flightName, String departure, String duration,
			String arrival, double b_price, double e_price, String tripType, String businessClass, String economyClass,
			int b_Seat, int e_seat, String[] days, String intermediate, String int_arrival_Time,
			String int_departure_Time, boolean enabled) {
		this.flightNo = flightNo;
		To = to;
		From = from;
		this.flightName = flightName;
		this.departure = departure;
		this.duration = duration;
		this.arrival = arrival;
		B_price = b_price;
		E_price = e_price;
		this.tripType = tripType;
		this.businessClass = businessClass;
		this.economyClass = economyClass;
		B_Seat = b_Seat;
		E_seat = e_seat;
		this.days = days;
		this.intermediate = intermediate;
		this.int_arrival_Time = int_arrival_Time;
		this.int_departure_Time = int_departure_Time;
		this.enabled = enabled;
	}

	public long getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(long flightNo) {
		this.flightNo = flightNo;
	}

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public double getB_price() {
		return B_price;
	}

	public void setB_price(double b_price) {
		B_price = b_price;
	}

	public double getE_price() {
		return E_price;
	}

	public void setE_price(double e_price) {
		E_price = e_price;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String getEconomyClass() {
		return economyClass;
	}

	public void setEconomyClass(String economyClass) {
		this.economyClass = economyClass;
	}

	public int getB_Seat() {
		return B_Seat;
	}

	public void setB_Seat(int b_Seat) {
		B_Seat = b_Seat;
	}

	public int getE_seat() {
		return E_seat;
	}

	public void setE_seat(int e_seat) {
		E_seat = e_seat;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}

	public String getInt_arrival_Time() {
		return int_arrival_Time;
	}

	public void setInt_arrival_Time(String int_arrival_Time) {
		this.int_arrival_Time = int_arrival_Time;
	}

	public String getInt_departure_Time() {
		return int_departure_Time;
	}

	public void setInt_departure_Time(String int_departure_Time) {
		this.int_departure_Time = int_departure_Time;
	}

	@Override
	public String toString() {
		return "Flight [flightNo=" + flightNo + ", To=" + To + ", From=" + From + ", flightName=" + flightName
				+ ", departure=" + departure + ", duration=" + duration + ", arrival=" + arrival + ", B_price="
				+ B_price + ", E_price=" + E_price + ", tripType=" + tripType + ", businessClass=" + businessClass
				+ ", economyClass=" + economyClass + ", B_Seat=" + B_Seat + ", E_seat=" + E_seat + ", days="
				+ Arrays.toString(days) + ", intermediate=" + intermediate + ", int_arrival_Time=" + int_arrival_Time
				+ ", int_departure_Time=" + int_departure_Time + ", enabled=" + enabled + "]";
	}

}

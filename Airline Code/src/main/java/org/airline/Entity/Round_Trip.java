package org.airline.Entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Round_Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Ticket_Id")
	private long tId;
	@Column(name = "Flight_Number")
	private long flightNo;
	private long round_flightNo;
	@Column(name = "Flight_Name")
	private String flightName;
	private String round_flightName;
	@Column(name = "F_Date")
	private String date;
	@Column(name = "TO")
	private String to;
	@Column(name = "From")
	private String from;
	private String round_From;
	private String returm_To;
	private String depatureTime;
	private String duration;
	private String arivalTime;
	private String round_depatureTime;
	private String round_duration;
	private String round_arivalTime;
	private double price;
	private double round_Price;
	private String trivalClass;
	private String round_trivalClass;
	private String tripType;
	private String re_date;
	private String intermediate;
	private String round_intermediate;
	private Object[] seatStore;
	private Object[] round_seatStore;
	private String status;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_roundId")
	private User user;

	public Round_Trip(long tId, long flightNo, long round_flightNo, String flightName, String round_flightName,
			String date, String to, String from, String round_From, String returm_To, String depatureTime,
			String duration, String arivalTime, String round_depatureTime, String round_duration,
			String round_arivalTime, double price, String trivalClass, String round_trivalClass, String tripType,
			String re_date, String intermediate, String round_intermediate, Object[] seatStore,
			Object[] round_seatStore, String status, User user) {
		this.tId = tId;
		this.flightNo = flightNo;
		this.round_flightNo = round_flightNo;
		this.flightName = flightName;
		this.round_flightName = round_flightName;
		this.date = date;
		this.to = to;
		this.from = from;
		this.round_From = round_From;
		this.returm_To = returm_To;
		this.depatureTime = depatureTime;
		this.duration = duration;
		this.arivalTime = arivalTime;
		this.round_depatureTime = round_depatureTime;
		this.round_duration = round_duration;
		this.round_arivalTime = round_arivalTime;
		this.price = price;
		this.trivalClass = trivalClass;
		this.round_trivalClass = round_trivalClass;
		this.tripType = tripType;
		this.re_date = re_date;
		this.intermediate = intermediate;
		this.round_intermediate = round_intermediate;
		this.seatStore = seatStore;
		this.round_seatStore = round_seatStore;
		this.status = status;
		this.user = user;
	}

	public Round_Trip() {

	}

	public long gettId() {
		return tId;
	}

	public void settId(long tId) {
		this.tId = tId;
	}

	public long getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(long flightNo) {
		this.flightNo = flightNo;
	}

	public long getRound_flightNo() {
		return round_flightNo;
	}

	public void setRound_flightNo(long round_flightNo) {
		this.round_flightNo = round_flightNo;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getRound_flightName() {
		return round_flightName;
	}

	public void setRound_flightName(String round_flightName) {
		this.round_flightName = round_flightName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRound_From() {
		return round_From;
	}

	public void setRound_From(String round_From) {
		this.round_From = round_From;
	}

	public String getReturm_To() {
		return returm_To;
	}

	public void setReturm_To(String returm_To) {
		this.returm_To = returm_To;
	}

	public String getDepatureTime() {
		return depatureTime;
	}

	public void setDepatureTime(String depatureTime) {
		this.depatureTime = depatureTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getArivalTime() {
		return arivalTime;
	}

	public void setArivalTime(String arivalTime) {
		this.arivalTime = arivalTime;
	}

	public String getRound_depatureTime() {
		return round_depatureTime;
	}

	public void setRound_depatureTime(String round_depatureTime) {
		this.round_depatureTime = round_depatureTime;
	}

	public String getRound_duration() {
		return round_duration;
	}

	public void setRound_duration(String round_duration) {
		this.round_duration = round_duration;
	}

	public String getRound_arivalTime() {
		return round_arivalTime;
	}

	public void setRound_arivalTime(String round_arivalTime) {
		this.round_arivalTime = round_arivalTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRound_Price() {
		return round_Price;
	}

	public void setRound_Price(double round_Price) {
		this.round_Price = round_Price;
	}

	public String getTrivalClass() {
		return trivalClass;
	}

	public void setTrivalClass(String trivalClass) {
		this.trivalClass = trivalClass;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getRe_date() {
		return re_date;
	}

	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}

	public String getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}

	public String getRound_intermediate() {
		return round_intermediate;
	}

	public void setRound_intermediate(String round_intermediate) {
		this.round_intermediate = round_intermediate;
	}

	public Object[] getSeatStore() {
		return seatStore;
	}

	public void setSeatStore(Object[] seatStore) {
		this.seatStore = seatStore;
	}

	public Object[] getRound_seatStore() {
		return round_seatStore;
	}

	public void setRound_seatStore(Object[] round_seatStore) {
		this.round_seatStore = round_seatStore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRound_trivalClass() {
		return round_trivalClass;
	}

	public void setRound_trivalClass(String round_trivalClass) {
		this.round_trivalClass = round_trivalClass;
	}

	@Override
	public String toString() {
		return "Round_Trip [tId=" + tId + ", flightNo=" + flightNo + ", round_flightNo=" + round_flightNo
				+ ", flightName=" + flightName + ", round_flightName=" + round_flightName + ", date=" + date + ", to="
				+ to + ", from=" + from + ", round_From=" + round_From + ", returm_To=" + returm_To + ", depatureTime="
				+ depatureTime + ", duration=" + duration + ", arivalTime=" + arivalTime + ", round_depatureTime="
				+ round_depatureTime + ", round_duration=" + round_duration + ", round_arivalTime=" + round_arivalTime
				+ ", price=" + price + ", round_Price=" + round_Price + ", trivalClass=" + trivalClass
				+ ", round_trivalClass=" + round_trivalClass + ", tripType=" + tripType + ", re_date=" + re_date
				+ ", intermediate=" + intermediate + ", round_intermediate=" + round_intermediate + ", seatStore="
				+ Arrays.toString(seatStore) + ", round_seatStore=" + Arrays.toString(round_seatStore) + ", status="
				+ status + "]";
	}

}

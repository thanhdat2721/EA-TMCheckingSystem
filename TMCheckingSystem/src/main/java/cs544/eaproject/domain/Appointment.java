package cs544.eaproject.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Appointment {
	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private LocalDate dateTime;

	@Column(nullable = false)
	private String location;

	@OneToMany(mappedBy = "appointment",cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	@ManyToOne
	private User provider;

	public Appointment() {
	}

	public Appointment(LocalDate dateTime, String location, User provider) {
		this.dateTime = dateTime;
		this.location = location;
		this.provider = provider;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDate dateTime) {
		this.dateTime = dateTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getProvider() {
		return provider;
	}

	public void setProvider(User provider) {
		this.provider = provider;
	}

	public List<Reservation> getReservations() {
		return Collections.unmodifiableList(reservations);
	}

	public void addReservation(Reservation reservation) {
		// add this
		reservation.setAppointment(this);
		reservations.add(reservation);

	}
}

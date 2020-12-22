package com.example.DbProject1;

/**
 * This is data object class which is used to make 
 * appointment between a doctor and patient.
 *
 */
public class Appointment {

	private String appointmentId;
	private String patientId;
	private User doctor;
	private String status;
	private String date;
	
	/**
	 * This is an empty constructor that initilises 
	 * the Appointment with values that I get from the view.
	 */
	Appointment(){
		
	}
	/**
	 * Creates a appointment object for paitent who wants an appointment with doctor.
	 * @param appointmentId appointment Id of the appointment
	 * @param patientId patient Id who makes the appointment
	 * @param doctor   doctor who is booked for an appointment
	 * @param status   status of the appointment
	 * @param date    date of the appointment
	 */
	public Appointment(String appointmentId, String patientId, User doctor, String status, String date) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.doctor = doctor;
		this.status = status;
		this.date = date;
	}

	/**
	 * Returns the appointment Id.
	 * @return returns appointment
	 */
	public String getAppointmentId() {
		return appointmentId;
	}
	/**
	 * Sets the appointmentId.
	 * @param appointmentId
	 */
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	/**
	 * Gets the patient Id.
	 * @return returns patient Id
	 */
	public String getPatientId() {
		return patientId;
	}
	/**
	 * Sets the patientId.
	 * @param patientId new patientId
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	/**
	 * Gets the doctor Id.
	 * @return returns doctor Id
	 */
	public User getDoctor() {
		return doctor;
	}
	/**
	 * Sets the doctor.
	 * @param doctor new doctor
	 */
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	/**
	 * Gets the status of the appointment.
	 * @return returns status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status.
	 * @param doctor new doctor
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Gets the data of the appointment.
	 * @return returns data of appointment.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Sets the data of the appointment.
	 * @param date new date of appointment
	 */
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", patientId=" + patientId + ", doctor=" + doctor
				+ ", status=" + status + ", date=" + date + "]";
	}
	
	
	
}

package com.example.DbProject1;

/**
 * This represents the patient who is also a user. 
 * This data object class for users who register as patient.
 * @author ujjvalsharma
 *
 */
public class Patient extends User{

	private String password;
	/**
	 * This is an empty constructs that sets default value feild values.
	 */
	Patient(){
		password=null;
	}
/**
 * This constructs a patient who is registered as a patient in the application	
 * @param firstName first name of patient
 * @param lastName last name of patient
 * @param email    email of patient
 * @param password   password of patient
 * @param id    id of patient
 */
Patient(String firstName, String lastName, String email, String password, int id){
		super(firstName,lastName,email,id);
		this.password=password;
	}
/**
 * Gets the password of patient
 * @return returns password
 */
public String getPassword() {
	return password;
}
/**
 * Sets the password of patient
 * @param password new password of the patient.
 */
public void setPassword(String password) {
	this.password = password;
}

@Override
public String toString() {
	return "Patient [password=" + password + "]";
}
	

}

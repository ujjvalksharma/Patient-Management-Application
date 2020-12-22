package com.example.DbProject1;

/**
 * This is data object class that represents a user
 *  is registering as a patient or doctor.
 * @author ujjvalsharma
 *
 */
public class User {
	
private int id=0;
private String firstName;
private String lastName;
private String email;

/**
 * This constructs user who logs in the application or
 *  who is viewed in the application.
 */
User(){
	id=-1;
	firstName=null;
	lastName=null;
	email=null;
}
/**
 * This constructs User with some parameters
 * @param firstName first name of the user
 * @param lastName last name of the user
 * @param email email of the user
 * @param id id of the user
 */
public User( String firstName, String lastName, String email,int id) {
	super();
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
}

/**
 * Gets the Id of user.
 * @return id of the user
 */
public int getId() {
	return id;
}
/**
 * Sets the user id to new id.
 * @param id new id of patient
 */
public void setId(int id) {
	this.id = id;
}

/**
 * Gets the first name of user.
 * @return first name of the user
 */
public String getFirstName() {
	return firstName;
}
/**
 * Sets the user first name to new first name.
 * @param firstName new first name of patient
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * Gets the last name of user.
 * @return last name of the user
 */
public String getLastName() {
	return lastName;
}
/**
 * Sets the user last name to new last name.
 * @param lastName new last name of patient
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}

/**
 * Gets the email of the user.
 * @return email of the user
 */
public String getEmail() {
	return email;
}
/**
 * Sets the user email to new email.
 * @param email new email of the user.
 */
public void setEmail(String email) {
	this.email = email;
}
@Override
public String toString() {
	return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
}


}

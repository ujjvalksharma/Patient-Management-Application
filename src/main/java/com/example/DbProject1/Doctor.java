package com.example.DbProject1;

/**
 * This represent a user who is registered as a doctor in the application.
 *
 */
public class Doctor extends User {

	String departmentId;
	/**
	 * This constructs the doctor with default values.
	 */
	Doctor(){
		departmentId=null;
	}
	/**
	 * This constructs a doctor with values given in the paramter.
	 * @param departmentId department Id of the doctor
	 * @param firstName first name of the doctor
	 * @param lastName last name of the doctor
	 * @param email email of the doctor
	 * @param id id of doctor
	 */
Doctor(String departmentId,String firstName, String lastName, String email,int id){
	super(firstName,lastName,email,id);
	this.departmentId=departmentId;
	
	}
/**
 * This constructs a doctor whose department id is not 
 * know or not needed for the model.
 * 
 * @param firstName first name of the doctor
 * @param lastName last name of the doctor
 * @param email email of the doctor
 * @param id id of doctor
 */
Doctor(String firstName, String lastName, String email,int id){
	super(firstName,lastName,email,id);
	this.departmentId=null;
	
	}
/**
 * Gets the deparment Id.
 * @return deoartment Id.
 */
public String getDepartmentId() {
	return departmentId;
}
/**
 * Sets the department Id to new department Id.
 * @param departmentId new departmet Id
 */
public void setDepartmentId(String departmentId) {
	this.departmentId = departmentId;
}
@Override
public String toString() {
	return "Doctor [departmentId=" + departmentId + "]";
}
	

}

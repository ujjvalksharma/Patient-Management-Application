package com.example.DbProject1;
/**
 * This is data object class that represents search criteria 
 * to search doctors and department.
 *
 */
public class DoctorSearchCriteria {

	private String firstName;
	private String lastName;
	private String doctorId;
	private String departmentId;
	
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
	 * Gets the doctor Id.
	 * @return id of the doctor
	 */
	public String getDoctorId() {
		return doctorId;
	}
	/**
	 * Sets the doctor to new doctor Id.
	 * @param doctorId new doctor Id
	 */
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
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
		return "DoctorSearchCriteria [firstName=" + firstName + ", lastName=" + lastName + ", doctorId=" + doctorId
				+ ", departmentId=" + departmentId + "]";
	}
	
}

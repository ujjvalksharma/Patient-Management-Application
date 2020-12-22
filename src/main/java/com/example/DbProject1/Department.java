package com.example.DbProject1;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a department in which doctors are registered. 
 * Each department has its Id and name which uniquelt identify the department.
 *
 */
public class Department {

	private int id;
	private String name;
	private List<Doctor> users;
	
	/**
	 * This is constructs a department with default values.
	 */
	public	Department(){
		users=new ArrayList<>();
		name=null;
		id=0;
	}
	/**
	 * This is constructs a department with the values provided in the paramter. 
	 * @param id id of the department
	 * @param name name of the department
	 * @param users list of doctors present in deparmtent
	 */
	public Department(int id, String name, List<Doctor> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}
/**
 * Gets the department id.
 * @return department id
 */
	public int getId() {
		return id;
	}
	/**
	 * Sets the id of the department.
	 * @param id new id of the department
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Gets the department  name.
	 * @return department name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets name of department.
	 * @param name new name of department
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the list of doctors.
	 * @return list of doctors
	 */
	public List<Doctor> getUsers() {
		return users;
	}
	/**
	 * Sets the doctor's list.
	 * @param users doctor in the department
	 */
	public void setUsers(List<Doctor> users) {
		this.users = users;
	}
	/**
	 * Adds a new doctor to the list of doctor
	 * @param user new doctor to be added
	 */
	public void addDoctor(Doctor user) {
		users.add(user);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", doctors=" + users + "]";
	}
	
	
}

package com.example.DbProject1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * This is a service that interacts with data class to perform operations on it.
 *  It is used to make request to data class and repository class to
 *   perform database operations.
 *
 */
@Service
public class UserService {

	/**
	 * Insertes patient into database.
	 * @param patient patient
	 * @return true is inserted else false
	 */
	public boolean insert(Patient patient) {
		
		try {
			
			String sql="CALL `Insert_Patient`"
					+ "('"+patient.getFirstName()+"',"
					+ " '"+patient.getLastName()+"',"
					+ " '"+patient.getEmail()+"',"
					+ "'"+patient.getPassword()+"')";
			System.out.println(sql);
			DbRepository.getMySqlStatement()
			.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
/**
 * Checks if patient is user of the application
 * @param patient paitent object
 * @return true if it is a user else false
 */
	public boolean checkUser(Patient patient) {
		
		String sql="Select `patientCount`"
				+ " ('"+patient.getEmail()+"',"
				+ "'"+patient.getPassword()+"') as totalUsers";
		try {
			System.out.println(sql);
		ResultSet results =DbRepository.getMySqlStatement()
		.executeQuery(sql);
		int totalUsers=0;
		while (results.next()) {
			totalUsers = Integer.parseInt(results.getString("totalUsers"));
		} 
		if(totalUsers==1) {
			return true;
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Find all the details of the patient on basis of email and password
	 * @param email email of patient
	 * @param password password of patient
	 * @return Patient object
	 */
	  public Patient findPatient(String email,String password) {
			String sql="CALL `Get_Patient_Details`('"+email+"','"+password+"');";
			try {
				System.out.println(sql);
			ResultSet results =DbRepository.getMySqlStatement()
					.executeQuery(sql);
			
			while (results.next()) {
				int id=Integer.parseInt(results.getString("id"));
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				        email = results.getString("email");
				     password = results.getString("password");
				     Patient tempPatient= new Patient(firstName,lastName,email,password,id);
	
				HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("id", ""+id);
				hm.put("firstName",firstName);
				hm.put("lastName",lastName);
				hm.put("password",password);
				DbRepository.getRedisConn().hset(email, hm);
				return tempPatient;
			} 
		
			
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
/**
 * finds all the department based on the search criteria
 * @param doctorSearchCriteria search criteria to search doctors
 * @return list of departments
 */
	public List<Department> findDepartmentAndDoctor(DoctorSearchCriteria doctorSearchCriteria) {
		List<Department> departments=new ArrayList<>();
		StringBuilder sql=new StringBuilder(" SELECT * "
				+ " FROM User "
				+ " INNER JOIN Doctor "
				+ " ON Doctor.doctorId = User.id "
				+ " INNER JOIN Department "
				+ " ON department.id = Doctor.departmentId "
				+ " where 1=1 ");
				if(doctorSearchCriteria.getDoctorId()!=null) {
				sql.append(" and Doctor.doctorId like'%"+doctorSearchCriteria.getDoctorId()+"%' ");
				}
				if(doctorSearchCriteria.getFirstName()!=null) {
				sql.append(" and  User.firstName like'%"+doctorSearchCriteria.getFirstName()+"%' ");
				}
				if(doctorSearchCriteria.getFirstName()!=null) {
				sql.append("  and  User.lastName like'%"+doctorSearchCriteria.getLastName()+"%' ");
				}
				if(doctorSearchCriteria.getDepartmentId()!=null) {
				sql.append("  and  department.id like '%"+doctorSearchCriteria.getDepartmentId()+"%' ");
				}
				sql.append(" order by department.departmentName asc");
				
		
		try {
			System.out.println(sql);
			ResultSet results =DbRepository.getMySqlStatement()
					.executeQuery(sql.toString());
			
			HashMap<String,Integer> depNameToIndex=new HashMap<>();
			
			while (results.next()) {
				String departmentName=results.getString("departmentName");
				if(depNameToIndex.containsKey(departmentName)==false) {
					depNameToIndex.put(departmentName, departments.size());
					List<Doctor> doctors=new ArrayList<>();
					Department department=new Department
				(
				Integer.parseInt(results.getString("departmentId")),
				results.getString("departmentName"),
				doctors
				);
					departments.add(department);
				}
				
				departments
				.get(depNameToIndex.get(results.getString("departmentName")))
				.addDoctor(new 
						Doctor(results.getString("firstName"),
					results.getString("lastName"),
					results.getString("email"),
					Integer.parseInt(results.getString("id"))));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return departments;
	}
/**
 * Deletes user's account from database and Redis.
 * @param userId user id of patient
 * @param email email of of the patient
 */
	public void delete(int userId,String email) {
		
		String sql="delete from user where id='"+userId+"'";
		DbRepository.getRedisConn().del(email);
		try {
			System.out.println(sql);
			DbRepository.getMySqlStatement()
			.execute(sql);
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/**
 * Updates User details in database and Redis.
 * @param userId user id of the patient
 * @param patient patient object
 * @param email email if of the patient
 */
	public void updateUser(int userId,Patient patient,String email) {
		StringBuilder sql=new StringBuilder(" Update User set " );
				if(patient.getFirstName()!=null&&patient.getFirstName().length()>0) {
					System.out.println(email+"firstName->"+patient.getFirstName());
					DbRepository.getRedisConn().hset(email, "firstName", patient.getFirstName());
					System.out.println("done");
				sql.append("  firstName='"+patient.getFirstName()+"'");
				}
				if(patient.getLastName()!=null&&patient.getLastName().length()>0) {
					if(patient.getFirstName()!=null&&patient.getFirstName().length()>0) {
						
						sql.append(", lastName='"+patient.getLastName()+"'");
					}
					else {
						sql.append(" lastName='"+patient.getLastName()+"'");
					}
					DbRepository.getRedisConn().hset(email, "lastName", patient.getLastName());
					
					}
					sql.append(" where id='"+userId+"'");
					System.out.println("Update User table="+sql);
					try {
						DbRepository.getMySqlStatement()
						.execute(sql.toString());
						sql=new StringBuilder(" Update Patient set " );
						if(patient.getPassword()!=null&&patient.getPassword().length()>0) {
							DbRepository.getRedisConn().hset(email, "password", patient.getPassword());
							sql.append(" password='"+patient.getPassword()+"'");
							sql.append(" where patientId='"+userId+"'");
							System.out.println(sql);
							DbRepository.getMySqlStatement()
							.execute(sql.toString());
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}
/**
 * Books appointment between user and doctor on a selected date.
 * @param userId userId of the user
 * @param doctorId doctor id
 * @param apppointmentDate appointment date.
 * @return
 */
	public boolean bookAppointment(int userId,int doctorId,String apppointmentDate) {
		
		
		String sql="Insert into Appointment (patientId, doctorId, status,dateOfAppointment) \n"
				+ "values ('"+userId+"','"+doctorId+"','Pending','"+apppointmentDate+"');";
		
		System.out.println(sql);
		try {
		int results =DbRepository.getMySqlStatement()
		.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
/**
 * Makes payment of an appointment.
 * @param appointmentId appointment Id whose payment has been made
 */
	public void makePayment(int appointmentId) {
		String sql="CALL `Add_Default_Invoice`('"+appointmentId+"')";
		try {
			System.out.println(sql);
			DbRepository.getMySqlStatement()
			.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Gets appointment of a user
	 * @param userId user id of the patient.
	 * @return
	 */
	public List<Appointment> getAppointment(int userId) {
		
		List<Appointment> appointments=new ArrayList<>();
		String sql=" CALL `Get_Pending_Details`('"+userId+"');";
		ResultSet results;
		try {
			System.out.println(sql);
			results = DbRepository.getMySqlStatement()
						.executeQuery(sql);
			while (results.next()) {
				int id=Integer.parseInt(results.getString("id"));
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				String    email = results.getString("email");
				String dateOfAppointment = results.getString("dateOfAppointment");
				Doctor doctor= new Doctor(firstName,lastName,email,id);
				
				appointments.add(
						new 
						Appointment(
						results.getString("appointmentId")
						,results.getString("patientId")
						,doctor
						,results.getString("status"),dateOfAppointment));	
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 
	return appointments;
	}
/**
 * Ges all appointment details based on userId
 * @param userId userId to fetch appointment details
 * @return
 */
	public List<Appointment> getAllAppointment(int userId) {
		List<Appointment> appointments=new ArrayList<>();
		String sql="CALL `Get_All_Appointment`('"+userId+"')";
		ResultSet results;
		try {
			System.out.println(sql);
			results = DbRepository.getMySqlStatement()
						.executeQuery(sql);
			while (results.next()) {
				int id=Integer.parseInt(results.getString("id"));
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				String    email = results.getString("email");
				String dateOfAppointment = results.getString("dateOfAppointment");
				
				Doctor doctor= new Doctor(firstName,lastName,email,id);
				
				appointments.add(
						new 
						Appointment(
						results.getString("appointmentId")
						,results.getString("patientId")
						,doctor
						,results.getString("status"),dateOfAppointment));	
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	return appointments;
	}
/**
 * Deletes an appointment 
 * @param appointmentId appointment Id
 */
	public void deleteAppointment(int appointmentId) {
		String sql="delete from Appointment where appointmentId='"+appointmentId+"'";
		try {
			System.out.println(sql);
			DbRepository.getMySqlStatement()
			.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/**
 * Finds doctor details.
 * @param doctorId doctor Id
 * @return
 */
	public Doctor findDoctor(int doctorId) {
		String sql="select "
				+ " * "
				+ "from User "
				+ "where "
				+ " id='"+doctorId+"'";
		try {
			System.out.println(sql);
		ResultSet results =DbRepository.getMySqlStatement()
				.executeQuery(sql);
		while (results.next()) {
			String email=results.getString("email");
			String firstName = results.getString("firstName");
			String lastName = results.getString("lastName");
			doctorId = Integer.parseInt(results.getString("id"));
			Doctor doctor= new Doctor(firstName,lastName,email,doctorId);
			return doctor;
		} 
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
/**
 * Updates an appointment in db.
 * @param appointmentId appointment id
 * @param appointmentDate appointment date
 * @return
 */
	public boolean updateAppointment(int appointmentId, String appointmentDate) {

		String sql="Select UpdateAppointment('"+appointmentId+"','"+appointmentDate+"') as boolUpdatedAppointment";

		System.out.println(sql);
		try {
		ResultSet results =DbRepository.getMySqlStatement()
		.executeQuery(sql);
		int boolUpdatedAppointment=0;
		while (results.next()) {
			boolUpdatedAppointment = Integer.parseInt(results.getString("boolUpdatedAppointment"));
		} 
		System.out.println("boolUpdatedAppointment->"+boolUpdatedAppointment);
		if(boolUpdatedAppointment==0) {
			return false;
		}
		

			return true;
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
/**
 * Verifies if patient is present in Redis db.
 * @param patient patient object
 * @return true if present else false
 */
	public boolean checkPatientInCache(Patient patient) {
     if(DbRepository.getRedisConn().hget(patient.getEmail(), "id") != null) {	
    	 System.out.println("Redis found the patient");
		return true;
     }
     System.out.println("Redis could not find the patient");
     return false;
	}
/**
 * Find Patient details from cache on the basis of email and password.
 * @param email email of the patient
 * @param password password of the patient
 * @return Patien object.
 */
	public Patient findPatientFromCache(String email,String password) {
		Map<String, String> mapPatientFieldToValue=DbRepository.getRedisConn().hgetAll(email);
		
		if(!password.equals(mapPatientFieldToValue.get("password"))) {
			System.out.println("Redis could match the username and password");
			return null;
		}
		System.out.println("Redis is returning patient details");
		return new Patient(mapPatientFieldToValue.get("firstName"),
				mapPatientFieldToValue.get("lastName"),
				email,
				mapPatientFieldToValue.get("password"),
				Integer.parseInt(mapPatientFieldToValue.get("id")));
		
	}


	
	
}

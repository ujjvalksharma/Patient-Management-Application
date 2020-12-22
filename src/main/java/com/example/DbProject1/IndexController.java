package com.example.DbProject1;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This represents the index controller which handle all the 
 * incoming requirest and then interact with the service class 
 * to which will interact with data object to perform operations.
 * 
 * @author ujjvalsharma
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	UserService userService;
	
	/**
	 * Return index page to start the application.
	 * @return returns the index page.
	 */
	@GetMapping("/")
  public String start() {
	        return "index";	
  }
	 
  /**
   * Returns signUp page when user requests it.
   * @param model model in patient is patient object is inserted
   * @return signUp view
   */
  @GetMapping("/SignUp")
  public String getSignUp(Model model) {
	  Patient patient=new Patient();
	  model.addAttribute("patient", patient);
	        return "SignUp";	
  }
  
  /**
   * Insert data into database if a new user has signup else email must 
   * have been used earlier, so return to signUp.
   * 
   * @param patient patient model that we get after signUp is completed
   * @return login page if data is inserted else signUp page
   */
  @PostMapping("/Login")
  public String postSignUp(@ModelAttribute("patient") Patient patient) {
	 if(userService.insert(patient)) {
	        return "redirect:Login/?error=false";	
	 }
	 return "redirect:SignUp/?error=true";
  }
  /**
   * Returns login page.
   * @param model model for paitent details in login page
   * @return login page
   */
  @RequestMapping("/Login")
  public String getLogin(Model model) {
	  Patient patient=new Patient();
	  model.addAttribute("patient", patient);
	        return "Login";	
  }
  
  /**
   * Request a home page.
   * @param model model in appointments are added for home page
   * @param session session is created as user has entered the login page.
   * @return home page.
   */
  @GetMapping("/Home")
  public String getHome(Model model,HttpSession session) {
	  Patient patient=(Patient) session.getAttribute("patient");
	  List<Appointment> appointments=userService.getAppointment(patient.getId());
	  model.addAttribute("appointments",appointments);
	        return "Home";	
  }
  /**
   * Request a home page after login. If credentials are incorrect then user is redirected to login page.
   * @param patient patient whose credentials where entered
   * @param model model for the home page
   * @param session session is created on entering the home page.
   * @return home page if credentials are correct else login page.
   */
  @PostMapping("/Home")
  public String postLogin(@ModelAttribute("patient") Patient patient,Model model,HttpSession session) {
	
		 if(userService.checkPatientInCache(patient)) {
		 
		   
		   patient=userService.findPatientFromCache(patient.getEmail(),patient.getPassword());
		 }
		 else {
			  patient=userService.findPatient(patient.getEmail(),patient.getPassword());
			   
		 }
		 
		 if(patient==null) {
			 return "redirect:Login/?error=true";
		 }
		 
		 session.setAttribute("patient", patient);
		  return getHome(model, session);
	  
	 
  }
  
  /**
   * Returns a view to enter criteria to search doctors and departments.
   * @param model model to add doctor details
   * @return view for doctors
   */
  @RequestMapping("/ViewDoctors")
  public String searchDoctorsAndDepartment(Model model) {
	  DoctorSearchCriteria doctorSearchCriteria=new DoctorSearchCriteria();
	  model.addAttribute("doctorSearchCriteria", doctorSearchCriteria);
	        return "ViewDoctors";	
  }
/**
 * Returns list of doctor after the search criteria has been entered
 * @param doctorSearchCriteria criteria to search doctor
 * @param model model that contains department details
 * @return
 */
  @PostMapping("/ViewDoctorsResult")
  public String getDoctorsAndDepartment (@ModelAttribute("doctorSearchCriteria")
  DoctorSearchCriteria doctorSearchCriteria,Model model) {
	  List<Department> departments=userService.findDepartmentAndDoctor( doctorSearchCriteria);
	  model.addAttribute("departments", departments);
	        return "ViewDoctorsResult";	
  }
/**
 * Gets the page to delete account.
 * @return
 */
  @GetMapping("/DeleteAccount")
  public String getdeleteAccount() {
	        return "DeleteAccount";	
  }

  /**
   * Deletes the user from the application.
   * @param userId user id which is deleted
   * @param session session session details of the user
   * @return
   */
  @PostMapping("/DeleteAccount/{userId}")
  public String postdeleteAccount(@PathVariable("userId") int userId,HttpSession session) {
	  Patient patient=(Patient) session.getAttribute("patient");
	  userService.delete(userId,patient.getEmail());
	        return "redirect:../SignUp";	
  }
  /**
   * Updates the paitent details.
   * @param userId user id of the patient
   * @param Updatedpatient updated details of the patient
   * @param session session which to set updated session detials
   * @return
   */
  @PostMapping("/UpdatePatient/{userId}")
  public String postUpdateUser(@PathVariable("userId") int userId,@ModelAttribute("patient") Patient Updatedpatient,HttpSession session) {
	  Patient patient=(Patient)session.getAttribute("patient");
	  userService.updateUser(userId,Updatedpatient,patient.getEmail());
	  
	  if(Updatedpatient.getFirstName()!=null&&Updatedpatient.getFirstName().length()>0) {
		  patient.setFirstName(Updatedpatient.getFirstName()); 
	  }
	  if(Updatedpatient.getLastName()!=null&&Updatedpatient.getLastName().length()>0) {
		  patient.setLastName(Updatedpatient.getLastName()); 
	  }
	  if(Updatedpatient.getPassword()!=null&&Updatedpatient.getPassword().length()>0) {
		  patient.setPassword(Updatedpatient.getPassword()); 
	  }
	  session.setAttribute("patient", patient);
	  return "redirect:../Home";
	  }

  /**
   * Gets the page to update paitent details
   * @param model model to add patient
   * @return returns UpdatePatient page
   */
  @GetMapping("/UpdatePatient")
  public String getUpdateUser(Model model) {
	  Patient patient=new Patient();
	  model.addAttribute("patient",patient);
	        return "UpdatePatient";	
	  }
  
  /**
   * Books appointment of the patient.
   * @param doctorId doctor id of doctor
   * @param session session to get patient details
   * @param appointment appointment object that has appointment details
   * @return
   */
  @PostMapping("/BookAppointment/{doctorId}")
  public String getAppointment(@PathVariable("doctorId") int doctorId,HttpSession session,@ModelAttribute("appointment") Appointment appointment,Model model) {
	  Patient patient=(Patient) session.getAttribute("patient");
	  if(!userService.bookAppointment(patient.getId(),doctorId,appointment.getDate())) {
		  return "redirect:/BookAppointment/"+doctorId+"/"+"?book=false";
	  }
	  return "redirect:../Home/?book=true";
  }
  /**
   * Requests appointment page to booke appointment with doctor.
   * @param doctorId doctorId of the doctor
   * @param session session to store session details
   * @param model model to store details of appointment and doctor
   * @return BookAppointment page
   */
  @RequestMapping("/BookAppointment/{doctorId}")
  public String setAppointment(@PathVariable("doctorId") int doctorId,HttpSession session,Model model) {
	  Doctor doctor=userService.findDoctor(doctorId);
	  Appointment appointment=new Appointment();
	  model.addAttribute("appointment", appointment);
	  model.addAttribute("doctor", doctor);
	  return "BookAppointment";
  }
  
  /**
   * Request ViewAllAppointments page to view all appointments.
   * @param model model to get all appointments
   * @param session session to get patient details
   * @return returns ViewAllAppointments page.
   */
  @GetMapping("/ViewAllAppointments")
  public String getAllAppointments(Model model,HttpSession session) {
	
	  Patient patient=(Patient) session.getAttribute("patient");
	  List<Appointment> appointments=userService.getAllAppointment(patient.getId());
	  model.addAttribute("appointments",appointments);
	  return "ViewAllAppointments";
  }
  /**
   * This is used to an delete Appointment.
   * @param appointmentId appointment id that has to be deleted
   * @param model model to show patient details
   * @param session session to get patient details
   * @return
   */
  @RequestMapping("/DeleteAppointment/{appointmentId}")
  public String deleteAppointment(@PathVariable("appointmentId") int appointmentId,Model model,HttpSession session) {
	  
	 userService.deleteAppointment(appointmentId);
	  return "redirect:../Home";
	  
  }
  
 /**
  * This is used to edit appointment.
  * @param appointmentId appointment that has be edited
  * @param model model to add appointment details
  * @return returns EditAppointment page.
  */
  @RequestMapping("/EditAppointmentTime/{appointmentId}")
  public String editAppointment(@PathVariable("appointmentId") int appointmentId,Model model) {
	  
	  model.addAttribute("appointmentId", appointmentId);
	  Appointment appointment=new Appointment();
	  model.addAttribute("appointment", appointment);
	  return "EditAppointment";
	  
  }
  
  /**
   * This called after an appointment has been edited
   * @param appointmentId appointment Id 
   * @param appointment appointment details
   * @return
   */
  @PostMapping("/EditAppointmentTime/{appointmentId}")
  public String postUpdateAppointment(@PathVariable("appointmentId") int appointmentId,@ModelAttribute("appointment") Appointment appointment) {
	  
	if(!userService.updateAppointment(appointmentId,appointment.getDate())) {
		return "redirect:/EditAppointmentTime/"+appointmentId+"/"+"?editApp=false";
	}
	
	 return "redirect:../Home/?editApp=true";
  }
  
  /**
   * This used to set the payment of an appointment
   * @param appointmentId appointment Id for whom payment is set.
   * @return
   */
  @RequestMapping("/Payment/{appointmentId}")
  public String setPayment(@PathVariable("appointmentId") int appointmentId) {
	  userService.makePayment(appointmentId);
	  return "redirect:../Home";
  }
  
  
  
  }


package cs544.eaproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cs544.eaproject.dao.UserDAO;
import cs544.eaproject.domain.Appointment;
import cs544.eaproject.domain.User;
import cs544.eaproject.service.AppointmentService;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {
	@Autowired
	AppointmentService appointmentService;

	@Autowired
	UserDAO userDao;

	@GetMapping
	public List<Appointment> getAllAppointment() {
		return appointmentService.viewAppointments();
	}
	
	@GetMapping("/provider/{id}")
	public List<Appointment> getAllAppointmentByProvider(@PathVariable long id) {
		return appointmentService.viewAppointmentsByProvider(id);
	}


	@RequestMapping(value ="/delete/{appointmentId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long appointmentId) throws Exception{


		try {
		appointmentService.delete(appointmentId);
		return "Appointment removed!";
		}catch(Exception e) {
		return "No appointment found!";	
		}
		

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	@ResponseBody
	public Appointment createAppointment(@RequestBody Appointment appointmentDTO) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User current_user = userDao.findByEmail(auth.getName());

//			if(!((Role) current_user.getUserRole()).getRoleName().equals("CHECKER")) {
//				throw new Exception("");
//			}

		appointmentDTO.setProvider(current_user);
		Appointment appointment = appointmentService.createAppointment(appointmentDTO);

		return appointment;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

}

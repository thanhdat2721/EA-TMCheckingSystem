package cs544.eaproject.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import cs544.eaproject.domain.Appointment;
import cs544.eaproject.domain.Role;
import cs544.eaproject.domain.User;
import cs544.eaproject.repository.AppointmentRepository;
import cs544.eaproject.service.dto.AppointmentDto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceTest {

    private User user;
    private Appointment appointment;
    
	@Autowired
	private ModelMapper modelMapper;

    @InjectMocks
    private AppointmentService appointmentService = new AppointmentServiceImpl();

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        //given
    	Role roleProvider = new Role("Provider");
    	user= new User("Karim", "Salama", "Male", "ksalama@miu.edu", roleProvider , "123", "ksalama");
    	
    	Date date = new GregorianCalendar(2020, Calendar.MAY, 15).getTime();
		appointment = new Appointment(date, "Virall Room32", user);

        //mocking
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
    }

    @Test
    public void test1() {
    	
        AppointmentDto created = appointmentService.createAppointment(modelMapper.map(appointment, AppointmentDto.class));

        assertEquals(user.getEmail(), created.getProvider().getEmail());
    }
}
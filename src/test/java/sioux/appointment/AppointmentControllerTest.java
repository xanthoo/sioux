package sioux.appointment;

import org.junit.jupiter.api.Test;
import sioux.visitor.Visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentControllerTest {

    AppointmentController appointmentController = new AppointmentController(new AppointmentMemoryRepository());

    @Test
    void getAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 2", 2, LocalDateTime.now(), LocalDate.now(),new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 3", 3, LocalDateTime.now(), LocalDate.now(), new Visitor(2, "Jan", "456-def-78", "0690123456", "Brings his assistant with him.")));

        List<Appointment> appointmentListMemory = appointmentController.getAppointments();

        for (int i =0; i < appointmentList.size(); i++ ){
            assertEquals(appointmentList.get(i).getId(), appointmentListMemory.get(i).getId());
            assertEquals(appointmentList.get(i).getStart(), appointmentListMemory.get(i).getStart());
            assertEquals(appointmentList.get(i).getEnd(), appointmentListMemory.get(i).getEnd());
            assertEquals(appointmentList.get(i).getSubject(), appointmentListMemory.get(i).getSubject());
            assertEquals(appointmentList.get(i).getVisitor().getVisitorID(), appointmentListMemory.get(i).getVisitor().getVisitorID());

        }

    }

    @Test
    void getAppointmentById() {
        Appointment appointment = new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        Appointment appointmentMemory = appointmentController.getAppointmentById(1);

       assertEquals(appointment.getId(), appointmentMemory.getId());
       assertEquals(appointment.getStart(), appointmentMemory.getStart());
       assertEquals(appointment.getEnd(), appointmentMemory.getEnd());
       assertEquals(appointment.getSubject(), appointmentMemory.getSubject());
       assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.getVisitor().getVisitorID());

    }

    @Test
    void searchForAppointmentString() {
        Appointment appointment = new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        List<Appointment> appointmentMemory = appointmentController.searchForAppointmentString("Piet");

        assertEquals(appointment.getId(), appointmentMemory.get(0).getId());
        assertEquals(appointment.getStart(), appointmentMemory.get(0).getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.get(0).getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.get(0).getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.get(0).getVisitor().getVisitorID());


    }

    @Test
    void searchForAppointmentByDate() {
        Appointment appointment = new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        List<Appointment> appointmentMemory = appointmentController.searchForAppointmentByDate(LocalDateTime.now());

        assertEquals(appointment.getId(), appointmentMemory.get(0).getId());
        assertEquals(appointment.getStart(), appointmentMemory.get(0).getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.get(0).getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.get(0).getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.get(0).getVisitor().getVisitorID());
    }

    @Test
    void createAppointment() {
        Appointment appointment = new Appointment("Test event 4", 4, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        appointmentController.createAppointment(appointment);
        Appointment appointmentMemory = appointmentController.getAppointmentById(4);

        assertEquals(appointment.getId(), appointmentMemory.getId());
        assertEquals(appointment.getStart(), appointmentMemory.getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.getVisitor().getVisitorID());


    }

    @Test
    void updateAppointment() {
        Appointment appointment = new Appointment("Should be updated", 4, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        appointmentController.updateAppointment(appointment);
        Appointment appointmentMemory = appointmentController.getAppointmentById(4);

        assertEquals(appointment.getId(), appointmentMemory.getId());
        assertEquals(appointment.getStart(), appointmentMemory.getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.getVisitor().getVisitorID());
    }

    @Test
    void searchAppointmentStringDate() {
        Appointment appointment = new Appointment("Should be updated", 4, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        List<Appointment> appointmentMemory = appointmentController.searchAppointmentStringDate("Should be updated", LocalDateTime.now());

        assertEquals(appointment.getId(), appointmentMemory.get(0).getId());
        assertEquals(appointment.getStart(), appointmentMemory.get(0).getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.get(0).getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.get(0).getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.get(0).getVisitor().getVisitorID());
    }

    @Test
    void deleteAppointment() {
        Appointment appointment = new Appointment("Should be updated", 4, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));
        int size = appointmentController.getAppointments().size();

        appointmentController.deleteAppointment(appointment);
        int newsize = appointmentController.getAppointments().size();

        assertNotEquals(size, newsize);
        assertEquals(3, newsize);
    }

    @Test
    void searchEventsVisitorID() {
        Appointment appointment = new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));

        List<Appointment> appointmentMemory = appointmentController.searchEventsVisitorID(1);

        assertEquals(appointment.getId(), appointmentMemory.get(0).getId());
        assertEquals(appointment.getStart(), appointmentMemory.get(0).getStart());
        assertEquals(appointment.getEnd(), appointmentMemory.get(0).getEnd());
        assertEquals(appointment.getSubject(), appointmentMemory.get(0).getSubject());
        assertEquals(appointment.getVisitor().getVisitorID(), appointmentMemory.get(0).getVisitor().getVisitorID());
    }
}
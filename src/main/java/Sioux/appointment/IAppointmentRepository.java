package Sioux.appointment;

import Sioux.visitor.Visitor;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentRepository {

    void CreateNewAppointment(Appointment appointment);
    void DeleteAppointmentById(int appointmentId);
    void UpdateAppointmentById(Appointment updatedAppointment);
    List<Appointment> GetAppointmentsByDate(LocalDate date);
    Appointment GetAppointmentById(int appointmentId);
    List<Appointment> getAllAppointments();
    List<Appointment> GetAppointmentsByCustomerId(int customerId);
    void SetCustomersOnAppointmentById(int eventId ,List<Visitor> customerToSet);
    List<Appointment> searchForAppointmentString(String searchTerm);
    List<Appointment> searchAppointmentStringDate(String term, LocalDate searchDate);
    List<Appointment> searchEventsVisitorID(int id);
}

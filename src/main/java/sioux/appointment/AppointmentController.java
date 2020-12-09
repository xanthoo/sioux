package sioux.appointment;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentController {
    /* This class will control the appointment section */

    IAppointmentRepository repository;

     public AppointmentController(IAppointmentRepository respository){
        this.repository = respository;
    }

    public List<Appointment> getAppointments() {
        return repository.getAllAppointments();
    }


    public Appointment getAppointmentById(int id){
        return repository.GetAppointmentById(id);
    }

    public void updateAppointment(Appointment appointment){
        repository.UpdateAppointmentById(appointment);
    }

    public List<Appointment> searchForAppointmentString(String searchTerm){
        return repository.searchForAppointmentString(searchTerm);
    }

    public List<Appointment> searchForAppointmentByDate(LocalDateTime searchDate) {
        return repository.GetAppointmentsByDate(searchDate);
    }

    public void createAppointment(Appointment appointment){
         repository.CreateNewAppointment(appointment);
    }

    /* public List<Event> searchForEventString(String searchTerm){
        List<Event> filteredList= new ArrayList<>();
        for (Event e : eventList)
            if (e.getSubject().contains(searchTerm) || e.getVisitor().getName().contains(searchTerm)){
                filteredList.add(e);
            }
        return filteredList;
    } */

    public List<Appointment> searchAppointmentStringDate(String term, LocalDateTime searchDate) {
        return repository.searchAppointmentStringDate(term, searchDate);

    }
      /*  public List<Event> searchForEventDate(LocalDate searchDate){
        List<Event> filteredList= new ArrayList<>();
        for (Event e : eventList){
            if (e.getStart().equals(searchDate)){
                filteredList.add(e);
            }
        }
        return filteredList;
    } */

   /* public List<Event> searchEventStringDate(String term, LocalDate searchDate) {
        List<Event> filteredList = new ArrayList<>();
        for (Event e : eventList){
            if (e.getSubject().contains(term) || e.getVisitor().getName().contains(term)){
                if (e.getStart().equals(searchDate)){
                    filteredList.add(e);
                }
            }
        }
        return filteredList;
    } */

    public void deleteAppointment(Appointment appointment){
        repository.DeleteAppointmentById(appointment.getId());
    }

    public List<Appointment> searchEventsVisitorID(int id) {
        return repository.searchEventsVisitorID(id);
    }
}

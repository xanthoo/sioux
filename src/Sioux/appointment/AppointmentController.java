package Sioux.appointment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    /* This class will control the appointment section */

    public List<Event> eventList;

    public AppointmentController(){
        eventList = new ArrayList<>();
        eventList.add(new Event("Test event", 1, LocalDate.now(), LocalDate.now(), "Manoah"));
        eventList.add(new Event("Test event 2", 2, LocalDate.now(), LocalDate.now(), "Joris"));
        eventList.add(new Event("Test event 2", 3, LocalDate.now(), LocalDate.now(), "Pietje"));
    }

    public List<Event> getEvents(){
        return eventList;
    }

    public Event getEventById(int id){
        for (Event e: eventList) {
            if (e.getId() == id)
            return e;
        }
        return null;
    }
}

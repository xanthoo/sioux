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

    public void editEvent(Event event){
     for (Event e : eventList){
         if (e.getId() == event.getId()){
             e.setStart(event.getStart());
             e.setEnd(event.getEnd());
             e.setSubject(event.getSubject());
             e.setVisitor(event.getVisitor());
             eventList.set(eventList.indexOf(e), e);
         }
     }
    }

    public List<Event> searchForEventString(String searchTerm){
        List<Event> filteredList= new ArrayList<>();
        for (Event e : eventList)
            if (e.getSubject().contains(searchTerm) || e.getVisitor().contains(searchTerm)){
                filteredList.add(e);
            }
        return filteredList;
    }

    public List<Event> searchForEventDate(LocalDate searchDate){
        List<Event> filteredList= new ArrayList<>();
        for (Event e : eventList){
            if (e.getStart().equals(searchDate)){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<Event> searchEventStringDate(String term, LocalDate searchDate) {
        List<Event> filteredList = new ArrayList<>();
        for (Event e : eventList){
            if (e.getSubject().contains(term) || e.getVisitor().contains(term)){
                if (e.getStart().equals(searchDate)){
                    filteredList.add(e);
                }
            }
        }
        return filteredList;
    }
}

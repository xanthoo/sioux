package Sioux.appointment;

import java.time.LocalDate;
import java.util.List;

public class Event {

    private  String subject;
    private int id;
    private LocalDate start;
    private LocalDate end;
        //private List<CustomerDTO> customers;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

        /*public List<CustomerDTO> getCustomers() {
            return customers;
        }

        public void setCustomers(List<CustomerDTO> customers) {
            this.customers = customers;
        }*/
}

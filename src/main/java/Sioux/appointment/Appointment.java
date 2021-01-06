package Sioux.appointment;

import Sioux.visitor.Visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Appointment {

    private  String subject;
    private int id;
    private LocalDateTime start;
    private Visitor visitor;


    public Appointment(String subject, int id, LocalDateTime start, Visitor visitor) {
        this.subject = subject;
        this.id = id;
        this.start = start;
        this.visitor = visitor;
    }

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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    /*public List<CustomerDTO> getCustomers() {
            return customers;
        }

        public void setCustomers(List<CustomerDTO> customers) {
            this.customers = customers;
        }*/

    @Override
    public String toString() {
        return visitor +  " | " + subject + " |" + start;
    }
}

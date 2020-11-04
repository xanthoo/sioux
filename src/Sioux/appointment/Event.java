package Sioux.appointment;

import Sioux.visitor.Visitor;

import java.time.LocalDate;
import java.util.List;

public class Event {

    private  String subject;
    private int id;
    private LocalDate start;
    private LocalDate end;
    private Visitor visitor;


    public Event(String subject, int id, LocalDate start, LocalDate end, Visitor visitor) {
        this.subject = subject;
        this.id = id;
        this.start = start;
        this.end = end;
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

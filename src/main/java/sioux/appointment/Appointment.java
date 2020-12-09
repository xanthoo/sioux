package sioux.appointment;

import sioux.visitor.Visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {

    private  String subject;
    private int id;
    private LocalDateTime start;
    private LocalDate end;
    private Visitor visitor;


    public Appointment(String subject, int id, LocalDateTime start, LocalDate end, Visitor visitor) {
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
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

package Sioux.appointment;

import Sioux.visitor.Visitor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Set;

public class Appointment {

    private  String subject;
    private int id;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime start;
    private Visitor visitor;
    private Set<Visitor> visitors;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime end;


    public Appointment(String subject, int id, LocalDateTime start, Visitor visitor) {
        this.subject = subject;
        this.id = id;
        this.start = start;
        this.visitor = visitor;
    }

    public Appointment(){}

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

    public Set<Visitor> getCustomers() {
        //customers.add(customer);
        return visitors;
    }

    public void setCustomers(Set<Visitor> visitors) {
        this.visitors = visitors;
        this.visitor = visitors.stream().findFirst().get();
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}

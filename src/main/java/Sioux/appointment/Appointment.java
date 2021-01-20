package Sioux.appointment;

import Sioux.visitor.Visitor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class Appointment {

    private  String subject;
    private int id;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime start;
    private Visitor customer;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime end;


    public Appointment(String subject, int id, LocalDateTime start, Visitor customer) {
        this.subject = subject;
        this.id = id;
        this.start = start;
        this.customer = customer;
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

    public Visitor getCustomer() {
        return customer;
    }

    public void setCustomer(Visitor customer) {
        this.customer = customer;
    }

    /*public List<CustomerDTO> getCustomers() {
            return customers;
        }

        public void setCustomers(List<CustomerDTO> customers) {
            this.customers = customers;
        }*/

    @Override
    public String toString() {
        return customer +  " | " + subject + " |" + start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}

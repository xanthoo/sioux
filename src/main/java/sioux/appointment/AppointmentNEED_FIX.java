package sioux.appointment;

import sioux.visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentNEED_FIX {

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("id")
    private int id;

    @JsonProperty("start")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime start;

    @JsonProperty("end")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime end;

    @JsonProperty("customers")
    private List<Visitor> visitors;

    public AppointmentNEED_FIX(){}

    public AppointmentNEED_FIX(String subject, int id, LocalDateTime start, LocalDateTime end, List<Visitor> visitors) {
        this.subject = subject;
        this.id = id;
        this.start = start;
        this.end = end;
        this.visitors = visitors;
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

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public List<Visitor> getVisitor() {
        return visitors;
    }

    public void setVisitor(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public String toString() {
        var visitorString = "visitors: ";
        for (Visitor visitor : visitors) {
            visitorString += visitor.toString() + ", ";
        }
        return visitorString + " | " + subject + " |" + start;
    }
}

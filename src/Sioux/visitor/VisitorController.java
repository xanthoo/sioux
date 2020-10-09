package Sioux.visitor;

import java.util.ArrayList;
import java.util.List;

public class VisitorController {
    /* This class controls the visitor section */

    private List<Visitor> visitorList;

    public VisitorController(){
         visitorList = new ArrayList<>();
         visitorList.add(new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));
         visitorList.add(new Visitor(1, "Jan", "456-def-78", "0690123456", "Brings his assistant with him."));
    }

    public List<Visitor> getVisitorList() {
        return visitorList;
    }

    public Visitor getVisitorByID(int id){
        for (Visitor visitor: visitorList) {
            if (visitor.getVisitorID() == id)
                return visitor;
        }
        return null;
    }
    public void updateVisitor(int id, String name, String licenseplateNumber, String notes, String phoneNumber){
        Visitor visitor = getVisitorByID(id);
        visitor.setName(name);
        visitor.setLicensePlateNumber(licenseplateNumber);
        visitor.setNotes(notes);
        visitor.setPhoneNumber(phoneNumber);
    }

    public Visitor addVisitor(String name, String licenseplateNumber, String notes, String phoneNumber){
        Visitor newVisitor = new Visitor(visitorList.toArray().length+1, name, licenseplateNumber, phoneNumber, notes);
        visitorList.add(newVisitor);
        return newVisitor;
    }
    public Visitor deleteVisitor(int id){
        for(Visitor visitor: visitorList){
            if(visitor.getVisitorID() == id){
                visitorList.remove(visitor);
                return visitor;
            }
        }
        return null;
    }

    public List<Visitor> searchVisitorByName(String name){
        List<Visitor> foundVisitors = new ArrayList<>();
        for(Visitor visitor: visitorList) {
            if (visitor.getName().equals(name)) {
                foundVisitors.add(visitor);
            }
        }
        return foundVisitors;
    }
}


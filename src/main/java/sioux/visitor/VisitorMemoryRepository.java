package sioux.visitor;

import java.util.ArrayList;
import java.util.List;

public class VisitorMemoryRepository implements IVisitorRepository{
    List<Visitor> visitorList;

    public VisitorMemoryRepository(){
        visitorList = new ArrayList<>();
        visitorList.add(new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard."));
        visitorList.add(new Visitor(2, "Jan", "456-def-78", "0690123456", "Brings his assistant with him."));
        visitorList.add(new Visitor(3, "Jan", "456-def-78", "0690123456", "Brings his assistant with him."));
    }

    public List<Visitor> GetAllVisitors() {
        return visitorList;
    }

    public Visitor getVisitorByID(int id) {
        for(Visitor visitor: visitorList) {
            if (visitor.getVisitorID() == id) {
                return visitor;
            }
        }
        return null;
    }

    public void updateVisitor(Visitor updatedVisitor) {
        Visitor visitor = getVisitorByID(updatedVisitor.getVisitorID());
        visitor.setName(updatedVisitor.getName());
        visitor.setLicensePlateNumber(updatedVisitor.getLicensePlateNumber());
        visitor.setPhoneNumber(updatedVisitor.getPhoneNumber());
        visitor.setNotes(updatedVisitor.getNotes());

    }

    public Visitor addVisitor(Visitor newVisitor) {
        visitorList.add(newVisitor);
        return newVisitor;
    }

    public Visitor deleteVisitor(int id) {
        for(Visitor visitor: visitorList){
            if(visitor.getVisitorID() == id){
                visitorList.remove(visitor);
                return visitor;
            }
        }
        return null;

    }

    public List<Visitor> searchVisitorByName(String name) {
        List<Visitor> foundVisitors = new ArrayList<>();
        for(Visitor visitor: visitorList) {
            if (visitor.getName().equals(name)) {
                foundVisitors.add(visitor);
            }
        }
        return foundVisitors;
    }

    public Visitor getVisitorByLicencePlate(String licencePlate) {
        for(Visitor visitor: visitorList) {
            if (visitor.getLicensePlateNumber().equals(licencePlate)) {
                return visitor;
            }
        }
        return null;
    }
}

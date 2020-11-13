package Sioux.visitor;

import java.util.List;

public interface IVisitorRepository {
    List<Visitor> GetAllVisitors();
    Visitor getVisitorByID(int id);
    void updateVisitor(Visitor updatedVisitor);
    Visitor addVisitor(Visitor newVisitor);
    Visitor deleteVisitor(int id);
    List<Visitor> searchVisitorByName(String name);
    Visitor getVisitorByLicencePlate(String licencePlate);
}

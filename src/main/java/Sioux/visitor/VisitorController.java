package Sioux.visitor;

import java.util.List;

public class VisitorController {
    /* This class controls the visitor section */

    private IVisitorRepository repository;
    private List<Visitor> visitorList;

    public VisitorController(IVisitorRepository repository){
        this.repository = repository;
    }

    public List<Visitor> getVisitorList() {
        return repository.GetAllVisitors();
    }

    public Visitor getVisitorByID(int id){
        return repository.getVisitorByID(id);
    }

    public void updateVisitor(Visitor updatedVisitor){
        repository.updateVisitor(updatedVisitor);
    }

    public Visitor addVisitor(Visitor newVisitor){
        return repository.addVisitor(newVisitor);
    }

    public Visitor deleteVisitor(int id){
        return repository.deleteVisitor(id);
    }

    public List<Visitor> searchVisitorByName(String name){
        return repository.searchVisitorByName(name);
    }
}


package Sioux.visitor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ChooseVisitorController {

    @FXML
    private ListView<Visitor> lvVisitors;
    @FXML
    private Button btnSelectVisitor;
    @FXML
    private TextField tfSearchVisitorByName;

    private VisitorController visitorController;
    private Visitor selectedVisitor;

    public void initData(VisitorController visitorController){
        this.visitorController = visitorController;
        showVisitors();
    }

    public void showVisitors(){
        lvVisitors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (Visitor v : visitorController.getVisitorList()) {
            lvVisitors.getItems().add(v);
        }
    }
    public void searchVisitor(){
        lvVisitors.getItems().clear();
        for (Visitor v : visitorController.searchVisitorByName(tfSearchVisitorByName.getText())){
            lvVisitors.getItems().add(v);
        }
        lvVisitors.refresh();
    }

    public void selectVisitor(){
        selectedVisitor = lvVisitors.getSelectionModel().getSelectedItem();
        if (selectedVisitor !=null){
            Stage stage = (Stage) btnSelectVisitor.getScene().getWindow();
            stage.close();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("No visitor selected.");
        alert.setContentText("Please select a visitor.");
        alert.showAndWait();
    }
    public Visitor getSelectedVisitor(){
        return selectedVisitor;
    }
}

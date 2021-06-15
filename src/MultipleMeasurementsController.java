import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import java.net.URL;
import java.util.ResourceBundle;

public class MultipleMeasurementsController implements Initializable {
    @FXML
    private SplitMenuButton DateofMeasurements;

    //Metode der gemmer den Date, og finder det measurementID hvor date passer og fjerner resterende data og lukker sig selv
    public void ok() {
        SQL.getSqlOBJ().getIdWhereDate(getDateofMeasurements().getText());
        Algorithm.getAlgorithmOBJ().textBox("Patient Found");
        getDateofMeasurements().getItems().clear();
        SQL.getSqlOBJ().getNumberOfMeasurementsOnSameCPR().clear();
        Main.closeStage(Main.MultipleMeasurementStage);
    }

    //Danner MenuItems for alle date's , når Controlleren Initialiseres
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i =0;i<=SQL.getSqlOBJ().getNumberOfMeasurementsOnSameCPR().size()-1;i++){
            String date = SQL.getSqlOBJ().getNumberOfMeasurementsOnSameCPR().get(i);
            MenuItem name = new MenuItem();
            name.setText(date);
            name.setOnAction(e->DateofMeasurements.setText(date));
            getDateofMeasurements().getItems().add(name);
        }
    }
    //Getter
    public SplitMenuButton getDateofMeasurements() {
        return DateofMeasurements;
    }
}

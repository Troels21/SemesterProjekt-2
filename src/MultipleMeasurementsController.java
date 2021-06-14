import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MultipleMeasurementsController implements Initializable {
    @FXML
    private SplitMenuButton DateofMeasurements;

    public void ok() {
        SQL.getSqlOBJ().getIdWhereData(getDateofMeasurements().getText());
        Algorithm.getAlgorithmOBJ().textBox("Patient Found");
        Main.closeStage(Main.MultipleMeasurementStage);
    }

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

    public SplitMenuButton getDateofMeasurements() {
        return DateofMeasurements;
    }
}

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerDatabaseLogin extends Main {
    //Attributter til databaseLogin, og valg af COMPORT
    @FXML
    private TextField COMPORT;
    @FXML
    private TextField DatabaseName;
    @FXML
    private TextField DatabaseUsername;
    @FXML
    private TextField DatabasePassword;
    @FXML
    private TextField DatabaseURL;

    //Metode der gemmer de indtastede data, åbner EKG SCENE og tjekker om login passede
    public void okButton() {
        SQL.getSqlOBJ().setUrl(getDatabaseURL().getText() + getDatabaseName().getText());
        SQL.getSqlOBJ().setUser(getDatabaseUsername().getText());
        SQL.getSqlOBJ().setPassword(getDatabasePassword().getText());
        SerialPortClass.getSerialPortOBJ().setCOMPORT(getCOMPORT().getText());
        try {
            SQL.getSqlOBJ().makeConnectionSQL();
            openStage(EKGStage, "EKG SCENE", "EKG", 650, 400);
        } catch (SQLException | IOException throwables) {
            Algorithm.getAlgorithmOBJ().textBox("Acces Denied");
        }
    }

    //Getters and Setters
    public TextField getDatabaseName() {
        return DatabaseName;
    }

    public TextField getDatabaseUsername() {return DatabaseUsername;
    }

    public TextField getDatabasePassword() {
        return DatabasePassword;
    }

    public TextField getDatabaseURL() {
        return DatabaseURL;
    }

    public TextField getCOMPORT() {
        return COMPORT;
    }

    public void setCOMPORT(TextField COMPORT) {
        this.COMPORT = COMPORT;
    }
}

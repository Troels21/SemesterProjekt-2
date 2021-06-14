import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerDatabaseLogin extends Main {
    @FXML
    private TextField DatabaseCOMPORT;
    @FXML
    private TextField DatabaseName;
    @FXML
    private TextField DatabaseUsername;
    @FXML
    private TextField DatabasePassword;
    @FXML
    private TextField DatabaseURL;

    public void okButton() {
        SQL.getSqlOBJ().setUrl(getDatabaseURL().getText() + getDatabaseName().getText());
        SQL.getSqlOBJ().setUser(getDatabaseUsername().getText());
        SQL.getSqlOBJ().setPassword(getDatabasePassword().getText());
        SerialPortClass.getSerialPortOBJ().setCOMPORT(getDatabaseCOMPORT().getText());
        try {
            SQL.getSqlOBJ().makeConnectionSQL();
            openStage(EKGStage, "EKG SCENE", "EKG", 650, 400);
        } catch (SQLException | IOException throwables) {
            Algorithm.getAlgorithmOBJ().textBox("Acces Denied");
        }
    }

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

    public TextField getDatabaseCOMPORT() {
        return DatabaseCOMPORT;
    }

    public void setDatabaseCOMPORT(TextField COMPORT) {
        this.DatabaseCOMPORT = COMPORT;
    }
}

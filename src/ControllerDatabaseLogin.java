import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerDatabaseLogin extends Main implements Initializable {
    //Attributter til databaseLogin, og valg af COMPORT
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
        try {
            SQL.getSqlOBJ().makeConnectionSQL(SQL.getSqlOBJ().getUrl(), SQL.getSqlOBJ().getUser(), SQL.getSqlOBJ().getPassword());
            openStage(EKGStage, "EKG SCENE", "EKG", 650, 400);
        } catch (SQLException | IOException throwables) {
            Algorithm.getAlgorithmOBJ().textBox("Acces Denied");
        }
    }

    //Getters and Setters
    public TextField getDatabaseName() {
        return DatabaseName;
    }

    public TextField getDatabaseUsername() {
        return DatabaseUsername;
    }

    public TextField getDatabasePassword() {
        return DatabasePassword;
    }

    public TextField getDatabaseURL() {
        return DatabaseURL;
    }

    public void setDatabaseName(String databaseName) {
        DatabaseName.setText(databaseName);
    }

    public void setDatabaseUsername(String databaseUsername) {
        DatabaseUsername.setText(databaseUsername);
    }

    public void setDatabasePassword(String databasePassword) {
        DatabasePassword.setText(databasePassword);
    }

    public void setDatabaseURL(String  databaseURL) {
        DatabaseURL.setText(databaseURL);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDatabaseUsername(SQL.getSqlOBJ().getUser());
        setDatabasePassword(SQL.getSqlOBJ().getPassword());
    }
}

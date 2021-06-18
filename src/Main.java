import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;

import java.io.IOException;

public class Main extends Application {
    //VM options --module-path "C:\Users\Troel\SemesterProjekt 2\SDK\javafx-sdk-16\lib" --add-modules javafx.controls,javafx.fxml
    Stage EKGStage = new Stage();
    static Stage MultipleMeasurementStage = new Stage();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Database login Scene.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 340, 290));
        stage.show();
    }

    public static void openStage(Stage stage, String name, String title, int height, int width) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(name+".fxml"));
        stage.setTitle(title);
        stage.setScene(new Scene(root, height, width));
        stage.show();
    }
    static public void closeStage(Stage stage){
        stage.hide();
    }
}

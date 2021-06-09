import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        /*
        SerialPortClass.getSerialPortOBJ().openPort();
        while (true) {
            String maling = SerialPortClass.getSerialPortOBJ().maaling();
            if (maling != null) {
                System.out.println(maling);
            }

            Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueA);

            for (int i = 0; i < 1000; i++) {
                System.out.println(Filter.getFilterOBJ().getValueA()[i]);
            }
        }*/
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scener.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 650, 400));
        stage.show();
    }
}

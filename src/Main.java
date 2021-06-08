import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {
    static int counter=0;

    public static void main(String[] args) {
        SerialPortClass.getSerialPortOBJ().openPort();/*
        while (true){
            String maling = SerialPortClass.getSerialPortOBJ().maaling();
            if (maling !=null)
                System.out.println(maling);
        }*/

        Filter.getFilterOBJ().filter3950measurements(Filter.ValueA);

        for(int i=0;i<Filter.ValueA.length;i++){
            System.out.println(Filter.ValueA[i]);
            if(Filter.ValueA[i]==0){
                counter++;
            }
        }
        System.out.println(Filter.ValueA.length);
        System.out.println(Arrays.stream(Filter.ValueA).max() +"  " + Arrays.stream(Filter.ValueA).min());
        System.out.println(counter);
        //launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scener.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 650, 400));
        stage.show();
    }
}

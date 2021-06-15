import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.util.LinkedList;

public class ThreadHandler {
    //singleton af ThreadHandler OBJ
    private static ThreadHandler ThreadHandlerOBJ = new ThreadHandler();
    private ThreadHandler() {}
    public static ThreadHandler getThreadHandlerOBJ() {
        return ThreadHandlerOBJ;
    }

    //Boolean der skal sørge for at trådene skal stoppe, men kun efter at de har køft deres aktuelle måling interval færdig
    static private Boolean ShouldMyThreadBeRuning = false;

    //Metode der laver en ny tråd, hvis tråden har kørt færdig
    public void makeNewThreadIfClosed(Thread thread) {
        if(!thread.isAlive()){
            Thread t = new Thread(thread);
            t.start();
        }
    }

    //Getters and Setters
    public static Boolean getShouldMyThreadBeRuning() {
        return ShouldMyThreadBeRuning;
    }

    public static void setShouldMyThreadBeRuning(Boolean shouldMyThreadBeRuning) {
        ShouldMyThreadBeRuning = shouldMyThreadBeRuning;
    }
}

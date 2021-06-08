public class SQLupdateThread extends Thread {

    @Override
    public void run() {
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //SQL Inject FilterOBJ.ValueA
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //SQL Inject FilterOBJ.ValueB
        }
    }
}

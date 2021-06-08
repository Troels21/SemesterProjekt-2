public class ThreadHandler {
    Thread RealTimet, SQLUpdatet,SerialPortt;

    SerialPortThread SerialPortThreadOBJ = new SerialPortThread(SQLUpdatet);
    RealTimeThread RealTimeThreadOBJ= new RealTimeThread();
    SQLupdateThread sqlThreadOBJ= new SQLupdateThread();

    static Boolean ShouldMyThreadBeRuning = true;

    public void makeThread(){
        SerialPortt = new Thread(SerialPortThreadOBJ);
        RealTimet = new Thread(RealTimeThreadOBJ);
        SQLUpdatet = new Thread(sqlThreadOBJ);
    }

    public void threadStart(){
        SerialPortt.start();
        RealTimet.start();
        SQLUpdatet.start();
    }

    public void threadJoin(){
        try {
            SerialPortt.join();
            RealTimet.join();
            SQLUpdatet.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void ifThreadOpenClose(Thread thread) {
        if (thread.isAlive()) {
            thread.interrupt();
        }

    }

}

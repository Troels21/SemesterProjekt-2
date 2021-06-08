public class ThreadHandler {
    Thread RealTime, SQLUpdate,SerialPort;

    static Boolean ShouldMyThreadBeRuning = true;
    //sørgere for at linechart tråd er slukket, Overfør til SQL, SerialPort Tråd,

    public void threadStart(){
        SerialPortThread SerialPortThreadOBJ = new SerialPortThread(SQLUpdate);
        RealTimeThread RealTimeThreadOBJ= new RealTimeThread();
        SQLupdateThread sqlThreadOBJ= new SQLupdateThread();

        RealTime = new Thread()
    }

    public void threadJoin(){

    }

    public void ifThreadOpenClose(Thread thread) {
        if (thread.isAlive()) {
            thread.interrupt();
        }

    }

}

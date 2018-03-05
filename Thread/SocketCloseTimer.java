package Reader.Thread;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SocketCloseTimer extends TimerTask {
    private Thread t;
    public SocketCloseTimer(Thread t){
        this.t = t;
    }

    @Override
    public void  run(){


    }
}

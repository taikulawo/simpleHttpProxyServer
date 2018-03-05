package Reader.UserException;

import java.awt.*;

public class ChioceNotFound extends Exception {

    private String msg;
    public ChioceNotFound(String msg){
        this.msg = msg;
    }

    public void what(){
        System.out.println(msg);
    }
}

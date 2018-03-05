package Reader.Proxy;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public interface IProxy{
     boolean doConnect()throws IOException;
     void forever()throws IOException;


}

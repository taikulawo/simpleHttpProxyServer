package Reader.Thread;

import Reader.Interface.ConstValues;
import Reader.Proxy.HttpProxy;
import jdk.internal.util.xml.impl.Input;


import java.net.Socket;


public class ProxyHandleThread extends Thread implements ConstValues{
    private boolean IsFinished = false;
    private HttpProxy httpProxy ;
    Socket socket;
    Socket socketToServer = null;


    public ProxyHandleThread(Socket socket){
        this.socket = socket;
        httpProxy = new HttpProxy();
    }


    @Override
    public void run(){

                httpProxy.initField(this.socket);
                if(!httpProxy.doConnect()){
                    return;
                }

                StreamProcessThread t = new StreamProcessThread();
                StreamProcessThread t1 = new StreamProcessThread();

                t.initStream(socket, httpProxy.getSocketToServer(),ClientToServer_Stream);
                t1.initStream(socket, httpProxy.getSocketToServer(),ServerToClient_Stream);
                t.start();
                t1.start();

    }

}

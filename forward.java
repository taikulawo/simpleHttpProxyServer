package Reader;

import Reader.Proxy.HttpProxy;
import Reader.Thread.ProxyHandleThread;
import Reader.Thread.StreamProcessThread;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class forward {
    public static void main(String[] args) throws Exception{

        ServerSocket serversocket = new ServerSocket(9999);
        ArrayList<ProxyHandleThread> proxyHandleThreads = new ArrayList<>();
        while(true){
            Socket socket = serversocket.accept();
            ProxyHandleThread local = new ProxyHandleThread(socket);
            proxyHandleThreads.add(local);
            local.start();
        }

        /*while(true){

        }*/
    }

}

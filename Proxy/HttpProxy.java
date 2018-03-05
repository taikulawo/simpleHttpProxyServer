package Reader.Proxy;
import Reader.Interface.ConstValues;
import java.net.*;
import  java.io.*;

import Reader.Test.Test;
public class HttpProxy implements IProxy,ConstValues{

    private Socket socket_http  ;
    private Socket socketToServer  ;
    private InputStream clientInputStream;/*with client*/
    private OutputStream clientOutputStream ;/*with client*/
    private InputStream serverInputStream ;/*with server*/
    private OutputStream serverOutputStream ;/*with server*/



    private  boolean isHttp = true;

    public HttpProxy(Socket socket) throws IOException{
        this.socket_http = socket;

        clientInputStream = socket_http.getInputStream();
        clientOutputStream = socket_http.getOutputStream();
    }

    public HttpProxy(){

    }

    public void initField(Socket socket){
        try{
            socket_http = socket;
            clientInputStream = socket_http.getInputStream();
            clientOutputStream = socket_http.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean doConnect(){
        try{
        int count = clientInputStream.available();
            byte[] bytes = new byte[count];
            clientInputStream.read(bytes);
            Test.Writer(bytes);
            System.out.println("--------------------------------");
            System.out.println(new String(bytes));
            System.out.println("--------------------------------");
            String info = new String(bytes);
            String[] firstHandShake = info.split("\r\n");

            String[] firstFields = firstHandShake[0].split(" ");
            String requestMethod = firstFields[0];/*CONNECT == https/////GET === http*/
            String remoteHost;
            Integer remotePort;
            if ("CONNECT".equalsIgnoreCase(requestMethod)){/*tunnel to https*/
                String[] destinationAddr = (firstHandShake[1].split(": "))[1].split(":");
                remoteHost = destinationAddr[0];
                remotePort = Integer.parseInt(destinationAddr[1]);
                System.out.println("remoteHost: " + remoteHost + "//" + "remotePort: " + remotePort);
                if(!connectRemoteHost(remoteHost,remotePort)){
                    return false;
                }
                clientOutputStream.write("HTTP/1.1 200 Connection established\r\n\r\n".getBytes());
                clientOutputStream.flush();
            }else{/*is http*/
                remoteHost = (firstHandShake[1].split(": "))[1];
                remotePort = 80;
                if(!connectRemoteHost(remoteHost,remotePort)){
                    return false;
                }
            }
        /*for (String str : firstHandShake){
            System.out.println(str);
        }*/
        }catch (NullPointerException e ){
            e.printStackTrace();
        }catch (IOException e ){
            e.printStackTrace();
        }

        return true;
    }

    public Socket getSocketToServer(){
        return socketToServer;
    }

    public boolean connectRemoteHost(String host, Integer port){
        try{
            Socket socket = new Socket(host,port);
            socketToServer = socket;
            serverInputStream = socketToServer.getInputStream();
            serverOutputStream = socketToServer.getOutputStream();
        }catch (ConnectException e ){
            System.out.println("connection failed, funciton has been retured!");
               return false;
        }catch (IOException e ){
            e.printStackTrace();
            return false;
        }
    return true;
    }

   /*public String[] parseHeader(byte[] bytes){
        String data = new String(bytes);
        int mark = data.indexOf("\r\n\r\n",0);
        String header = data.substring(0, mark-1);
        String[] headerLines = (header.split("\r\n"));
        Vector<String[]> vector = new Vector<String[]>();
        int i = 0;
        for (String single : headerLines){
            single.split()
        }
    }*/


   /* public byte[] rewriteProxyHeader(){
        HashMap<String,String> header = new HashMap<>();

    }*/

    /*public String[] changeHeaderFormat() throws IOException{
        byte[] bytes = new byte[1024*10];
        clientInputStream.read(bytes);


        //String headerInfo = new String(bytes);

    }*/


    @Override
    public void forever(){

    }

}

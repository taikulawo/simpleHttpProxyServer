package Reader.Thread;

import Reader.Interface.ConstValues;
import jdk.internal.util.xml.impl.Input;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class StreamProcessThread extends Thread implements ConstValues{
    private Socket socket;
    private Socket socketToServer;
    private InputStream in;
    private OutputStream out;
    private boolean IsFinished ;

    public StreamProcessThread(){
        IsFinished = false;
        socketToServer = null;
    }

    @Override
    public void run(){
        try{
            socket.setSoTimeout(3*1000);
        }catch (SocketException e){
            e.printStackTrace();
            System.out.println("Cannot set socket timeout, thread maybe not exit!");
        }

        while(!IsFinished){

            int count = 0;
            try{
                if (( count = in.available())>0){
                    byte[] bytes = new byte[count];
                    in.read(bytes);
                    out.write(bytes,0,count);
                    out.flush();
                }
            }
            catch (SocketTimeoutException e ){
                IsFinished = true;
            }
            catch (IOException e ){
                e.printStackTrace();
                System.out.println("Time over, may be socket has been closed?");
            }
        }
        closeSocketsHandles();
    }

    public boolean closeSocketsHandles(){
        try {
            in.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    public void initStream(Socket socketToClient, Socket socketToServer, int chioce){
        try{
            this.socket = socketToClient;
            this.socketToServer = socketToServer;
            if(chioce == ClientToServer_Stream) {
                this.in = socket.getInputStream();
                this.out = socketToServer.getOutputStream();
            }else if(chioce == ServerToClient_Stream){
                this.in = socketToServer.getInputStream();
                this.out = socket.getOutputStream();
            }
        }catch (IOException e ){
            e.printStackTrace();
        }




    }

}

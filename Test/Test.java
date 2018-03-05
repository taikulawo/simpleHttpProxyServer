package Reader.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public  class Test {
    public static void Writer(byte[] bytes)throws IOException{
        File file = new File("D:\\Test.txt");
        FileOutputStream fos = new FileOutputStream(file,true);
        fos.write(bytes);
        fos.close();
    }
}

package image;

import java.io.UnsupportedEncodingException;

/**
 * Created by guzy on 17/11/11.
 */
public class Str2Byte {

    public static void main(String[] args) {
        byte2Str();
        str2Byte();
    }

    public static void byte2Str(){
        byte[] bytes=new byte[]{0x31,0x32,0x32};
        //System.out.println(new String(bytes));

        char[]cs=new char[3];
        for(int i=0;i<cs.length;i++){
            cs[i]=(char)bytes[i];
        }
        System.out.println(new String(cs));
    }

    public static void str2Byte()  {
        String str="我是";
        byte[] bytes= new byte[0];
        try {
            bytes = str.getBytes("utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(byte b:bytes){
            System.out.println(b);
        }
    }
}

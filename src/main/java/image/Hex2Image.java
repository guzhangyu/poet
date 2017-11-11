package image;

import java.io.*;

/**
 * Created by guzy on 17/11/8.
 */
public class Hex2Image {

    public static void main(String[] args) throws IOException {
        Hex2Image to=new Hex2Image();
        StringBuffer sb = getHexStr();
        to.saveToImgFile(sb.toString(),"/Users/guzy/Downloads/aa.png");
    }

    private static StringBuffer getHexStr() throws IOException {
        InputStream is=new FileInputStream("/Users/guzy/Downloads/aa.txt");
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        StringBuffer sb=new StringBuffer();
        String str=null;
        while((str=br.readLine())!=null){
            sb.append(str);
        }
        return sb;
    }

    public void saveToImgFile(String src,String output){
        if(src==null || src.length()==0){
            return;
        }
        try{
            FileOutputStream out=new FileOutputStream(new File(output));
            byte[] bytes=src.getBytes();
            for(int i=0;i<bytes.length;i+=2){
                out.write(charToInt(bytes[i])*16+charToInt(bytes[i+1]));
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int charToInt(byte ch){
        int val=0;
        if(ch>=0x30 && ch<=0x39){
            val=ch-0x30;
        }else if(ch>=0x41 && ch<=0x46){
            val=ch-0x41+10;
        }
        return val;
    }
}

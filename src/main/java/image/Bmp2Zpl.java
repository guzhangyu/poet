package image;

import image.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by guzy on 17/11/9.
 */
public class Bmp2Zpl {

    public static void main(String[] args) throws IOException {

        System.out.println(getZpl("/Users/guzy/Downloads/image_sample_bw.png"
                //"/Users/guzy/IdeaProjects/img2grf/test.bmp"
        ));
    }

    public static String getZpl(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            BufferedImage img = ImageIO.read(new File(filePath));
            int h = img.getHeight();
            int w = img.getWidth();
            byte[] origBytes = ImageUtil.readFully(fis);
            System.out.println("height: " + h + " width: " + w + " total byte length: " + origBytes.length);

            int pixeloffset = origBytes[10] + origBytes[11] + origBytes[12] + origBytes[13];
            if (pixeloffset == 62) {
                System.out.println("pixel offset: " + pixeloffset);

            } else {

                System.out.println("pixel offset (WARNING! NOT THE DEFAULT OF 62): " + pixeloffset);

            }

            int byteW = (int) Math.ceil(((double) w) / 8);
            byte[] withoutHeaderBytes = //getConvertedBytes1(img, Arrays.copyOfRange(origBytes, 62, origBytes.length),w/8,byteW);
                    getConvertedBytes(origBytes, pixeloffset, byteW);

//            if (invertPixels) {

                System.out.println("pixels inverted!");
                for (int i = 0; i < withoutHeaderBytes.length; i++) {
                    withoutHeaderBytes[i] ^= 0xFF;
                }
           // }

            String byteAsString = byte2HexStr(withoutHeaderBytes);

            return String.format("^XA^GFA,%d,%d,%d,%s^FS^XZ",withoutHeaderBytes.length,withoutHeaderBytes.length,byteW,byteAsString);
        }finally {
            if(fis!=null){
                fis.close();
            }
        }
    }

    private static byte[] getConvertedBytes(byte[] origBytes, int pixeloffset, int byteW) {
        byte[] withoutHeaderBytes = new byte[origBytes.length - pixeloffset];

        int newByteIndex = 0;

        for (int i = origBytes.length - 1; i >= pixeloffset; i--) {

            int tmp = i - (byteW - 1);

            for (int j = tmp; j < tmp + byteW; j++) {

                    if(newByteIndex==withoutHeaderBytes.length){
                        break;
                    }
                withoutHeaderBytes[newByteIndex++] = origBytes[j];

            }
            i = tmp;
        }
        return withoutHeaderBytes;
    }


    public static String byte2HexStr(byte[] b) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
        }
        return hs.toUpperCase();
    }
}

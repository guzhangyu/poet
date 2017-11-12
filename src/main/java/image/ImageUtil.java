package image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by guzy on 17/11/11.
 */
public class ImageUtil {

    public static void convertToBmp(){
        try {
            FileImageInputStream fiis=new FileImageInputStream(new File("/Users/guzy/Desktop/2.png"));
            FileImageOutputStream fios=new FileImageOutputStream(new File("/Users/guzy/Desktop/23.bmp"));

            ImageReader reader=null;
            Iterator<ImageReader> it1= ImageIO.getImageReadersByFormatName("png");
            if(it1.hasNext()){
                reader=it1.next();
            }
            reader.setInput(fiis);

            ImageWriter bmpWriter=null;
            Iterator<ImageWriter> it2=ImageIO.getImageWritersByFormatName("bmp");
            if(it2.hasNext()){
                bmpWriter=it2.next();
            }
            bmpWriter.setOutput(fios);
            BufferedImage br=reader.read(0);
            bmpWriter.write(br);
            fiis.close();
            fios.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static byte[] readFully(InputStream stream){
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        try {
            while ((bytesRead = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static byte[] convertBytes4Zpl(byte[] rgbPixels, int originW, int originH) {
//        for(int i=0;i<rgbPixels.length;i++){
////            byte b=0;
////            for(int j=0;j<8;j++){
////                b=(byte)(b+((rgbPixels[i]>>j)&3));
////            }
//            rgbPixels[i]^=0xFF;
//        }
        return rgbPixels;
//        int w=originW/8;
//        int newW=(originW+7)/8;
//
//        byte[]imgData=new byte[originH*newW];
//        if(newW!=w){
//            imgData=new byte[newW*originH];
//            System.out.println(imgData.length);
//            System.out.println(rgbPixels.length);
//
//            int offset=newW*8-originW;
//            byte mask = (byte)(0xFF << offset);
//
//            System.out.println(String.format("h:%d,w:%d,l:%d",originH,originW,imgData.length/originH));
//            out:for(int j=0;j<originH;j++){
//                for(int i=0;i<newW;i++){
//                    int index=j*newW+i;
//                    for(int k=0;k<8;k++){
//                        int originIndex=j*originW+i*8+k;
//                        if(i*8+k<=originW-1){
//                            imgData[index]=
//                                    (byte)((imgData[index]+
//                                            ((getGray(rgbPixels[originIndex])>100?0:1)<<(7-k))
//                                    //((rgbPixels[originIndex]^0xFF)<<(7-k))
//                                    ));
//                        }
//                    }
//
////                    int index=j*newW+i;
////                    if(index>=imgData.length){
////                        break out;
////                    }
////                    int originIndex=j*w+i;
////                    if(i<w-1){
////                        imgData[index]=(byte)(rgbPixels[originIndex]^0xFF);
////                    }else if(i==w-1){
////                        imgData[index]=(byte)(rgbPixels[originIndex]^mask);
////                    }else {
////                        imgData[index]=0x00;
////                    }
//                }
//            }
//        }
//        return imgData;
    }

    //计算像素点的灰度
    public static int getGray(Color pixel) {
        return (pixel.getRed()*30+pixel.getGreen()*60+pixel.getBlue()*10)/100;
    }


    public static int getGray(int rgbpixel) {
        int r=(rgbpixel & 0xFF0000) >>> 16;
        int g=(rgbpixel & 0xFF00) >>> 8;
        int b=(rgbpixel & 0xFF);

        int luma=((r*299) + (g*587) +(b*114))/1000;
        return luma;
    }

    public static boolean isBlack(int rgbpixel) {
        int a=(rgbpixel & 0xFF000000) >>> 24;
        if(a<127){
            return false;
        }
        int r=(rgbpixel & 0xFF0000) >>> 16;
        int g=(rgbpixel & 0xFF00) >>> 8;
        int b=(rgbpixel & 0xFF);

        int luma=((r*299) + (g*587) +(b*114))/1000;
        return luma<127;
    }

    public static void main(String[] args) {
        System.out.println(getGray(0xFFFFFF));
        System.out.println(getGray(0));
    }

    public static String join(String[] arr){
        StringBuffer sb=new StringBuffer();
        for(String a:arr){
            sb.append(a);
        }
        return sb.toString();
    }
}

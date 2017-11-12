package image;

import image.ImageUtil;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guzy on 17/11/8.
 */
public class ImageToZpl {

    public static void main(String[] args) throws IOException {

//        System.out.println(0xFF ^ 0);
//        System.exit(1);

//        System.out.println(minimizeSameWord("000000000000000000000000000000000000000003F"));
//        System.exit(1);
        BufferedInputStream bis=null;

        try {
            bis = new BufferedInputStream(new FileInputStream("/Users/guzy/Desktop/23.bmp"));

            BufferedImage srcImage = ImageIO.read(bis);

            //minimizeRgbArr(srcImage);

//            byte[] result=new byte[srcImage.getWidth()*srcImage.getHeight()];
//            for(int i=0;i<srcImage.getHeight();i++){
//                for(int j=0;j<srcImage.getWidth();j++){
//                    result[i*srcImage.getWidth()+j]=(byte)srcImage.getRGB(j,i);
//                }
//            }


            String str = image2Zpl(srcImage);

//            System.out.println(data.getSize());
//            System.out.println(srcImage.getWidth()*srcImage.getHeight());
//
//            byte[] afterResult=result;
//            if(BufferedImage.TYPE_3BYTE_BGR==srcImage.getType()){
//                afterResult=new byte[srcImage.getWidth()*srcImage.getHeight()];
//                for(int i=0;i<srcImage.getHeight();i++){
//                    for(int j=0;j<srcImage.getWidth();j++){
//                        int r=result[(i*srcImage.getWidth()+j)*3];
//                        int g=result[(i*srcImage.getWidth()+j)*3+1];
//                        int b=result[(i*srcImage.getWidth()+j)*3+2];
//                        int luma=((r*299) + (g*587) +(b*114))/1000;
//                        afterResult[i*srcImage.getWidth()+j]=(byte)luma;
//                    }
//                }
//
//            }
//            bis.close();
//
//            bis = new BufferedInputStream(new FileInputStream("/Users/guzy/Desktop/2.png"));

            //imgDecode64(result);

            System.out.println( str);
            //System.out.println("^XA"+ImageWrapper.getImage(new ImageIcon(srcImage,"test"), LanguageType.ZPLII, Charsets.UTF_8)+"^XZ");
        }finally {
            if(bis!=null){
                bis.close();
            }
        }
    }

    public static String image2Zpl(BufferedImage srcImage) {
        DataBufferByte data=(DataBufferByte) getBinaryGrayImage(srcImage).getRaster().getDataBuffer();
        byte[] result=data.getData();
        return getImage(srcImage,result);
    }

    private static String minimizeSameWord(String str) {
        Matcher matcher=MULTI_W.matcher(str);
        while (matcher.find()){
            String group=matcher.group();
            int len=group.length();
            String c="";
            if(len>20){
                c=Character.toString((char) ('f' + len / 20));
            }
            if(len%20>0){
                c=c+Character.toString((char)('F'+len%20));
            }

            str=str.replaceFirst(group, c + group.charAt(0));
        }
        return str;
    }

    private static BufferedImage getBinaryGrayImage(BufferedImage srcImage) {
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        dstImage.getGraphics().drawImage(srcImage, 0, 0, null);
        for (int y = 0; y < dstImage.getHeight(); y++) {
            for (int x = 0; x < dstImage.getWidth(); x++) {
                Color color = new Color(dstImage.getRGB(x, y));
                //获取该点的像素的RGB的颜色
                Color newColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                dstImage.setRGB(x, y, newColor.getRGB());//new Color(getGray(pixel), getGray(pixel), getGray(pixel)).getRGB()
            }
        }
        return dstImage;
    }


    public static String getImage(BufferedImage image,byte[] rgbPixels) {
        byte[] imgData =//rgbPixels;
                ImageUtil.convertBytes4Zpl(rgbPixels, image.getWidth(), image.getHeight());

        int newW=(image.getWidth()+7)/8;
        String[] data=byte2HexStr(imgData,newW);

        int bytes=imgData.length;
        int perRow=newW;
        return String.format("^XA^GFA,%d,%d,%d,%s^FS^XZ", bytes,bytes,perRow,compress(data));
    }



    public static String compress(String[] data) {
        StringBuffer sb=new StringBuffer();
        String pre=null;
        for(String d:data){
           // System.out.println(d);
            String a=d;
            Matcher m=ZEROS.matcher(a);
            if(m.find()){
                a=m.replaceFirst(",");
            }

            m=ONES.matcher(a);
            if(m.find()){
                a=m.replaceFirst("!");
            }

            a=minimizeSameWord(a);

            if(pre!=null && a.equals(pre)){
                a=":";
            }else{
                pre=a;
            }
            sb.append(a);
        }
        return sb.toString();
    }


    static Pattern ZEROS=Pattern.compile("0+$"),ONES=Pattern.compile("1+$");

    static Pattern MULTI_W=Pattern.compile("([0-9A-Z])\\1{2,}");

    public static String[] byte2HexStr(byte[] b,int rowSize) {
        int len=b.length/rowSize;
       String[] arr=new String[len];
        for (int n=0;n<len;n++) {
            StringBuffer hs=new StringBuffer();
            for(int j=0;j<rowSize;j++){
                String stmp=Integer.toHexString(b[n*rowSize+j] & 0XFF);
                if (stmp.length()==1) hs.append("0");
                hs.append(stmp);
            }
           arr[n]=hs.toString().toUpperCase();
        }
        return arr;
    }
}

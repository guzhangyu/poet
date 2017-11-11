package image;

import image.ImageUtil;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
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

        BufferedInputStream bis=null;

        try {
            bis = new BufferedInputStream(new FileInputStream("/Users/guzy/Downloads/18A8675F-96A3-4F79-AE7B-CFDCB8FAF2F5.png"));

            BufferedImage srcImage = ImageIO.read(bis);

            //minimizeRgbArr(srcImage);

//            byte[] result=new byte[srcImage.getWidth()*srcImage.getHeight()];
//            for(int i=0;i<srcImage.getHeight();i++){
//                for(int j=0;j<srcImage.getWidth();j++){
//                    result[i*srcImage.getWidth()+j]=(byte)srcImage.getRGB(j,i);
//                }
//            }

            //getGrayImage(srcImage);


            DataBufferByte data=(DataBufferByte) srcImage.getRaster().getDataBuffer();

            System.out.println(data.getSize());
            byte[] result=data.getData();
//            bis.close();
//
//            bis = new BufferedInputStream(new FileInputStream("/Users/guzy/Desktop/2.png"));

            //imgDecode64(result);

            System.out.println(getImage(srcImage,result));
            //System.out.println("^XA"+ImageWrapper.getImage(new ImageIcon(srcImage,"test"), LanguageType.ZPLII, Charsets.UTF_8)+"^XZ");
        }finally {
            if(bis!=null){
                bis.close();
            }
        }
    }

    /**
     * 图片字节数组,base64解码
     * @param result
     * @throws IOException
     */
    private static byte[] imgDecode64(byte[] result) throws IOException {
        int tRecvCount=result.length;
        char[]tChars=new char[tRecvCount];

        for(int i=0;i<tRecvCount;i++)
            tChars[i]=(char)result[i];

        BASE64Decoder decoder = new BASE64Decoder();

        //Base64解码
        byte[] b = decoder.decodeBuffer(new String(result));
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }
        return b;
    }

    private static void getGrayImage(BufferedImage srcImage) {
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//		ColorSpace grayCS=ColorSpace.getInstance(ColorSpace.CS_GRAY);
//		ColorConvertOp colorConvertOp=new ColorConvertOp(grayCS,new RenderingHints());
//		colorConvertOp.filter(srcImage,dstImage);
        dstImage.getGraphics().drawImage(srcImage, 0, 0, null);
        for (int y = 0; y < dstImage.getHeight(); y++) {
            for (int x = 0; x < dstImage.getWidth(); x++) {
               // Color pixel = new Color(dstImage.getRGB(x, y));
                dstImage.setRGB(x, y, dstImage.getRGB(x, y));//new Color(getGray(pixel), getGray(pixel), getGray(pixel)).getRGB()
            }
        }
        //int[]result=((DataBufferInt) dstImage.getRaster().getDataBuffer()).getData();
    }

    /**
     * 缩小图片的字节数组
     * @param srcImage
     */
    private static void minimizeRgbArr(BufferedImage srcImage) {
        DataBufferByte data=(DataBufferByte) srcImage.getRaster().getDataBuffer();

        byte[] result1=data.getData();
        byte[] result=new byte[(result1.length+7)/8];
        for(int i=0;i<result.length;i++){
            int a=0;
            int count=0;
            for(int j=0;j<8;j++){
                if(i*8+j<result1.length){
                    a+=result1[i*8+j];
                    count++;
                }
            }
            if(count>0){
                a=a/count;
            }
            result[i]=(byte)a;
        }
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

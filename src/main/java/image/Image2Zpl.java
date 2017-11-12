package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在 erp中单独使用
 * 实现思路:
 * 1、获取图片的二值化字节数组 这一步是关键
 * 2、将字节数组转为十六进制
 * 3、压缩十六进制字符串 结尾为1、0或者与上一行相同的;相同的连续字符压缩
 * 4、拼凑ZPL编码，宽度需要扩大，因为需要时8个点(1字节)的整数倍
 * Created by guzy on 17/11/12.
 */
public class Image2Zpl {

    static Pattern ZEROS=Pattern.compile("0+$"),ONES=Pattern.compile("1+$"),MULTI_W=Pattern.compile("([0-9A-Z])\\1{2,}");

    public static void main(String[] args) throws IOException {
        System.out.println("^XA"+image2Zpl(ImageIO.read(new URL("http://jxc-storage.oss-cn-shanghai.aliyuncs.com/0cac85f28e42f6f811fc63b4067cae21.jpg")))+"^XZ");
    }

    public static String image2Zpl(BufferedImage image) {
        //获取图片的字节数组
        DataBufferByte data=(DataBufferByte) getBinaryGrayImage(image).getRaster().getDataBuffer();
        byte[] imgData=data.getData();

        int newW=(image.getWidth()+7)/8;//实际每行字节大小，8个点，每个点1位，共8位
        String[] strs=byte2HexStr(imgData,newW);
        int bytes=imgData.length;
        return String.format("^XA^GFA,%d,%d,%d,%s^FS^XZ", bytes, bytes, newW, compress(strs));
    }


    /**
     * 获取二值化图，并取反
     * @param srcImage
     * @return
     */
    private static BufferedImage getBinaryGrayImage(BufferedImage srcImage) {
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        dstImage.getGraphics().drawImage(srcImage, 0, 0, null);
        for (int y = 0; y < dstImage.getHeight(); y++) {
            for (int x = 0; x < dstImage.getWidth(); x++) {
                Color color = new Color(dstImage.getRGB(x, y));
                //获取该点的像素的RGB的颜色
                Color newColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                dstImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return dstImage;
    }

    /**
     * 压缩图片数据
     * @param data
     * @return
     */
    private static String compress(String[] data) {
        StringBuffer sb=new StringBuffer();
        String pre=null;
        for(String d:data){
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

    /**
     * 十六进制串中相同字母压缩
     * @param str
     * @return
     */
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



    /**
     * 字节数组转为十六进制
     * @param b
     * @param rowSize
     * @return
     */
    private static String[] byte2HexStr(byte[] b,int rowSize) {
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

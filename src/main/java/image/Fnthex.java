package image;

import com.sun.deploy.util.ArrayUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by guzy on 17/11/8.
 */
public class Fnthex {

    public static BufferedImage source=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

    public static Graphics2D gs=source.createGraphics();

    public static void main(String[] args) {
        System.out.println(getFontHexWithSize("我是中国人啊啊啊啊啊啊", 150, 50, "宋体"));
    }

    public static String getFontHexWithSize(String content,int width,int size,String fontName){
        if(content==null || "".equals(content)){
            return "";
        }

        Font f=null;
        width=(width+7)/8*8;
        if("宋体".equals(fontName)){
            f=new Font("simsun",Font.PLAIN,size);
        }else if("黑体".equals(fontName)){
            f=new Font("simhei",Font.BOLD,size);
        }else{
            f=new Font("simsun",Font.PLAIN,size);
        }

        int height=size;
        BufferedImage image=new BufferedImage(width,height*3,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=image.createGraphics();
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString(content, 1, (int) (height * 0.88));
        g2.drawString("哈哈",1,(int)(height*0.88*2));

        g2.dispose();

        return getImage(image);
    }

    public static String getFontHexWithWidth(String content,int x,int y,int width,int maxHeight,String fontName){
        if(content==null || "".equals(content)){
            return "";
        }

        Font f=null;
        width=(width+7)/8*8;
        int size=width/content.length();
        int retryFlag=1;
        if(size>maxHeight){
            size=maxHeight;
            if("宋体".equals(fontName)){
                f=new Font("simsun",Font.PLAIN,size);
            }else if("黑体".equals(fontName)){
                f=new Font("simhei",Font.BOLD,size);
            }else{
                f=new Font("simsun",Font.PLAIN,size);
            }
        }else{
            while(true){
                if("宋体".equals(fontName)){
                    f=new Font("simsun",Font.PLAIN,size);
                }else if("黑体".equals(fontName)){
                    f=new Font("simhei",Font.BOLD,size);
                }else{
                    f=new Font("simsun",Font.PLAIN,size);
                }
                gs.setFont(f);

                FontMetrics fontMetrics=gs.getFontMetrics();
                Rectangle2D stringBounds=fontMetrics.getStringBounds(content,gs);
                int nw=(int)stringBounds.getWidth();

                if(nw>width){
                    size--;
                    if(retryFlag==1){
                        break;
                    }
                    retryFlag=0;
                }else{
                    if(size>=maxHeight){
                        break;
                    }
                    size++;
                    retryFlag=1;
                }
            }
        }

        int height=size;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=image.createGraphics();
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString(content,1,(int)(height*0.88));

        g2.dispose();

        return getImage(image);
    }

    private static String getImage(BufferedImage image) {
        int w=image.getWidth();
        int h=image.getHeight();
        boolean black[]=getBlackPixels(image,w,h);
        int hex[]=getHexValues(black);

        String data=ints2Hex(hex);

        int bytes=data.length()/2;
        int perRows=bytes/h;


        System.out.println(String.format("perRow:%d,width:%d,height:%d,total:%d",perRows,w,h,data.length()));
        String result = compress(h, data, perRows);

        //System.out.println(String.format("^GFA,%d,%d,%d,%s",bytes,bytes,perRows,data));
        return String.format("^GFA,%d,%d,%d,%s",bytes,bytes,perRows,result);
    }

    private static String compress(int h, String data, int perRows) {
        String[] arr=new String[h];
        String pre=null;
        for(int i=0;i<h;i++){
            int end=(i+1)*perRows*2;
            if(end>data.length()){
                end=data.length();
            }
            arr[i]=data.substring(i*perRows*2,end).replaceAll("0+$",",").replaceAll("1+$","!");
            if(pre!=null && arr[i].equals(pre)){
                arr[i]=":";
            }else{
                pre=arr[i];
            }
        }
        return join(arr);
    }

    private static String join(String[] arr){
        StringBuffer sb=new StringBuffer();
        for(String a:arr){
            sb.append(a);
        }
        return sb.toString();
    }


    private static char[]HEX="0123456789ABCDEF".toCharArray();
    private static String ints2Hex(int[] ints) {
        char[] hexChars=new char[ints.length*2];
        for(int i=0;i<ints.length;i++){
            hexChars[i*2]=HEX[(ints[i] & 0xF0) >>4];
            hexChars[i*2+1]=HEX[ints[i] & 0x0F];
        }
        return new String(hexChars);
    }

    private static int[] getHexValues(boolean[] black) {
        int hex[]=new int[(int)(black.length/8)];
        for(int i=0;i<hex.length;i++){
            for(int k=0;k<8;k++){
                hex[i]+=(black[8*i+k]?1:0)<<7-k;
            }
        }
        return hex;
    }

    private static boolean[] getBlackPixels(BufferedImage image, int w, int h) {
        int[] rgbPixels=((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int i=0;
        boolean[] pixels=new boolean[rgbPixels.length];
        for(int rgbpixel:rgbPixels){
            pixels[i++]=isBlack(rgbpixel);
        }
        return pixels;
    }

    private static boolean isBlack(int rgbpixel) {
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
}

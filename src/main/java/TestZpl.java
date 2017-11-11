import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by guzy on 17/11/3.
 */
public class TestZpl {

    public static void main (String argv[]) throws Exception{

        //192.168.54.112
        //124.160.56.14

        String str1="^XA^MMP^CI14^FO30,150^GB790,2,1^FS^FO30,270^GB790,2,1^FS^FO30,350^GB790,2,1^FS^FO30,466^GB790,2,1^FS^FO30,562^GB790,2,1^FS^FO30,735^GB790,2,1^FS^FO30,897^GB790,2,1^FS^FO30,982^GB790,2,1^FS^FO30,1060^GB633,2,1^FS^FO30,1140^GB790,2,1^FS^FO704,33^GB2,117,2^FS^FO187,735^GB2,162,2^FS^FO663,735^GB2,162,2^FS^FO663,982^GB2,158,2^FS^FO40,277^GB70,70,6^FS^FO37,375^GC72,6,B^FS^FO37,985^GC72,6,B^FS^FO675,756^BQN,2,5.0^FD3341954655139^FS^FO675,1000^BQN,2,5.0^FD3341954655139^FS^FO78,585^BY3.0^BCN,120,Y,N,N^FD3341954655139^FS^FO250,908^BY3.0^BCN,50,Y,N,N^FD3341954655139^FS^FO36,174^A@N,65,65,^FD浙-杭州市^FS^FO118,290^A@N,38,38,^FD^FS^FO118,357^A@N,33,33,^FD超级ERP^FS^FO483,357^A@N,33,33,^FD18688888888^FS^FO118,396^A@N,33,33,^FB690,2.0,0,L,0^FD浙江省 杭州市 滨江区 江南大道588号恒鑫大厦10F光云科技^FS^FO118,475^A@N,23,23,^FDghuhg^FS^FO483,475^A@N,23,23,^FD18657709988^FS^FO118,510^A@N,23,23,^FD   义务四海大道^FS^FO46,740^A@N,20,20,^FD2017/11/03^FS^FO46,775^A@N,30,30,^FD12:47:31^FS^FO46,820^A@N,33,33,^FD1/1^FS^FO118,996^A@N,20,20,^FD超级ERP^FS^FO483,996^A@N,20,20,^FD18688888888^FS^FO118,1028^A@N,20,20,^FB550,2.0,0,L,0^FD浙江省 杭州市 滨江区 江南大道588号恒鑫大厦10F光云科技^FS^FO118,1076^A@N,20,20,^FDghuhg^FS^FO483,1076^A@N,20,20,^FD18657709988^FS^FO118,1120^A@N,20,20,^FD   义务四海大道^FS^FO675,1360^A@N,33,33,^FB690,5.0,0,L,0^FD文件^FS^FO710,47^A@N,41,41,^FB90,2,0,L,0^FD标准快递^FS^FO54,290^A@N,41,41,^FD集^FS^FO54,390^A@N,41,41,^FD收^FS^FO54,1000^A@N,41,41,^FD收^FS^FO37,490^A@N,55,55,^FD寄^FS^FO37,1086^A@N,55,55,^FD寄^FS^FO65,860^A@N,20,20,^FD打印时间^FS^FO198,763^A@N,20,20,^FB460,2,0,L,0^FD快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达，您的签字代表您已经签收此包裹，并已确认商品信息无误，包装完好，没有划痕，破损等表面质量问题。^FS^FO558,860^A@N,20,20,^FD签收栏^FS^FO675,1400^A@N,33,33,^FD已验视^FS^FO4.0,1148.0,0^A@N,36.0,36.0^FB747.0,3^FDERP小二 18657709988 淘宝 ^FS^FO468.0,1366.0,0^A@N,36.0,36.0^FB315.0,2^FDbbgfgfdgf^FS^FO527.0,1280.0,2^GB212.0,42.0^FS^FO4.0,1275.0,0^A@N,36.0,36.0^FB419.0,5^FD桔梗 桔梗 1^FS^FO546.0,1311.0,2^A@N,21.0,21.0^FB254.0,2^FDdsd^FS^FO332.0,1229.0,0^A@N,36.0,36.0^FB466.0,1^FD 2017-08-04 22:08:40^FS^XZ";

        printStr(str1);

        System.out.println(str1.replaceAll("\\^FS","^FS\n"));
    }

    private static void printStr(String str1) throws IOException {

        Socket clientSocket=new Socket("192.168.54.112",9100);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );

        System.out.println(str1);
        outToServer.write(str1.getBytes("gbk"));
        clientSocket.close();
    }
}

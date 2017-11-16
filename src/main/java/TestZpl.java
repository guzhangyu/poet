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

        //~DEE:GB.DAT,29780,~DEE:MSUNG24,546172,24,24,21,8,
        //^SEE:GB.DAT^CWJ,E:MSUNG24^CI14
        //^WD*:*.*
        String str1="^XA^MMP^CI14^FO4.8,98.4^GB772,0,1^FS^FO4.8,210.4^GB772,0,1^FS^FO4.8,287.2^GB772,0,1^FS^FO566.4,340^GB211.2,0,1^FS^FO4.8,400.8^GB561.6,0,1^FS^FO4.8,517.6^GB561.6,0,1^FS^FO4.8,692^GB772,0,1^FS^FO4.8,841.6^GB772,0,1^FS^FO4.8,980^GB772,0,1^FS^FO4.8,1080^GB561.6,0,1^FS^FO4.8,1172^GB772,0,1^FS^FO51.2,287.2^GB0,230.4,1^FS^FO566.4,287.2^GB0,404.8,1^FS^FO368,692^GB0,149.6,1^FS^FO601.6,692^GB0,149.6,1^FS^FO51.2,980^GB0,192,1^FS^FO566.4,980^GB0,192,1^FS^FO84.8,539.2^AEN,22,22^BY2.0^BCN,80,Y,N,N^FD78896200020063^FS^FO265.6,857.6^BY3.0^A@N,22,22^BCN,72,Y,N,N^FD78896200020063^FS^FO619.2,700^BQN,2,5.0^FD78896200020063^FS^FO584,986.8^BQN,2,4.0^FD78896200020063^FS^FO19.2,296.8^A@N,28,28，^FB35,2.0,0,L,0^FD收件^FS^FO19.2,411.2^A@N,28,28，^FB35,2.0,0,L,0^FD寄件^FS^FO19.2,985.6^A@N,28,28，^FB35,2.0,0,L,0^FD收件^FS^FO19.2,1088^A@N,28,28，^FB35,2.0,0,L,0^FD寄件^FS^FO17.6,107.2^AVN,81,81,^FD浙江-杭州-滨江^FS^FO16,215.2^AHN,54,54,^FD杭州萧山分拨仓^FS^FO60,287.6^A@N,27,27,^FD我叫补发的收货人 17876545678^FS^FO60,328.8^A@N,27,27,^FB515,2.0,0,L,0^FD北京 北京市 {receiver_district} {receiver_address}^FS^FO60,402^A@N,27,27,^FD{send_name} {send_mobile}^FS^FO60,441.6^A@N,27,27,^FB515,2.0,0,L,0^FD{send_state} {send_city} {send_district} {send_address}^FS^FO60,981^A@N,27,27,^FD{receiverName} {receiverPhone}^FS^FO60,1007.2^A@N,27,27,^FB515,2.0,0,L,0^FD{receiver_state} {receiver_city} {receiver_district} {receiver_address}^FS^FO60,1081^A@N,27,27,^FD{send_name} {send_mobile}^FS^FO60,1109.6^A@N,27,27,^FB515,2.0,0,L,0^FD{send_state} {send_city} {send_district} {send_address}^FS^FO628.8,292^A@N,26,26,^FD服务^FS^FO374.4,697.6^A@N,26,26,^FD签收人:^FS^FO374.4,778.4^A@N,26,26,^FD时间:^FS^FO15.2,696^A@N,16,16,^FB336.8,2,0,L,0^FD快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达，您的签字代表您已经签收此包裹，并已确认商品信息无误，包装完好，没有划痕，破损等表面质量问题。^FS^XZ";
                //"^XA^MMP^CI14^FO30,150^GB790,2,1^FS^FO30,270^GB790,2,1^FS^FO30,350^GB790,2,1^FS^FO30,466^GB790,2,1^FS^FO30,562^GB790,2,1^FS^FO30,735^GB790,2,1^FS^FO30,897^GB790,2,1^FS^FO30,982^GB790,2,1^FS^FO30,1060^GB633,2,1^FS^FO30,1140^GB790,2,1^FS^FO704,33^GB2,117,2^FS^FO187,735^GB2,162,2^FS^FO663,735^GB2,162,2^FS^FO663,982^GB2,158,2^FS^FO40,277^GB70,70,6^FS^FO37,375^GC72,6,B^FS^FO37,985^GC72,6,B^FS^FO675,756^BQN,2,5.0^FD3341954655139^FS^FO675,1000^BQN,2,5.0^FD3341954655139^FS^FO78,585^BY3.0^BCN,120,Y,N,N^FD3341954655139^FS^FO250,908^BY3.0^BCN,50,Y,N,N^FD3341954655139^FS^FO36,174^A@N,65,65,^FD浙-杭州市^FS^FO118,290^A@N,38,38,^FD^FS^FO118,357^A@N,33,33,^FD超级ERP^FS^FO483,357^A@N,33,33,^FD18688888888^FS^FO118,396^A@N,33,33,^FB690,2.0,0,L,0^FD浙江省 杭州市 滨江区 江南大道588号恒鑫大厦10F光云科技^FS^FO118,475^A@N,23,23,^FDghuhg^FS^FO483,475^A@N,23,23,^FD18657709988^FS^FO118,510^A@N,23,23,^FD   义务四海大道^FS^FO46,740^A@N,20,20,^FD2017/11/03^FS^FO46,775^A@N,30,30,^FD12:47:31^FS^FO46,820^A@N,33,33,^FD1/1^FS^FO118,996^A@N,20,20,^FD超级ERP^FS^FO483,996^A@N,20,20,^FD18688888888^FS^FO118,1028^A@N,20,20,^FB550,2.0,0,L,0^FD浙江省 杭州市 滨江区 江南大道588号恒鑫大厦10F光云科技^FS^FO118,1076^A@N,20,20,^FDghuhg^FS^FO483,1076^A@N,20,20,^FD18657709988^FS^FO118,1120^A@N,20,20,^FD   义务四海大道^FS^FO675,1360^A@N,33,33,^FB690,5.0,0,L,0^FD文件^FS^FO710,47^A@N,41,41,^FB90,2,0,L,0^FD标准快递^FS^FO54,290^A@N,41,41,^FD集^FS^FO54,390^A@N,41,41,^FD收^FS^FO54,1000^A@N,41,41,^FD收^FS^FO37,490^A@N,55,55,^FD寄^FS^FO37,1086^A@N,55,55,^FD寄^FS^FO65,860^A@N,20,20,^FD打印时间^FS^FO198,763^A@N,20,20,^FB460,2,0,L,0^FD快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达，您的签字代表您已经签收此包裹，并已确认商品信息无误，包装完好，没有划痕，破损等表面质量问题。^FS^FO558,860^A@N,20,20,^FD签收栏^FS^FO675,1400^A@N,33,33,^FD已验视^FS^FO4.0,1148.0,0^A@N,36.0,36.0^FB747.0,3^FDERP小二 18657709988 淘宝 ^FS^FO468.0,1366.0,0^A@N,36.0,36.0^FB315.0,2^FDbbgfgfdgf^FS^FO527.0,1280.0,2^GB212.0,42.0^FS^FO4.0,1275.0,0^A@N,36.0,36.0^FB419.0,5^FD桔梗 桔梗 1^FS^FO546.0,1311.0,2^A@N,21.0,21.0^FB254.0,2^FDdsd^FS^FO332.0,1229.0,0^A@N,36.0,36.0^FB466.0,1^FD 2017-08-04 22:08:40^FS^XZ";

        printStr(str1);

        System.out.println(str1.replaceAll("\\^FS","^FS\n"));
    }

    private static void printStr(String str1) throws IOException {

        Socket clientSocket=new Socket("192.168.54.112",9100);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );

        //System.out.println(str1);
        outToServer.write(str1.getBytes("gbk"));
        clientSocket.close();
    }
}

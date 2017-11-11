import javax.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

class javanet
{
	byte[] dotfont;
	String s_prt = "^XA", s_prt_buffer="";
	public static void main (String argv[]) throws Exception
	{
//		try {
//			javanet a = new javanet();
//                        a.setCommand();
//			String str = a.getCommand();
//			System.out.println(str);
//			Socket clientSocket=new Socket("172.28.4.26",9100);
//			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );
//			outToServer.writeBytes(str);
//			clientSocket.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		URL url=new URL("http://jxc-storage.oss-cn-shanghai.aliyuncs.com/0cac85f28e42f6f811fc63b4067cae21.jpg");
//		HttpURLConnection httpUrl=(HttpURLConnection)url.openConnection();
//		httpUrl.connect();
//		BufferedInputStream bis=new BufferedInputStream(httpUrl.getInputStream());
	}


	public javanet() throws Exception {
		File file = new File("ts24.lib");
		FileInputStream fis = new FileInputStream(file);
		dotfont = new byte[fis.available()];
		fis.read(dotfont);
		fis.close();

	}
	protected void setCommand() {
		String barcode = "ABCD1234567890";			
		printCN("南京康顿数码ABCD", 100, 50,30,34,2);		
		printCN("精彩每一天", 100, 100,30,34,2);
		printChar(barcode,100,150,30,50);
		printBarcode(barcode, 100, 220);	
	}
	protected String getCommand() {	
		return s_prt + s_prt_buffer+"^XZ";
	}
	
	public void print(String str) throws PrintException {

		PrintService psZebra = PrintServiceLookup.lookupDefaultPrintService();
		if (psZebra == null) {
			System.out.println("没有发现条码打印机.");
			return;
		}
		DocPrintJob job = psZebra.createPrintJob();
		byte[] by = str.getBytes();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(by, flavor, null);
		job.print(doc, null);
	}


	
/**
 * 打印条形码 
 * @param barcode 字符串
 * @param x x坐标
 * @param y y坐标
 */
	protected void printBarcode(String barcode,int x, int y) {
		System.out.println(barcode);
//	s_prt_buffer +=  "^BY3^FS^FO"+x+","+y+"^BEN,"+h+"^FD" + barcode + "\n";
	s_prt_buffer +=  "^FO"+x+","+y+"^BY2,3,60^BCN,,Y,N^FD" + barcode + "\n";
}

/**
 * 打印英文字符，数字
 * @param str
 * @param x
 * @param y
 */
	protected void printChar(String str,int x, int y,int h,int w) {
		System.out.println(str);
	s_prt_buffer +="^FO"+x+","+y+"^A0,"+h+","+w+"^FD"+str+"^FS";
}
	protected void printCharR(String str,int x, int y,int h,int w) {
		System.out.println(str);
	s_prt_buffer +="^FO"+x+","+y+"^A0R,"+h+","+w+"^FD"+str+"^FS";
}
/**
 * 打印中文字符串
 * @param strCN
 * @param x
 * @param y
 */
	protected void printCN(String strCN, int x, int y,int h,int w,int b) {
		System.out.println(strCN);
		byte[] ch = str2bytes(strCN);
		for (int off = 0; off < ch.length;) {
			if (((int) ch[off] & 0x00ff) >= 0xA0) {
				int qcode = ch[off] & 0xff;
				int wcode = ch[off + 1] & 0xff;
				s_prt_buffer = s_prt_buffer
						+ String.format("^FO%d,%d^XG0000%01X%01X,%d,%d^FS\n", x,
								y, qcode, wcode,b,b);
				s_prt +=  String.format("~DG0000%02X%02X,00072,003,\n", qcode,
								wcode);
				qcode = (qcode + 128 - 32) & 0x00ff;
				wcode = (wcode + 128 - 32) & 0x00ff;
				int offset = ((int) qcode - 16) * 94 * 72 + ((int) wcode - 1)
						* 72;
				for (int j = 0; j < 72; j += 3) {
					qcode = (int) dotfont[j + offset] & 0x00ff;
					wcode = (int) dotfont[j + offset + 1] & 0x00ff;
					int qcode1 = (int) dotfont[j + offset + 2] & 0x00ff;
					s_prt +=  String.format("%02X%02X%02X\n", qcode, wcode,
									qcode1);
				}
				x = x + 25*b;
				off = off + 2;
			} else if (((int) ch[off] & 0x00FF) < 0xA0) {
				;
				printChar(String.format("%c",ch[off]), x,  y, h, w);
//				s_prt_buffer += String.format("^FO"+x+","+y+"^A0,"+h+","+w+"^FD%c^FS\n",ch[off]);
//				s_prt_buffer += "^FO"+x+","+y+"^A0,"+h+",20^FD"+ch[off]+"^FS\n";
				x = x + 15;
				off++;
			}
		}
	}

	byte[] str2bytes(String s) {
		if (null == s || "".equals(s)) {
			return null;
		}
		byte[] abytes = null;
		try {
			abytes = s.getBytes("gb2312");
		} catch (UnsupportedEncodingException ex) {
		}
		return abytes;
	}
}
package image;

import java.io.*;

/**
 * Created by guzy on 17/11/10.
 */
public class MyPng {
    public static final int head1 = 0x89504e47;
    public static final int head2 = 0x0d0a1a0a;
    private final int max=10;

    boolean tRNS;
    boolean plte;
    int idatCounts;

    String strName;

    int IHDR_length;
    int PLTE_length;
    int tRNS_length;
    int[]IDAT_length=new int[max];
    int IEND_length;

    byte[]IHDR_ChunkTypeCode;
    byte[]PLTE_ChunkTypeCode;
    byte[] tRNS_ChunkTypeCode;
    byte[][]IDAT_ChunkTypeCode=new byte[max][];
    byte[] IEND_ChunkTypeCode;

    byte[]IHDR_Data;
    byte[]PLTE_Data;
    byte[]tRNS_Data;
    public byte[][] IDAT_Data=new byte[max][];
    byte[]IEND_Data;

    int IHDR_crc;
    int PLTE_crc;
    int tRNS_crc;
    int[]IDAT_crc=new int[max];
    int IEND_crc;

    public int width;
    public int height;
    public byte bitDepth;
    public byte colorType;
    public byte compressionMethod;
    public byte filterMethod;
    public byte interlaceMethod;

    public MyPng(String name){
        this.strName=name;
    }


    public void setData(int length,byte[] chunkTypeCode,byte[] idata_Data,int crc){
        for(int i=0;i<max;i++){
            if(IDAT_Data[i]==null){
                IDAT_length[i]=length;
                IDAT_ChunkTypeCode[i]=new byte[4];
//                for(int a=0;a<chunkTypeCode.length;a++)
                IDAT_ChunkTypeCode[i]=chunkTypeCode;
                System.out.println(" "+new String(chunkTypeCode));
                IDAT_Data[i]=idata_Data;
                IDAT_crc[i]=crc;
                idatCounts++;
                break;
            }
        }
    }


    public static MyPng getPngData(String path){
        DataInputStream dataInput=null;
        FileInputStream fis=null;

        byte[]pngHead=new byte[8];//8b

        int length_Data;//4b
        char[]crc_Data=new char[2];

        MyPng png=new MyPng(path);
        try{
            fis=new FileInputStream(path);
            dataInput=new DataInputStream(fis);

            //png head
            System.out.println(dataInput.readInt());
            System.out.println(dataInput.readInt());
            System.out.println("======");
            System.out.println(0x89504e47);
            System.out.println(0x0d0a1a0a);

            //other:Length Chunk_Type_Code Chunk_Data CRC
            while(true){
                //dataInput.read(length_Data)
                length_Data=dataInput.readInt();
                System.out.println("l="+length_Data);

                byte[] ChunkTypeCode_Data=new byte[4];//4b
                //4b ASC-11 1char2b
                for(int i=0;i<ChunkTypeCode_Data.length;i++){
                    ChunkTypeCode_Data[i]=dataInput.readByte();//32
                }

                String str=new String(ChunkTypeCode_Data);//ASC-11 java
                //class编码采用utf8
                System.out.println("TYPE:"+str);

                //PLTE,IDAT,IEND,tRNS
                if("IHDR".contentEquals(str.trim())){
                    System.out.println("enter IHDR");
                    png.IHDR_length=length_Data;

                    png.IHDR_ChunkTypeCode=ChunkTypeCode_Data;

                    //png.IHDR_Data=new byte[png.IHDR_length];
                    png.width=dataInput.readInt();
                    png.height=dataInput.readInt();
                    png.bitDepth=dataInput.readByte();
                    png.colorType=dataInput.readByte();
                    png.compressionMethod=dataInput.readByte();
                    png.filterMethod=dataInput.readByte();
                    png.interlaceMethod=dataInput.readByte();
                   // dataInput.read(png.IHDR_Data);

                    png.IHDR_crc=dataInput.readInt();
                }else if("PLTE".contentEquals(str.trim())){
                    png.plte=true;
                    png.PLTE_length=length_Data;
                    png.PLTE_ChunkTypeCode=ChunkTypeCode_Data;

                    png.PLTE_Data=new byte[png.PLTE_length];
                    dataInput.read(png.PLTE_Data);
                    png.PLTE_crc=dataInput.readInt();
                }else if("IDAT".contentEquals(str.trim())){
                    byte[]data=new byte[length_Data];
                    dataInput.read(data);
                    png.setData(length_Data,ChunkTypeCode_Data,data,dataInput.readInt());
                }else if("IEND".contentEquals(str.trim())){
                    return png;
                }else if("tRNS".contentEquals(str.trim())){
                    png.tRNS=true;
                    png.tRNS_length=length_Data;

                    png.tRNS_ChunkTypeCode=ChunkTypeCode_Data;
                    png.tRNS_Data=new byte[length_Data];
                    dataInput.read(png.tRNS_Data);
                    png.tRNS_crc =dataInput.readInt();
                }else{
                    System.out.println(length_Data);
                    dataInput.skipBytes(length_Data+4);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //is.close();
                if(fis!=null){
                    fis.close();
                }
                if(dataInput!=null){
                    dataInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void putFile(MyPng myPng,String newPath){
        System.out.println("begin");
        DataOutputStream dos=null;
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(newPath);
            dos=new DataOutputStream(fos);

            //file
            dos.writeInt(0x89504e47);
            dos.writeInt(0x0d0a1a0a);

            //IHDR;int4 short2 short2
            dos.writeInt(13);
            dos.writeInt(0x49484452);
            dos.writeInt(myPng.width);
            dos.write(myPng.height);
            dos.writeByte(myPng.bitDepth);
            dos.writeByte(myPng.colorType);
            dos.writeByte(myPng.compressionMethod);
            dos.writeByte(myPng.filterMethod);
            dos.writeByte(myPng.interlaceMethod);
            dos.writeInt(myPng.IHDR_crc);

            //PLTE
            dos.writeBoolean(myPng.plte);
            if(myPng.plte){
                dos.writeInt(myPng.PLTE_length);
                System.out.println(myPng.PLTE_length);

                //dos.write(myPng.PLTE_ChunkTypeCode);
                dos.writeInt(0x504C5445);
                for(int i=0;i< myPng.PLTE_length;i++){
                    dos.writeByte(myPng.PLTE_Data[i]);
                }
                dos.writeInt(myPng.PLTE_crc);
            }

            dos.writeBoolean(myPng.tRNS);
            if(myPng.tRNS){
                dos.writeInt(myPng.tRNS_length);
                dos.writeInt(0x74524E53);

                dos.write(myPng.tRNS_ChunkTypeCode);
                for(int i=0;i<myPng.tRNS_length;i++){
                    dos.writeByte(myPng.tRNS_Data[i]);
                }
                dos.writeInt(myPng.tRNS_crc);
            }

            //IDAT
            dos.writeInt(myPng.idatCounts);
            System.out.println("png.idatCounts:"+myPng.idatCounts);

            for(int i=0;i<myPng.idatCounts;i++){
                dos.writeInt(myPng.IDAT_length[i]);
                System.out.println(myPng.IDAT_length[i]);
                dos.writeInt(0x49444154);
                for(int j=0;j<myPng.IDAT_length[i];j++){
                    dos.writeByte(myPng.IDAT_Data[i][j]);
                    System.out.println();
                }
                System.out.println(myPng.IDAT_Data[i].length+" i="+i);
                dos.writeInt(myPng.IDAT_crc[i]);
            }

            //END
            System.out.println("end---");
            dos.writeInt(0x00000000);
            dos.writeInt(0x49454e44);
            dos.writeInt(0xae426082);

            System.out.println(dos.size());
            dos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(dos!=null){
                    dos.close();
                }
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //convertToBmp();
       // putFile(getPngData("/Users/guzy/Desktop/2.png"),"/Users/guzy/Desktop/22.png");
      //  writeBytes(readFully(new FileInputStream("/Users/guzy/Desktop/2.png")),"/Users/guzy/Desktop/22.png");
    }



    public static void writeBytes(byte[]bytes,String path){
        try {
            FileOutputStream fos=new FileOutputStream(path);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

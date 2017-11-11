package com.poet;

import com.alibaba.druid.support.json.JSONUtils;

import java.nio.ByteBuffer;
/**
 * Created by guzy on 16/7/25.
 */
public class Utils {

//    public static void main(String[] args) {
//
//        //JSONUtils.deserializeObject();
//                //System.out.println(System.currentTimeMillis());
//    }


    public static void main(String[] args){
        Long a=3243277423432L;
        Long b=3243247723432L;
        System.out.println(a.equals(b));

        ByteBuffer bf=ByteBuffer.allocate(1024);
        bf.put(new byte[]{4,5,6});
        System.out.println(bf);

        bf.mark();
//        bf.rewind();
        //bf.flip();
        bf.position(1);
        System.out.println(bf);

        bf.reset();
        System.out.println(bf);
    }
}

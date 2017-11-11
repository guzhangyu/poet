package com.poet.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.core.convert.converter.Converter;
import sun.reflect.annotation.AnnotationType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guzy on 16/7/24.
 */
public class DateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String s) {
        if(StringUtils.isBlank(s)){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(getFormatFromStr(s));
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String[] dateFormat=new String[]{"yyyy","MM","dd","HH","mm","ss"};

    private static final  Pattern pattern=Pattern.compile("\\d+");

    private String getFormatFromStr(String str){
       Matcher matcher=pattern.matcher(str);
        int i=0;
        while (matcher.find()){
           str=str.replaceFirst("\\d+",dateFormat[i++]);
        }
        return str;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(URLEncoder.encode("我 放大负担 sssdd", "utf-8"));
//        //System.out.println(new DateConverter().convert("2017-12-23"));
//    }

    public static void main(String[] args){
//        Long a=3247742343268888886L;
//        Long b=3247742343268888886L;
//        System.out.println(a==b);


        System.out.println( AnnotationType.getInstance(TestAno.class).isInherited());;

        int money=1167+2399+696+74+287+110;
        System.out.println(money);
    }
}

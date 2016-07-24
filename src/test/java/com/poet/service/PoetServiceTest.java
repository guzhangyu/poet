package com.poet.service;

import com.poet.BaseTest;
import com.poet.po.Poet;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by guzy on 16/7/24.
 */
public class PoetServiceTest extends BaseTest {

    @Resource
    IPoetService poetService;

    @Test
    public void insert(){
        Poet poet=new Poet();
        poet.setTitle("TEST");
        poet.setContent("testContent");
        poet.setAuthor("王柱成");
        poet.setWriteTime(new Date());
        System.out.println(poetService.insert(poet));
    }
}

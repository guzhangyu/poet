package com.poet.dao;

import com.poet.BaseTest;
import com.poet.po.Poet;
import com.poet.vo.PoetCondition;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
public class PoetDaoTest extends BaseTest {

    @Resource
    PoetDao poetDao;

    @Test
    public void insert(){
        Poet poet=new Poet();
        poet.setTitle("TEST");
        poet.setContent("testContent");
        poet.setAuthor("王柱成");
        poet.setWriteTime(new Date());
        int id= poetDao.insert(poet);
        System.out.println(id);
    }

    @Test
    public void query(){
        PoetCondition poet=new PoetCondition();
        poet.setStartTime(new Date(System.currentTimeMillis()-10000000));
        poet.setEndTime(new Date());
        poet.setAuthor("王柱成");
        List<Poet>list= poetDao.query(poet,null);

        System.out.println(list.size());
    }

    @Test
    public void count(){
        PoetCondition poet=new PoetCondition();
        poet.setStartTime(new Date(System.currentTimeMillis()-10000000));
        poet.setEndTime(new Date());
        poet.setAuthor("王柱成");
        System.out.println(poetDao.count(poet));
    }



}

package com.poet.service.impl;

import com.poet.dao.PoetDao;
import com.poet.po.Poet;
import com.poet.service.IPoetService;
import com.poet.vo.PageList;
import com.poet.vo.Pagination;
import com.poet.vo.PoetCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
@Service
public class PoetService implements IPoetService {

    @Resource
    PoetDao poetDao;

    @Override
    public int insert(Poet poet) {
        return poetDao.insert(poet);
    }

    public List<Poet> query(PoetCondition condition){
//        PoetCondition condition=new PoetCondition();
//        condition.setTitle(poet.getTitle());
//        condition.setContent(poet.getContent());
//        condition.setAuthor(poet.getAuthor());
//        condition.setStartTime(startTime);
        return poetDao.query(condition,null);
    }

    public PageList<Poet> queryPageList(PoetCondition condition,Pagination pagination){
        int count=poetDao.count(condition);
        PageList<Poet> pageList=new PageList<Poet>();
        pageList.setCount(count);
        pageList.setPages(count/pagination.getPageSize());
        if(count<=pagination.getStartRow()){
            pageList.setList(poetDao.query(condition,pagination));
        }else{
            pageList.setList(new ArrayList<Poet>());
        }
        return pageList;
    }
}

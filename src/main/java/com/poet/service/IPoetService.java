package com.poet.service;

import com.poet.po.Poet;
import com.poet.vo.PageList;
import com.poet.vo.Pagination;
import com.poet.vo.PoetCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
public interface IPoetService {

    public int insert(Poet poet);

    public List<Poet> query(PoetCondition condition);

    public PageList<Poet> queryPageList(PoetCondition condition,Pagination pagination);
}

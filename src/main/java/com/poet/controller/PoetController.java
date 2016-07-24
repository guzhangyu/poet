package com.poet.controller;

import com.poet.po.Poet;
import com.poet.service.IPoetService;
import com.poet.vo.PageList;
import com.poet.vo.Pagination;
import com.poet.vo.PoetCondition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
@Controller
@RequestMapping("poet/")
public class PoetController {

    @Resource
    private IPoetService poetService;

    @RequestMapping("query")
    @ResponseBody
    public List<Poet> query(PoetCondition condition){
        return poetService.query(condition);
    }

    @RequestMapping("queryPageList")
    @ResponseBody
    public PageList<Poet> queryPageList(PoetCondition condition,Pagination page){
        return poetService.queryPageList(condition, page);
    }
}

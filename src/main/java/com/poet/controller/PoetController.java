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

    /**
     * 分页查询
     * @param condition
     * @param page
     * @return
     */
    @RequestMapping("queryPageList")
    @ResponseBody
    public PageList<Poet> queryPageList(PoetCondition condition,Pagination page){
        return poetService.queryPageList(condition, page);
    }

    /**
     * 展示页面
     * @param poet
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Integer save(Poet poet){
        return poetService.insert(poet);
    }

    /**
     * 跳转控制器
     * @param url
     * @return
     */
    @RequestMapping("index")
    public String index(String url){
        //System.out.println(System.getProperty("webapp.root"));
        return url;
    }
}

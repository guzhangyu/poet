package com.poet.dao;

import com.poet.po.Poet;
import com.poet.vo.Pagination;
import com.poet.vo.PoetCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
@Repository
public interface PoetDao {
    int insert(Poet poet);

    List<Poet> query(@Param(value="condition") PoetCondition condition,@Param(value="page")Pagination page);
    int count(@Param(value="condition")PoetCondition condition);


//    List<Poet> query(@Param(value="condition") PoetCondition condition,@Param(value="page")Pagination page);
//    int count(@Param(value="condition")PoetCondition condition);

}

package com.itheima.travel.dao;

import com.itheima.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/21 15:44
 */
public interface RouteDao {
    // 查询总和数据的数量
    Integer findCount(@Param("cid") Integer cid, @Param("rname") String rname);

    // 分页查询线路数据
    List<Route> findList(@Param("cid") Integer cid, @Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("rname") String rname);

    // 四张表嵌套查询线路详情信息
    Route findByRidWithAll(Integer rid);
}

package com.sx.ma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sx.ma.entity.BscTestitem;
import com.sx.ma.vo.TestItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * BscTestitem 数据层
 *
 * @author nhh
 */
@Mapper
public interface BscTestitemMapper extends BaseMapper<BscTestitem> {
    /**
     * 查询所有测试项目
     * @return 测试项目集合
     */
    List<TestItemVo> selectAll();
}

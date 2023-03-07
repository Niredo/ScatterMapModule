package com.sx.ma.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.ma.entity.BscTestitem;
import com.sx.ma.vo.TestItemVo;

import java.util.List;


/**
 * BscTestitem 服务层
 * 测试项目
 *
 * @author nhh
 */
public interface BscTestitemService extends IService<BscTestitem> {
    /**
     * 查询所有测试项目
     * @return 测试项目集合
     */
    List<TestItemVo> selectAll();
}

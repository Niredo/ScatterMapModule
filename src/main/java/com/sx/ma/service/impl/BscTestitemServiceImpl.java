package com.sx.ma.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.ma.entity.BscTestitem;
import com.sx.ma.mapper.BscTestitemMapper;
import com.sx.ma.service.BscTestitemService;
import com.sx.ma.vo.TestItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BscTestitem 服务层实现
 * 测试项目
 *
 * @author nhh
 */
@Service("bscTestitemService")
public class BscTestitemServiceImpl extends ServiceImpl<BscTestitemMapper, BscTestitem> implements BscTestitemService {

    @Autowired
    private BscTestitemMapper bscTestitemMapper;

    @Override
    public List<TestItemVo> selectAll() {
        return bscTestitemMapper.selectAll();
    }
}

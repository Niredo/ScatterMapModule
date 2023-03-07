package com.sx.ma.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.ma.entity.BscLab;
import com.sx.ma.mapper.BscLabMapper;
import com.sx.ma.service.BscLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BscLab 服务层实现
 *
 * @author nhh
 */
@Service("bscLabService")
public class BscLabServiceImpl extends ServiceImpl<BscLabMapper, BscLab> implements BscLabService {

    @Autowired
    private BscLabMapper bscLabMapper;

    @Override
    public String selectLabNameByLabCode(String labCode) {
        return bscLabMapper.selectLabNameByLabCode(labCode);
    }
}

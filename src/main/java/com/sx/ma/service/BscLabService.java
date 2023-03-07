package com.sx.ma.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sx.ma.entity.BscLab;


/**
 * BscLab 服务层
 *
 * @author nhh
 */
public interface BscLabService extends IService<BscLab> {
    String selectLabNameByLabCode(String labCode);
}

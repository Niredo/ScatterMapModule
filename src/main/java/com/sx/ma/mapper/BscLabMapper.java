package com.sx.ma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sx.ma.entity.BscLab;
import org.apache.ibatis.annotations.Mapper;

/**
 * BscLab 数据层
 *
 * @author nhh
 */
@Mapper
public interface BscLabMapper extends BaseMapper<BscLab> {
    /**
     * 医院名称字段转换
     *
     * @param labCode 社区代码
     * @return 字符串
     */
    String selectLabNameByLabCode(String labCode);
}

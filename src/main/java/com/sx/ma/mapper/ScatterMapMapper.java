package com.sx.ma.mapper;

import com.sx.ma.vo.InstrumentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nhh
 */
@Mapper
public interface ScatterMapMapper {
    /**
     * 查询仪器名称集合
     * @param tableName 表名
     * @return 字符串集合
     */
    List<String> selectInstrumentNameList(@Param("tableName") String tableName);
    /**
     * 获取仪器信息
     * @param tableName 表名
     * @param instrumentName 仪器名称
     * @return 仪器
     */
    List<InstrumentVo> selectByInstrumentName(@Param("tableName") String tableName, @Param("instrumentName") String instrumentName);
}

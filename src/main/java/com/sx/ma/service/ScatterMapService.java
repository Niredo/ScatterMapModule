package com.sx.ma.service;

import com.sx.ma.utils.ResponseResult;
import com.sx.ma.vo.InstrumentVo;
import com.sx.ma.vo.RequestVo;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 点状图 服务层实现
 *
 * @author nhh
 */
public interface ScatterMapService {
    /**
     * 查询仪器名称集合
     *
     * @param tableName 表名
     * @return 字符串集合
     */
    List<String> selectInstrumentNameList(String tableName);

    /**
     * 获取仪器信息
     *
     * @param tableName      表名
     * @param instrumentName 仪器名称
     * @return 仪器
     */
    List<InstrumentVo> selectByInstrumentName(@Param("tableName") String tableName, String instrumentName);

    /**
     * 初始化数据并生成点状图
     *
     * @param requestVo 请求体VO
     * @return 统一请求返回体
     */
    ResponseResult map(RequestVo requestVo);

    /**
     * 落点范围
     *
     * @param multiple       倍数
     * @param copies         区间份数
     * @param instrumentList 仪器集合
     * @return HashMap
     */
    HashMap<String, ArrayList<Double>> pointRange(Integer multiple, Integer copies, ArrayList<InstrumentVo> instrumentList);

    /**
     * 箱线图过滤数据
     *
     * @param instrumentList 仪器集合
     * @return 仪器集合
     */
    List<InstrumentVo> filterDataByBoxDiagram(List<InstrumentVo> instrumentList);

    /**
     * 标准差过滤数据
     *
     * @param instrumentList 仪器集合
     * @return 仪器集合
     */
    List<InstrumentVo> filterDataByStandardDeviation(List<InstrumentVo> instrumentList);

    /**
     * 单浓度范围计算
     *
     * @param instrumentNameList             仪器名称集合
     * @param classifiedInstrumentLocalCache 本地分类仪器数据集合
     * @param pointRange                     落点范围
     * @param resultMap                      最终返回结果MAP
     * @param instrumentSize                 仪器数量
     * @return HashMap
     */
    HashMap<String, ArrayList<Double>> singleConcentration(List<String> instrumentNameList, HashMap<String, List<InstrumentVo>> classifiedInstrumentLocalCache, HashMap<String, ArrayList<Double>> pointRange, HashMap<String, Object> resultMap, int instrumentSize);

    /**
     * 多浓度范围计算
     *
     * @param cv                               变异系数
     * @param multiple                         标准差倍数
     * @param unclassifiedInstrumentLocalCache 不分类仪器数据集合
     * @param singleConcentration              单浓度范围
     * @param direction                        方向
     * @param keyValue                         key的值
     * @param nextKeyValue                     下一个key的值
     * @param statistics                       math3工具类
     */
    void multiConcentration(Double cv, Integer multiple, ArrayList<InstrumentVo> unclassifiedInstrumentLocalCache, HashMap<String, ArrayList<Double>> singleConcentration, Boolean direction, String keyValue, String nextKeyValue, DescriptiveStatistics statistics);
}

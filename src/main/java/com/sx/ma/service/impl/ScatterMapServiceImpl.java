package com.sx.ma.service.impl;

import com.sx.ma.enums.AppHttpCodeEnum;
import com.sx.ma.mapper.ScatterMapMapper;
import com.sx.ma.service.ScatterMapService;
import com.sx.ma.utils.Constant;
import com.sx.ma.utils.ConvertUtils;
import com.sx.ma.utils.ResponseResult;
import com.sx.ma.vo.InstrumentVo;
import com.sx.ma.vo.RequestVo;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 点状图 服务层实现
 *
 * @author nhh
 */
@Service
public class ScatterMapServiceImpl implements ScatterMapService {
    @Autowired
    private ScatterMapMapper scatterMapMapper;

    @Override
    public List<String> selectInstrumentNameList(String tableName) {
        return scatterMapMapper.selectInstrumentNameList(tableName);
    }

    @Override
    public List<InstrumentVo> selectByInstrumentName(String tableName, String instrumentName) {
        return scatterMapMapper.selectByInstrumentName(tableName, instrumentName);
    }

    @Override
    public ResponseResult map(RequestVo requestVo) {
        // 返回结果
        HashMap<String, Object> resultMap = new HashMap<>();
        // 仪器名称集合
        List<String> instrumentNameList = requestVo.getInstrumentNameList();
        resultMap.put("instrumentNameList", instrumentNameList);
        // 仪器数量
        Integer instrumentSize = instrumentNameList.size();
        // 项目代码
        String testItemCode = requestVo.getTestItemCode();
        // 变异系数(注意：cv%)
        Double cv = requestVo.getCv();
        cv = (cv / 100);
        resultMap.put("cv", cv);
        // 项目名称
        String projectName = requestVo.getProjectName();
        resultMap.put("projectName", projectName);
        // 拼接成数据库表名
        String tableName = Constant.PREFIX_DATABASE + testItemCode;
        // 范围左右累计数据集
        DescriptiveStatistics rightStatistics = new DescriptiveStatistics();
        DescriptiveStatistics leftStatistics = new DescriptiveStatistics();
        // 本地缓存
        HashMap<String, List<InstrumentVo>> classifiedInstrumentLocalCache = new HashMap<>();
        ArrayList<InstrumentVo> unclassifiedInstrumentLocalCache = new ArrayList<>();
        // 数据初始化
        if (!Objects.isNull(instrumentNameList)) {
            for (String instrumentName : instrumentNameList) {
                // 仪器数据
                ArrayList<List<Double>> instrumentData = new ArrayList<>();
                // 通过仪器名称查询数据集合
                List<InstrumentVo> instrumentVoList1 = scatterMapMapper.selectByInstrumentName(tableName, instrumentName);
                // 数据剔除
                List<InstrumentVo> instrumentVoList = filterDataByBoxDiagram(instrumentVoList1);
                if (instrumentVoList.size() >= 50) {
                    classifiedInstrumentLocalCache.put(instrumentName, instrumentVoList);
                }
                if (!Objects.isNull(instrumentVoList)) {
                    for (InstrumentVo instrumentVo : instrumentVoList) {
                        unclassifiedInstrumentLocalCache.add(instrumentVo);
                        // 添加点状图所需要的数据
                        List<Double> list = new ArrayList<>();
                        // 添加结果和年龄
                        list.add(ConvertUtils.toDouble(instrumentVo.getResult()));
                        // list.add(Double.parseDouble(instrumentVo.getAge()));
                        /* 青浦区检 */
                        list.add(Double.parseDouble(String.valueOf(instrumentVo.getWardId())));
                        instrumentData.add(list);
                    }
                }
                // 去重
                HashSet<List> hashSet = new HashSet<>(instrumentData);
                ArrayList<List> scatterMapData = new ArrayList<>(hashSet);
                resultMap.put(instrumentName, scatterMapData);
            }


            /* 点状图所需数据计算 */

            // 落点范围
            HashMap<String, ArrayList<Double>> pointRange = pointRange(Constant.MULTIPLE, Constant.COPIES, unclassifiedInstrumentLocalCache);
            // 单浓度
            HashMap<String, ArrayList<Double>> concentration = singleConcentration(instrumentNameList, classifiedInstrumentLocalCache, pointRange, resultMap, instrumentSize);

            /**
             * 多浓度范围
             * true -> right
             * false -> left
             */
            multiConcentration(cv, Constant.MULTIPLE, unclassifiedInstrumentLocalCache, concentration, true, Constant.SINGLE_CONCENTRATION, Constant.SECOND_RIGHT_RANGE, rightStatistics);
            multiConcentration(cv, Constant.MULTIPLE, unclassifiedInstrumentLocalCache, concentration, true, Constant.SECOND_RIGHT_RANGE, Constant.THIRD_RIGHT_RANGE, rightStatistics);
            multiConcentration(cv, Constant.MULTIPLE, unclassifiedInstrumentLocalCache, concentration, false, Constant.SINGLE_CONCENTRATION, Constant.SECOND_LEFT_RANGE, leftStatistics);
            multiConcentration(cv, Constant.MULTIPLE, unclassifiedInstrumentLocalCache, concentration, false, Constant.SECOND_LEFT_RANGE, Constant.THIRD_LEFT_RANGE, leftStatistics);

            resultMap.put("concentration", concentration);

            return ResponseResult.okResult(resultMap);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public HashMap<String, ArrayList<Double>> pointRange(Integer multiple, Integer copies, ArrayList<InstrumentVo> instrumentList) {
        // 描述性统计
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        // 落点范围数据
        HashMap<String, ArrayList<Double>> pointScope = new HashMap<>(copies);
        for (InstrumentVo instrumentVo : instrumentList) {
            statistics.addValue(Double.parseDouble(instrumentVo.getResult()));
        }
        // 均值
        double mean = ConvertUtils.toDouble(statistics.getMean());
        // 标准差SD
        double sd = ConvertUtils.toDouble(statistics.getStandardDeviation());
        // 上下限范围
        double upper = ConvertUtils.toDouble(mean + (multiple * sd));
        double lower = ConvertUtils.toDouble(mean - (multiple * sd));
        double once = ConvertUtils.toDouble((upper + lower) / copies);

        // 落点范围
        for (int count = 0; count < copies; count++) {
            ArrayList<Double> tempList = new ArrayList<>();
            tempList.add(ConvertUtils.toDouble(lower));
            tempList.add(ConvertUtils.toDouble(lower + once));
            pointScope.put(String.valueOf(count), tempList);
            lower = ConvertUtils.toDouble(lower + once);
        }
        return pointScope;
    }

    @Override
    public List<InstrumentVo> filterDataByBoxDiagram(List<InstrumentVo> instrumentList) {
        // 1.排序从小到大
        instrumentList.sort(Comparator.comparing(InstrumentVo::getResult));
        double[] resultList = new double[instrumentList.size()];
        for (int i = 0; i < instrumentList.size(); i++) {
            InstrumentVo instrumentVo = instrumentList.get(i);
            Double result = Double.parseDouble(instrumentVo.getResult());
            resultList[i] = result;
        }
        Percentile percentile = new Percentile();
        percentile.setData(resultList);
        double q1 = percentile.evaluate(75);
        double q3 = percentile.evaluate(25);
        double iqr = q1 - q3;
        // 上限
        double upper = q1 + 1.5 * iqr;
        // 下限
        double lower = q3 - 1.5 * iqr;

        for (int i = 0; i < instrumentList.size(); i++) {
            InstrumentVo instrumentVo = instrumentList.get(i);
            Double result = Double.parseDouble(instrumentVo.getResult());
            if (result > upper || result < lower) {
                instrumentList.remove(i--);
            }
        }
        return instrumentList;
    }

    @Override
    public List<InstrumentVo> filterDataByStandardDeviation(List<InstrumentVo> instrumentList) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        for (InstrumentVo instrumentVo : instrumentList) {
            Double result = Double.parseDouble(instrumentVo.getResult());
            statistics.addValue(result);
        }

        // 均值
        double mean = statistics.getMean();
        // 标准差
        double standardDeviation = statistics.getStandardDeviation();
        double upper = mean + (3 * standardDeviation);
        double lower = mean - (3 * standardDeviation);

        for (int i = 0; i < instrumentList.size(); i++) {
            InstrumentVo instrumentVo = instrumentList.get(i);
            Double result = Double.parseDouble(instrumentVo.getResult());
            if (result > upper || result < lower) {
                instrumentList.remove(i--);
            }
        }
        return instrumentList;
    }

    @Override
    public HashMap<String, ArrayList<Double>> singleConcentration(List<String> instrumentNameList, HashMap<String, List<InstrumentVo>> classifiedInstrumentLocalCache, HashMap<String, ArrayList<Double>> pointRange, HashMap<String, Object> resultMap, int instrumentSize) {
        // 所有仪器落点情况统计数据
        HashMap<ArrayList<Double>, ArrayList<Integer>> scopeTotalResult = new HashMap<>();
        // 每一个区域重复数据情况统计
        HashMap<ArrayList<Double>, Integer> scopeRepeatResult = new HashMap<>();
        // 返回结果体
        HashMap<String, ArrayList<Double>> concentration = new HashMap<>();
        //
        HashMap<String, HashMap<ArrayList<Double>, Integer>> show = new HashMap<>();
        for (String instrumentName : instrumentNameList) {
            // 单仪器数据
            List<InstrumentVo> instrumentVoList = classifiedInstrumentLocalCache.get(instrumentName);
            // 区间落点情况
            HashMap<ArrayList<Double>, Integer> intervalCount = new HashMap<>();
            if (!Objects.isNull(instrumentVoList)) {
                // 落点范围判断
                for (ArrayList<Double> interval : pointRange.values()) {
                    // 左端点
                    Double leftPoint = interval.get(0);
                    // 右端点
                    Double rightPoint = interval.get(1);
                    // 计数
                    int count = 0;
                    for (InstrumentVo instrumentVo : instrumentVoList) {
                        double result = ConvertUtils.toDouble(instrumentVo.getResult());
                        if (result >= leftPoint && result < rightPoint) {
                            count++;
                        }
                    }
                    intervalCount.put(interval, count);
                    if (scopeTotalResult.containsKey(interval)) {
                        if (count != 0) {
                            scopeTotalResult.get(interval).add(count);
                        }
                    } else {
                        ArrayList<Integer> list = new ArrayList<>();
                        if (count != 0) {
                            list.add(count);
                            scopeTotalResult.put(interval, list);
                        }
                    }
                }
            }
            show.put(instrumentName, intervalCount);
            // resultMap.put(instrumentName + "_pointCount", intervalCount);
        }

        for (ArrayList<Double> scope : scopeTotalResult.keySet()) {
            ArrayList<Integer> resultCount = scopeTotalResult.get(scope);
            // 该范围中仪器数量必须满足，即有可比性
            if (instrumentSize == resultCount.size()) {
                Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
                // 计算每一个区域里面最小的数，即重复的数据量
                Optional<Integer> minResult = resultCount.stream().min(comparator);
                scopeRepeatResult.put(scope, minResult.get());
            }
        }

        Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
        // 计算所有区域里面最大的值，即为全部仪器重复的数据范围和数据量
        Optional<Integer> maxResult = scopeRepeatResult.values().stream().max(comparator);

        String publicScope = "";
        for (ArrayList<Double> scope : scopeRepeatResult.keySet()) {
            if (maxResult.get().equals(scopeRepeatResult.get(scope))) {
                publicScope = String.valueOf(scope);
                concentration.put(Constant.SINGLE_CONCENTRATION, scope);
            }
        }
        HashMap<String, Integer> showPublicScopeAndData = new HashMap<>();
        showPublicScopeAndData.put(publicScope, maxResult.get());
        resultMap.put("publicScope", showPublicScopeAndData);

        for (String instrument : show.keySet()) {
            HashMap<ArrayList<Double>, Integer> interval = show.get(instrument);
            for (ArrayList<Double> scope : interval.keySet()) {
                if (publicScope.equals(String.valueOf(scope))) {
                    resultMap.put(instrument + "_count", interval.get(scope));
                }
            }
        }

        return concentration;
    }

    @Override
    public void multiConcentration(Double cv, Integer multiple, ArrayList<InstrumentVo> unclassifiedInstrumentLocalCache, HashMap<String, ArrayList<Double>> concentration, Boolean direction, String keyValue, String nextKeyValue, DescriptiveStatistics statistics) {

        ArrayList<Double> scope = concentration.get(keyValue);
        ArrayList<Double> list = new ArrayList<>();
        // 左端点
        Double leftPoint = scope.get(0);
        // 右端点
        Double rightPoint = scope.get(1);

        for (InstrumentVo instrumentVo : unclassifiedInstrumentLocalCache) {
            double result = ConvertUtils.toDouble(instrumentVo.getResult());
            if (result >= leftPoint && result < rightPoint) {
                statistics.addValue(result);
            }
        }
        // 均值
        double mean = ConvertUtils.toDouble(statistics.getMean());
        // 标准差SD
        double sd = cv * mean;
        if (direction) {
            // 右区域
            list.add(rightPoint);
            list.add(ConvertUtils.toDouble(rightPoint + multiple * sd));
            concentration.put(nextKeyValue, list);
        } else {
            // 左区域
            list.add(ConvertUtils.toDouble(leftPoint - multiple * sd));
            list.add(leftPoint);
            concentration.put(nextKeyValue, list);
        }
    }
}

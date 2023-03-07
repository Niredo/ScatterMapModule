package com.sx.ma.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求体VO
 *
 * @author nhh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVo {
    /**
     * 测试项目代码
     */
    private String testItemCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 仪器名称集合
     */
    private List<String> instrumentNameList;
    /**
     * 变异系数
     */
    private Double cv;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 选取范围的最大值
     */
    private String upperResult;
    /**
     * 选取范围的最小值
     */
    private String lowerResult;
}


package com.sx.ma.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试项目VO
 *
 * @author nhh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestItemVo {
    /**
     * 测试项目代码
     */
    private String testItemCode;
    /**
     * 英文名称
     */
    private String englishName;
    /**
     * 测试项目名称
     */
    private String testItemName;
    /**
     * 变异系数
     */
    private Double cv;
    /**
     * 样品类型
     */
    private String sample;
}

package com.sx.ma.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪器VO
 *
 * @author nhh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentVo {
    /**
     * 仪器名称
     */
    private String instrumentName;
    /**
     * 仪器结果
     */
    private String result;
    /**
     * 仪器结果日期
     */
    private String resultDate;
    /**
     * 患者年龄
     */
    private String age;
    /**
     * 患者性别
     */
    private String sex;
}

package com.sx.ma.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Qualitytargetsetingmodel 实体类
 * 卫生部室间质评评价标准
 *
 * @author nhh
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qualitytargetsetingmodel")
public class Qualitytargetsetingmodel  {
    @TableId
    private Integer quelitytagerSettingId;
    private String basis;
    private String bias;
    private String createTime;
    private String createUserId;
    private String cv;
    private String instrumentCode;
    private String interindividualVariation;
    private String linearRange;
    private String modifyTime;
    private String modifyUserId;
    private String normalRange;
    private String remark;
    private String setingDate;
    private String setingUser;
    private String specialityClassifyid;
    private Object tea;
    private String testItemCode;
    private String testItemName;
    private String sample;
    private String weight;
}

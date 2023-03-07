package com.sx.ma.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * BscTestitem 实体类
 * TestItemCode 字典
 *
 * @author nhh
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsc_testitem")
public class BscTestitem  {
    @TableId
    private Integer testitemid;
    private Integer instrumentgroupid;
    private String testitemcode;
    private String englishname;
    private String testitemname;
    private String checkmethod;
    private Integer printorder;
    private Integer testtypeid;
    private Integer resulttypeid;
    private Integer resultenumid;
    private String calformulaid;
    private Integer iskeepsecret;
    private Integer printtype;
    private String costfee;
    private String testfee;
    private Integer isaccountexpense;
    private String reportcode;
    private Integer sampleconcerned;
    private Integer sexconcerned;
    private Integer ageconcerned;
    private String cliniccomment;
    private Integer isordac;
    private String testitemuniformcode;
    private Integer disableflag;
    private Integer obsoleteflag;
    private String memorysymbol;
    private String comment;
    private Integer microbeproperty;
    private String exchangecode;
    private String grass;
    private Integer microberesultflag;
    private String convertformulaid;
    private String defaultvalue;
    private String wnOrg;
    private String wnOrgType;
    private Integer parenttestitemid;
    private Integer medgroupid;
    private String textdigestclass;
    private String oldliscode;
    private String microbecommonclassify;
    private String histestitemcode3;
    private String histestitemcode1;
    private String histestitemcode2;
    private Integer micitemclass;
    private Integer micresultkind;
    private String convertprogramformulaid;
    private String feeexchangecode;
    private Integer aspect;
    private String fenbeiliang;
    private Integer specialityclassifyid;
    private Integer wardconcerned;
}

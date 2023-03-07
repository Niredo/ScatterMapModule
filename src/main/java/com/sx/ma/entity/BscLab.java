package com.sx.ma.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * BscLab 实体类
 *
 * @author nhh
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsc_lab")
public class BscLab  {
    @TableId
    private String id;
    private String labCode;
    private String labName;
}

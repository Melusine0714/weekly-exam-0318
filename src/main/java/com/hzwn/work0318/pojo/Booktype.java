package com.hzwn.work0318.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_booktype
 */
@TableName(value ="t_booktype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booktype implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "createtime")
    private Date createTime;

    @TableField(value = "updatetime")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
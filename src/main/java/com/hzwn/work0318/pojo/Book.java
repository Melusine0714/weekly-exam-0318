package com.hzwn.work0318.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName t_book
 */
@TableName(value ="t_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "typeId")
    private Integer typeid;

    @TableField(value = "provider")
    private String provider;

    @TableField(value = "author")
    private String author;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "detail")
    private String detail;

    @TableField(value = "imgsrc")
    private String imgSrc;

    @TableField(value = "collectioncount")
    private Integer collectionCount;

    @TableField(value = "storecount")
    private Integer storeCount;

    @TableField(value = "buycount")
    private Integer buyCount;

    @TableField(value = "readcount")
    private Integer readCount;

    @TableField(value = "createtime")
    private Date createTime;

    @TableField(value = "updatetime")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
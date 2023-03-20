package com.hzwn.work0318.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzwn.work0318.pojo.Booktype;

import java.util.List;

public interface BooktypeService extends IService<Booktype> {
    List<Booktype> selectAll();
}

package com.hzwn.work0318.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzwn.work0318.pojo.Book;

import java.util.List;

public interface BookService extends IService<Book>  {
    Page<Book> selectAll(Integer current, Integer size);
}

package com.hzwn.work0318.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzwn.work0318.pojo.Book;
import com.hzwn.work0318.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;

    @RequestMapping("/selectAll")
    public Page<Book> selectAll(Integer current, Integer size){
        Page<Book> bookPage = bookService.selectAll(current,size);
        return bookPage;
    }
}

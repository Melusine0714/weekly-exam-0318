package com.hzwn.work0318.controller;


import com.hzwn.work0318.pojo.Booktype;
import com.hzwn.work0318.service.BooktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booktype")
public class BooktypeController {

    @Autowired
    private BooktypeService booktypeService;

    @Autowired
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @RequestMapping("/selectAll")
    public List<Booktype> selectAll(){
        return booktypeService.selectAll();
    }
    @RequestMapping("delete")
    public boolean delete(Integer bookTypeId){
        return booktypeService.removeById(bookTypeId);
    }
    @RequestMapping("add")
    public boolean add(Booktype booktype){
        return booktypeService.save(booktype);
    }
    @RequestMapping("update")
    public boolean update(Booktype booktype){
        return booktypeService.updateById(booktype);
    }

}


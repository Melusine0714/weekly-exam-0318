package com.hzwn.work0318.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzwn.work0318.mapper.BookMapper;
import com.hzwn.work0318.mapper.BooktypeMapper;
import com.hzwn.work0318.pojo.Booktype;
import com.hzwn.work0318.service.BooktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class BooktypeServiceImpl extends ServiceImpl<BooktypeMapper, Booktype>
        implements BooktypeService {
    @Resource
    private BooktypeMapper booktypeMapper;
    @Resource
    RedisTemplate<String, Object> stringObjectRedisTemplate;

    public List<Booktype> selectAll() {
        ValueOperations<String, Object> opsForValue = stringObjectRedisTemplate.opsForValue();
        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();

        List<Booktype> bookTypeValue = (List<Booktype>) opsForValue.get("bookTypeValue");
        if (bookTypeValue == null) {
            //1.如果緩存為空,就去mysql裡查找
            bookTypeValue = booktypeMapper.selectList(null);
            //用uuid生成唯一key
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String redisKey = "bookType:" + uuid;
            //記錄下本頁的key ,方便以後刪除修改
            opsForSet.add("cachedTypeKey", redisKey);
            opsForValue.set("bookTypeValue", bookTypeValue);
            System.out.println("图书数据已放入缓存");
        } else {
            System.out.println("图书数据来自缓存");
        }
        return bookTypeValue;
    }
}





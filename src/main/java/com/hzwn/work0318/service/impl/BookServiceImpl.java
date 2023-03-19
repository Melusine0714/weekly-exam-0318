package com.hzwn.work0318.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzwn.work0318.mapper.BookMapper;
import com.hzwn.work0318.pojo.Book;
import com.hzwn.work0318.service.BookService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author HP
* @description 针对表【t_book】的数据库操作Service实现
* @createDate 2023-03-18 11:10:43
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService {
    @Resource
    RedisTemplate<String, Object> stringObjectRedisTemplate;
    @Resource
    private BookMapper bookMapper;
    @Override
    public Page<Book> selectAll(Integer pageNum, Integer pageSize) {
        //将当前页的页码信息储存在redis中
        String redisKey = "pageNum:"+ pageNum +"pageSize"+ pageSize;

        ValueOperations<String, Object> value = stringObjectRedisTemplate.opsForValue();
        SetOperations<String, Object> set = stringObjectRedisTemplate.opsForSet();

        Page<Book> bookPage = (Page<Book>) value.get(redisKey);
        if (bookPage == null){
            //缓存为空则查询
            Page<Book> redisBookPage = new Page<>(pageNum, pageSize);
            QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
            //根据buycount倒序
            queryWrapper.orderByDesc("buycount");
            bookPage = bookMapper.selectPage(redisBookPage,queryWrapper);
            value.set(redisKey,bookPage);
            //记录下缓存本页数据时使用的key
            set.add("cachedKey", redisKey);
            System.out.println("圖書信息數據已放入緩存");
        }else {
            //不为空则取缓存
            System.out.println("圖書信息數據來自緩存");
        }
        return bookPage;
    }
}






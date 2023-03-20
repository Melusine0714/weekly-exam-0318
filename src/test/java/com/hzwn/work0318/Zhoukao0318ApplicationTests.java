package com.hzwn.work0318;

import com.hzwn.work0318.controller.BooktypeController;
import com.hzwn.work0318.mapper.BooktypeMapper;
import com.hzwn.work0318.pojo.Booktype;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class Zhoukao0318ApplicationTests {

    @Resource
    private BooktypeMapper booktypeMapper;

    @Test
    void contextLoads() {
        booktypeMapper.selectList(null);
    }
}

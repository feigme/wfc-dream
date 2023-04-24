package com.wfc.starter.dal.demo.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wfc.starter.dal.demo.entity.DemoUserDO;
import com.wfc.starter.mybatis.autoconfigure.DataSourceConfig;
import com.wfc.starter.mybatis.autoconfigure.MybatisPlusConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 飞影
 * @create by 2019-08-30 10:33
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = { MybatisPlusConfig.class, DataSourceConfig.class })
@Transactional
public class DemoUserMapperTest {

    @Autowired
    private DemoUserMapper demoUserMapper;

    @Test
    public void testGet() {
        System.out.println("----- selectAll method test ------");
        List<DemoUserDO> userList = demoUserMapper.selectList(null);
        Assert.assertEquals(10, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        DemoUserDO user = new DemoUserDO();
        user.setName("test-a");
        user.setAge(18);
        user.setEmail("testsa@wfc.com");
        int n = demoUserMapper.insert(user);
        Assert.assertEquals(1, n);
        Assert.assertNotNull(user.getId());
        System.out.println("id_worker: " + user.getId());
    }

    @Test
    public void testUpdate() {
        DemoUserDO user = new DemoUserDO();
        user.setId(1L);
        user.setName("bill1");
        int n = demoUserMapper.updateById(user);
        Assert.assertEquals(1, n);
        Assert.assertEquals("bill1", demoUserMapper.selectById(1L).getName());
    }

    @Test
    public void testDelete() {
        int n = demoUserMapper.deleteById(1L);
        Assert.assertEquals(1, n);
        Assert.assertNull(demoUserMapper.selectById(1L));
    }

    @Test
    public void testSelectByPage() {
        Page<DemoUserDO> page = new Page<>(2, 3);
        IPage<DemoUserDO> list = demoUserMapper.selectPage(page, null);
        Assert.assertEquals(3, list.getSize());
        Assert.assertEquals("Sandy", list.getRecords().get(0).getName());
    }

    @Test
    public void testXmlMapper() {
        DemoUserDO user = demoUserMapper.getUserByName("Tom");
        Assert.assertNotNull(user);
        Assert.assertEquals(Long.valueOf(3), user.getId());
    }

    @Test
    public void testXmlSelectByPage() {
        Page<DemoUserDO> page = new Page<>(2, 3);
        IPage<DemoUserDO> list = demoUserMapper.selectLike(page, "Bill");
        Assert.assertEquals(3, list.getRecords().size());
        Assert.assertEquals("Bill8", list.getRecords().get(0).getName());
    }
}

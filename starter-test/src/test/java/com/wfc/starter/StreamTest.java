package com.wfc.starter;

import com.wfc.starter.dal.demo.entity.DemoUserDO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author hui.guo
 * @since 2022/6/14 12:05 上午
 */
public class StreamTest {

    @Test
    public void testSort(){
        List<DemoUserDO> list = new ArrayList<>();
        list.add(new DemoUserDO());
        list.add(new DemoUserDO());
        list.add(new DemoUserDO());

        DemoUserDO demoUserDO = list.stream().filter(p -> p.getAge() != null).max(Comparator.comparing(DemoUserDO::getId)).orElse(null);
        Assert.assertNull(demoUserDO);
    }

}

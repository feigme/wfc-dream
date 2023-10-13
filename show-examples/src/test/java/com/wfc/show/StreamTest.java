package com.wfc.show;

import com.wfc.show.dal.demo.entity.DemoUserDO;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author hui.guo
 * @since 2022/6/14 12:05 上午
 */
public class StreamTest {

    @Test
    public void testSort() {
        List<DemoUserDO> list = new ArrayList<>();
        list.add(new DemoUserDO());
        list.add(new DemoUserDO());
        list.add(new DemoUserDO());

        DemoUserDO demoUserDO = list.stream().filter(p -> p.getAge() != null).max(Comparator.comparing(DemoUserDO::getId)).orElse(null);
        Assert.assertNull(demoUserDO);
    }

    @Test
    public void testDate() {
        Date deadlineTime = new DateTime().plusHours(4320).toDate();
        System.out.println(DateFormatUtils.format(deadlineTime, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void testReplace() {
        String source = "insert overwrite table ytdw.dw_fw_bgmv_data_d partition (dayid='20220907')";
        source = RegExUtils.replacePattern(source, "\\s+\\(\\s*dayid\\s*=\\s*'20220907'\\s*\\)", " (dayid = '\\$\\{v_date\\}') ");
        System.out.println(source);
    }
}

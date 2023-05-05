package com.wfc.common.http.okhttp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author hui.guo
 * @since 2023/5/5 2:35 下午
 */
public class SimpleOkHttpReqTest {

    @Test
    public void test_get_返回String() {
        String rspStr = SimpleOkHttpReq.build().get("https://api.github.com/versions").execute();
        Assert.assertNotNull(rspStr);
        Assert.assertTrue(rspStr.length() > 0);
    }

    @Test
    public void test_get_返回Json() {
        JSONObject jsonObject = SimpleOkHttpReq.build().get("https://api.github.com/versions").execute(x -> JSON.parseObject(x));
        Assert.assertNotNull(jsonObject);
        Assert.assertEquals("https://docs.github.com/rest/overview/resources-in-the-rest-api#rate-limiting", jsonObject.getString("documentation_url"));
    }

    @Test
    public void test_get() {
        SimpleOkHttpReq.build().get("https://api.github.com/repos/spring-projects/spring-framework/releases/latest");
    }

}

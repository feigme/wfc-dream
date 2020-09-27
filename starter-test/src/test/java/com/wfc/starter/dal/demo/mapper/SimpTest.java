package com.wfc.starter.dal.demo.mapper;

import org.junit.Test;

/**
 * @author 飞影
 * @create by 2020-09-23 15:55
 */
public class SimpTest {

    @Test
    public void test1(){
        String temp = "史可法%s  -  %s";
        System.out.println(String.format(temp, null, "aaa"));
    }

}

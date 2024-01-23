package com.wfc.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hui.guo
 * @since 2024/1/22 9:56 上午
 */
public class ThreadLocalTest {

    @Test
    public void test_子线程继承父线程ThreadLocal_1() throws InterruptedException {
        ThreadLocal<Integer> threadLocal_1 = new ThreadLocal<>();
        threadLocal_1.set(1);

        ThreadLocal<Map<String, String>> threadLocal_2 = new ThreadLocal<>();
        Map<String, String> map = new HashMap();
        map.put("a", "1");
        threadLocal_2.set(map);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        threadPoolExecutor.execute(() -> {
            // 子线程并不能拿到父线程的ThreadLocal
            Assertions.assertNull(threadLocal_1.get());

            // 子线程并不能拿到父线程的ThreadLocal
            Assertions.assertNull(threadLocal_2.get());
        });
        Thread.sleep(1000);
    }

    @Test
    public void test_子线程继承父线程ThreadLocal_2() throws InterruptedException {
        // InheritableThreadLocal 是创建线程时复制
        ThreadLocal<Integer> inheritableThreadLocal_1 = new InheritableThreadLocal<>();
        inheritableThreadLocal_1.set(1);

        ThreadLocal<Map<String, String>> inheritableThreadLocal_2 = new InheritableThreadLocal<>();
        Map<String, String> map = new HashMap();
        map.put("a", "1");
        inheritableThreadLocal_2.set(map);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        threadPoolExecutor.execute(() -> {
            // 子线程能拿到父线程的InheritableThreadLocal
            Assertions.assertEquals(1, inheritableThreadLocal_1.get());

            // 子线程能拿到父线程的InheritableThreadLocal
            Assertions.assertNotNull(inheritableThreadLocal_2.get());
            Assertions.assertEquals("1", inheritableThreadLocal_2.get().get("a"));
        });
        Thread.sleep(1000);
    }

    @Test
    public void test_子线程继承父线程ThreadLocal_3() throws InterruptedException {
        // InheritableThreadLocal 是创建线程时复制
        ThreadLocal<Integer> inheritableThreadLocal_1 = new InheritableThreadLocal<>();
        inheritableThreadLocal_1.set(1);

        ThreadLocal<Map<String, String>> inheritableThreadLocal_2 = new InheritableThreadLocal<>();
        Map<String, String> map = new HashMap();
        map.put("a", "1");
        inheritableThreadLocal_2.set(map);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        threadPoolExecutor.execute(() -> {
            // 子线程修改InheritableThreadLocal
            inheritableThreadLocal_1.set(2);
            inheritableThreadLocal_2.get().put("b", "2");
        });
        Thread.sleep(1000);

        // 父线程拿不到子线程修改的值
        Assertions.assertEquals(1, inheritableThreadLocal_1.get());
        // 子线程修改的对象类型，父线程能照样获取
        Assertions.assertEquals("1", inheritableThreadLocal_2.get().get("a"));
        Assertions.assertEquals("2", inheritableThreadLocal_2.get().get("b"));
    }

    @Test
    public void test_子线程继承父线程ThreadLocal_4() throws InterruptedException {
        // InheritableThreadLocal 是创建线程时复制进去的
        ThreadLocal<Integer> inheritableThreadLocal_1 = new InheritableThreadLocal<>();
        inheritableThreadLocal_1.set(1);

        ThreadLocal<Map<String, String>> inheritableThreadLocal_2 = new InheritableThreadLocal<>();
        Map<String, String> map = new HashMap();
        map.put("a", "1");
        inheritableThreadLocal_2.set(map);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        threadPoolExecutor.execute(() -> {
            // 子线程修改InheritableThreadLocal
            inheritableThreadLocal_1.set(2);
            inheritableThreadLocal_2.get().put("b", "2");
        });
        Thread.sleep(1000);

        // 父线程修改了值
        inheritableThreadLocal_1.set(3);
        inheritableThreadLocal_2.get().put("c", "3");

        threadPoolExecutor.execute(() -> {
            // 子线程是复用的，拿不到父线程后来修改的值
            Assertions.assertEquals(2, inheritableThreadLocal_1.get());
            // 子线程修改的对象类型，父线程能照样获取
            Assertions.assertEquals("1", inheritableThreadLocal_2.get().get("a"));
            Assertions.assertEquals("2", inheritableThreadLocal_2.get().get("b"));
            Assertions.assertEquals("3", inheritableThreadLocal_2.get().get("c"));
        });
        Thread.sleep(1000);

    }
}

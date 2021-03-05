package com.lcl.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class EurekaClientApplicationTests {

    @Test
    void contextLoads() {
        boolean flag = true;
        Integer i = 0;
        int j = 1;
        Integer o = null;
        System.out.println("o:"+o);
        int k = flag ? i*o : j;
        System.out.println(k);
    }

    @Test
    void test1() {
        Integer i = Integer.valueOf(null);
        System.out.println(i);
        System.out.println(i.intValue());
    }

}

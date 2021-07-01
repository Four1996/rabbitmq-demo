package com.ph.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-07-01 15:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-consumer.xml")
public class ConsumerTest {
    @Test
    public void test(){
        while (true){

        }
    }
}

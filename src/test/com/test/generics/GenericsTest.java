package com.test.generics;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GenericsTest{

    /**
     * 测试泛型类型擦除
     */
    @Test
    public void testGenericsRemove() {
        List stringList = new ArrayList<String>();
        List integerList = new ArrayList<Integer>();
        Class stringListClass = stringList.getClass();
        Class integerListClass = integerList.getClass();
        Assert.assertTrue(stringListClass.equals(integerListClass));
    }
}

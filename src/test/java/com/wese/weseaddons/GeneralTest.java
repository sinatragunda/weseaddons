/*

    Created by Sinatra Gunda
    At 1:03 PM on 6/30/2021

*/
package com.wese.weseaddons;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class GeneralTest {




    @Test
    public void listTest(){

        BigDecimal bigDecimal = new BigDecimal(9.09444445554444);

        System.err.println(bigDecimal.doubleValue());


        System.err.println(bigDecimal.setScale(2 ,BigDecimal.ROUND_DOWN).doubleValue());

        System.err.println(bigDecimal.setScale(3 ,BigDecimal.ROUND_DOWN).doubleValue());

        System.err.println(bigDecimal.setScale(2 ,BigDecimal.ROUND_FLOOR).doubleValue());

        System.err.println(bigDecimal.setScale(1 ,BigDecimal.ROUND_DOWN).doubleValue());

    }


}

package com.wese.weseaddons.helper;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Random;

public class ColorGenerator {

    public static String generateColorCode(){

        final String hex[] = { "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "a", "b", "c", "d", "e", "f" };
        
        String[] s = new String[7];

        Random random = new Random(0x1000000);
        int     n = random.nextInt();

        s[0] = "#";
        
        String[] hexData = new String[7];

        for (int i=1;i<7;i++) {
            s[i] = hex[n & 0xf];
            
            n >>= 4;

            hexData[i] = hex[n & 0xf];
        
        }
        
        int index = s.toString().lastIndexOf("@") +1 ;

        return String.format("#%s", s.toString().substring(index));

    }
}

package ru.zont.topbuilder.core;

import java.util.Random;

public class Tools {
    public static String genNewStr(Random r) {
        StringBuilder sb = new StringBuilder();
        int a = 'a';
        int z = 'z';
        for (int i = 0; i < 6 + r.nextInt(10); i++)
            sb.append((char) (a + r.nextInt(z - a)));
        return sb.toString();
    }
}

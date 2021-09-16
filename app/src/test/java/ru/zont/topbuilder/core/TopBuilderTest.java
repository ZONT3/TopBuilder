package ru.zont.topbuilder.core;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TopBuilderTest extends TestCase {

    // TODO move EloTop to BackTrackable base, test it

    public void testStrGen() {
        final Random r = new Random(123);
        final String str = Tools.genNewStr(r);
        assertTrue(str.matches("[a-z]{6,16}"));
    }

    public void testStrGenStress() {
        final Random r = new Random(System.currentTimeMillis());
        String str;
        for (int i = 0; i < 10000; i++) {
            str = Tools.genNewStr(r);
            assertTrue("Invalid: " + str, str.matches("[a-z]{6,16}"));
        }
    }
}
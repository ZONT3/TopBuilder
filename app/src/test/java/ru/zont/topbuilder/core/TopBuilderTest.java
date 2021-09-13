package ru.zont.topbuilder.core;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TopBuilderTest extends TestCase {
    public void testSort() {
        ArrayList<Integer> integers = new ArrayList<>(15);
        for (int i = 14; i >= 0; i--) integers.add(i);
        testReturning(integers, (lhs, rhs) -> rhs - lhs);
    }

    public void testSortByOne() {
        ArrayList<Integer> integers = new ArrayList<>(15);
        for (int i = 14; i >= 0; i--) integers.add(i);
        testReturning(integers, (lhs, rhs) -> (int) Math.signum(rhs - lhs));
    }

    public void testSortWithDuplicates() {
        ArrayList<Integer> integers = new ArrayList<>(15);
        for (int i = 14; i >= 0; i--) integers.add(i - (i % 2));
        testReturning(integers, (lhs, rhs) -> (int) Math.signum(rhs - lhs));
    }

    private void testReturning(ArrayList<Integer> integers, TopBuilder.Comparator<Integer> comparator) {
        ArrayList<Integer> rand = new ArrayList<>(integers);
        Collections.shuffle(rand, new Random(123));

        WeightTop<Integer> top = new WeightTop<>(rand);
        while (top.hasNext()) {
            top.next(comparator);
        }

        List<Integer> results = top.getResults();
        assertEquals(integers, results);
    }
}
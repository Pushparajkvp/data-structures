package dev.pushparaj;

import org.junit.Test;

public class SegmentTreeWithArrayTest {

    @Test
    public void initialTest(){
        long[] testInput = genRandArray(5);
        CompactSegmentTree tree = new CompactSegmentTree(testInput);
        System.out.println("test");
    }

    static long[] genRandArray(int sz) {
        long[] lst = new long[sz];
        for (int i = 0; i < sz; i++) lst[i] = (long) (Math.random() * 100);
        return lst;
    }
}

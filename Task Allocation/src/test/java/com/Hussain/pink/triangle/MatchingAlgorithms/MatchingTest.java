package com.Hussain.pink.triangle.MatchingAlgorithms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatchingTest {

    @Test
    public void testAddMatching() {
        String expected = "(e1:t1)\n".trim();
        Matching<String> matching = new Matching<>();
        matching.addMatching("e1","t1");
        assertEquals(expected,matching.toString());
    }

    @Test
    public void testRemoveMatching() {
        String expected1 = "(e1:t1)\n(e2:t2)".trim();
        String expected2 = "(e1:t1)\n".trim();
        Matching<String> matching = new Matching<>();
        matching.addMatching("e1","t1");
        matching.addMatching("e2","t2");

        assertEquals(expected1,matching.toString());

        matching.removeMatching("e2");

        assertEquals(expected2,matching.toString());
    }

}
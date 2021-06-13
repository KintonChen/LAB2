/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import P1.graph.*;
import static org.junit.Assert.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 *
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {

    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices", Collections.emptySet(), Graph.empty().vertices());
    }

    // TODO test other vertex label types in Problem 3.2
    @Test
    public void testLType() {
        Graph<Integer> emptyInstance = Graph.empty();
        assertTrue(emptyInstance.add(10));
        assertTrue(emptyInstance.add(20));
        assertTrue(emptyInstance.add(30));
        assertEquals(0, emptyInstance.set(20, 10, 4));
        assertEquals(0, emptyInstance.set(20, 30, 5));

        Map<Integer, Integer> targets = new HashMap<>();
        targets.put(10, 4);
        targets.put(30, 5);
        assertEquals(targets, emptyInstance.targets(20));
        /* this case is to test if the source vertex has no out coming Edges */
        assertTrue("expect empty map", emptyInstance.targets(30).isEmpty());
    }

}
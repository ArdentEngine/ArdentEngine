package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRect2i {

    @Test
    public void testPosition() {
        var rect = new Rect2i(1, 2, 3, 4);
        var res = new Vector2i(1, 2);
        Assertions.assertEquals(res, rect.position());
    }

    @Test
    public void testSize() {
        var rect = new Rect2i(1, 2, 3, 4);
        var res = new Vector2i(3, 4);
        Assertions.assertEquals(res, rect.size());
    }

    @Test
    public void testEnd() {
        var rect1 = new Rect2i(1, 2, 3, 4);
        var res1 = new Vector2i(4, 6);
        Assertions.assertEquals(res1, rect1.end());
        var rect2 = new Rect2i(1, 2, -3, -4);
        var res2 = new Vector2i(-2, -2);
        Assertions.assertEquals(res2, rect2.end());
    }

    @Test
    public void testCenter() {
        var rect = new Rect2i(1, 2, 3, 4);
        var res = new Vector2i(2, 4);
        Assertions.assertEquals(res, rect.center());
    }

    @Test
    public void testArea() {
        var rect = new Rect2i(0, 0, 4, 3);
        Assertions.assertEquals(12, rect.area());
    }

    @Test
    public void testExtents() {
        var rect = new Rect2i(2, 1, 4, 3);
        Assertions.assertEquals(2, rect.left());
        Assertions.assertEquals(6, rect.right());
        Assertions.assertEquals(4, rect.top());
        Assertions.assertEquals(1, rect.bottom());
    }

    @Test
    public void testExtentsNegativeSize() {
        var rect = new Rect2i(6, 4, -4, -3);
        Assertions.assertEquals(2, rect.left());
        Assertions.assertEquals(6, rect.right());
        Assertions.assertEquals(4, rect.top());
        Assertions.assertEquals(1, rect.bottom());
    }

    @Test
    public void testCorners() {
        var rect = new Rect2i(2, 1, 4, 3);
        var bottomLeft = new Vector2i(2, 1);
        var topLeft = new Vector2i(2, 4);
        var bottomRight = new Vector2i(6, 1);
        var topRight = new Vector2i(6, 4);
        Assertions.assertEquals(bottomLeft, rect.bottomLeft());
        Assertions.assertEquals(topLeft, rect.topLeft());
        Assertions.assertEquals(bottomRight, rect.bottomRight());
        Assertions.assertEquals(topRight, rect.topRight());
    }

    @Test
    public void testCornersNegativeSize() {
        var rect = new Rect2i(6, 4, -4, -3);
        var bottomLeft = new Vector2i(2, 1);
        var topLeft = new Vector2i(2, 4);
        var bottomRight = new Vector2i(6, 1);
        var topRight = new Vector2i(6, 4);
        Assertions.assertEquals(bottomLeft, rect.bottomLeft());
        Assertions.assertEquals(topLeft, rect.topLeft());
        Assertions.assertEquals(bottomRight, rect.bottomRight());
        Assertions.assertEquals(topRight, rect.topRight());
    }

    @Test
    public void testContainsPoint() {
        var rect = new Rect2i(2, 1, 4, 3);
        var p1 = new Vector2i(3, 2);
        var p2 = new Vector2i(-1, -1);
        var p3 = new Vector2i(2, 1);
        Assertions.assertTrue(rect.containsPoint(p1));
        Assertions.assertFalse(rect.containsPoint(p2));
        Assertions.assertFalse(rect.containsPoint(p3));
        Assertions.assertTrue(rect.containsPoint(p3, true));
    }

    @Test
    public void testContainsPointNegativeSize() {
        var rect = new Rect2i(6, 4, -4, -3);
        var p1 = new Vector2i(3, 2);
        var p2 = new Vector2i(-1, -1);
        var p3 = new Vector2i(2, 1);
        Assertions.assertTrue(rect.containsPoint(p1));
        Assertions.assertFalse(rect.containsPoint(p2));
        Assertions.assertFalse(rect.containsPoint(p3));
        Assertions.assertTrue(rect.containsPoint(p3, true));
    }

    @Test
    public void testIntersects() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2i(3, 2, 4, 3);
        Assertions.assertTrue(rect1.intersects(rect2));
        var rect3 = new Rect2i(8, 1, 3, 2);
        Assertions.assertFalse(rect1.intersects(rect3));
        var rect4 = new Rect2i(6, 2, 4, 3);
        Assertions.assertFalse(rect1.intersects(rect4));
        Assertions.assertTrue(rect1.intersects(rect4, true));
        var rect5 = new Rect2i(7, 5, -4, -3);
        Assertions.assertTrue(rect1.intersects(rect5));
    }

    @Test
    public void testFromPoints() {
        var p1 = new Vector2i(2, 1);
        var p2 = new Vector2i(6, 4);
        var rect1 = new Rect2i(2, 1, 4, 3);
        Assertions.assertEquals(rect1, Rect2i.fromPoints(p1, p2));
        var p3 = new Vector2i(0, 5);
        var rect2 = new Rect2i(0, 1, 2, 4);
        Assertions.assertEquals(rect2, Rect2i.fromPoints(p1, p3));
    }

    @Test
    public void testIntersection() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2i(3, 2, 4, 3);
        var res = new Rect2i(3, 2, 3, 2);
        Assertions.assertEquals(res, rect1.intersection(rect2));
        var rect3 = new Rect2i(7, 5, -4, -3);
        Assertions.assertEquals(res, rect1.intersection(rect3));
        var rect4 = new Rect2i(1, 1, 2, 2);
        var rect5 = new Rect2i(-1, -1, -2, -2);
        Assertions.assertEquals(new Rect2i(), rect4.intersection(rect5));
    }

    @Test
    public void testAbs() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2i(6, 4, -4, -3);
        Assertions.assertEquals(rect1, rect2.abs());
    }

    @Test
    public void testEncloses() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2i(3, 2, 4, 3);
        Assertions.assertFalse(rect1.encloses(rect2));
        var rect3 = new Rect2i(2, 1, 3, 2);
        Assertions.assertTrue(rect1.encloses(rect3));
        var rect4 = new Rect2i(6, 4, -3, -2);
        Assertions.assertTrue(rect1.encloses(rect4));
    }

    @Test
    public void testGrow() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var res = new Rect2i(1, 2, 6, 4);
        Assertions.assertEquals(res, rect1.grow(1, 2, 1, -1));
    }

    @Test
    public void testGrowAllSides() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var res1 = new Rect2i(0, -1, 8, 7);
        Assertions.assertEquals(res1, rect1.grow(2));
        var res2 = new Rect2i(3, 2, 2, 1);
        Assertions.assertEquals(res2, rect1.grow(-1));
        var rect2 = new Rect2i(6, 4, -4, -3);
        var res3 = new Rect2i(5, 3, -2, -1);
        Assertions.assertEquals(res3, rect2.grow(1));
        var res4 = new Rect2i(7, 5, -6, -5);
        Assertions.assertEquals(res4, rect2.grow(-1));
    }

    @Test
    public void testExpandTo() {
        var rect1 = new Rect2i(0, 0, 5, 2);
        var p1 = new Vector2i(10, 0);
        var p2 = new Vector2i(-5, 5);
        var res1 = new Rect2i(0, 0, 10, 2);
        Assertions.assertEquals(res1, rect1.expandTo(p1));
        var res2 = new Rect2i(-5, 0, 10, 5);
        Assertions.assertEquals(res2, rect1.expandTo(p2));
        var rect2 = new Rect2i(5, 2, -5, -2);
        Assertions.assertEquals(res1, rect2.expandTo(p1));
    }

    @Test
    public void testMerge() {
        var rect1 = new Rect2i(1, 1, 3, 2);
        var rect2 = new Rect2i(3, 2, 4, 2);
        var res = new Rect2i(1, 1, 6, 3);
        Assertions.assertEquals(res, rect1.merge(rect2));
        var rect3 = new Rect2i(7, 4, -4, -2);
        Assertions.assertEquals(res, rect1.merge(rect3));
    }

    @Test
    public void testIsCongruent() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2i(6, 4, -4, -3);
        Assertions.assertTrue(rect1.isCongruentTo(rect2));
        Assertions.assertTrue(rect1.isCongruentTo(rect1));
    }

    @Test
    public void testToFloat() {
        var rect1 = new Rect2i(2, 1, 4, 3);
        var rect2 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        Assertions.assertEquals(rect2, rect1.toFloat());
    }
}
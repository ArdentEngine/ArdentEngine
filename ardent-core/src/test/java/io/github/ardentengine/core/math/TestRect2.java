package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRect2 {

    @Test
    public void testPosition() {
        var rect = new Rect2(1.0f, 2.0f, 3.0f, 4.0f);
        var res = new Vector2(1.0f, 2.0f);
        Assertions.assertEquals(res, rect.position());
    }

    @Test
    public void testSize() {
        var rect = new Rect2(1.0f, 2.0f, 3.0f, 4.0f);
        var res = new Vector2(3.0f, 4.0f);
        Assertions.assertEquals(res, rect.size());
    }

    @Test
    public void testEnd() {
        var rect1 = new Rect2(1.0f, 2.0f, 3.0f, 4.0f);
        var res1 = new Vector2(4.0f, 6.0f);
        Assertions.assertEquals(res1, rect1.end());
        var rect2 = new Rect2(1.0f, 2.0f, -3.0f, -4.0f);
        var res2 = new Vector2(-2.0f, -2.0f);
        Assertions.assertEquals(res2, rect2.end());
    }

    @Test
    public void testCenter() {
        var rect = new Rect2(1.0f, 2.0f, 3.0f, 4.0f);
        var res = new Vector2(2.5f, 4.0f);
        Assertions.assertEquals(res, rect.center());
    }

    @Test
    public void testArea() {
        var rect = new Rect2(0.0f, 0.0f, 4.0f, 3.0f);
        Assertions.assertEquals(12.0f, rect.area());
    }

    @Test
    public void testExtents() {
        var rect = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        Assertions.assertEquals(2.0f, rect.left());
        Assertions.assertEquals(6.0f, rect.right());
        Assertions.assertEquals(4.0f, rect.top());
        Assertions.assertEquals(1.0f, rect.bottom());
    }

    @Test
    public void testExtentsNegativeSize() {
        var rect = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        Assertions.assertEquals(2.0f, rect.left());
        Assertions.assertEquals(6.0f, rect.right());
        Assertions.assertEquals(4.0f, rect.top());
        Assertions.assertEquals(1.0f, rect.bottom());
    }

    @Test
    public void testCorners() {
        var rect = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var bottomLeft = new Vector2(2.0f, 1.0f);
        var topLeft = new Vector2(2.0f, 4.0f);
        var bottomRight = new Vector2(6.0f, 1.0f);
        var topRight = new Vector2(6.0f, 4.0f);
        Assertions.assertEquals(bottomLeft, rect.bottomLeft());
        Assertions.assertEquals(topLeft, rect.topLeft());
        Assertions.assertEquals(bottomRight, rect.bottomRight());
        Assertions.assertEquals(topRight, rect.topRight());
    }

    @Test
    public void testCornersNegativeSize() {
        var rect = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        var bottomLeft = new Vector2(2.0f, 1.0f);
        var topLeft = new Vector2(2.0f, 4.0f);
        var bottomRight = new Vector2(6.0f, 1.0f);
        var topRight = new Vector2(6.0f, 4.0f);
        Assertions.assertEquals(bottomLeft, rect.bottomLeft());
        Assertions.assertEquals(topLeft, rect.topLeft());
        Assertions.assertEquals(bottomRight, rect.bottomRight());
        Assertions.assertEquals(topRight, rect.topRight());
    }

    @Test
    public void testContainsPoint() {
        var rect = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var p1 = new Vector2(3.0f, 2.0f);
        var p2 = new Vector2(-1.0f, -1.0f);
        var p3 = new Vector2(2.0f, 1.0f);
        Assertions.assertTrue(rect.containsPoint(p1));
        Assertions.assertFalse(rect.containsPoint(p2));
        Assertions.assertFalse(rect.containsPoint(p3));
        Assertions.assertTrue(rect.containsPoint(p3, true));
    }

    @Test
    public void testContainsPointNegativeSize() {
        var rect = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        Assertions.assertTrue(rect.containsPoint(3.0f, 2.0f));
        Assertions.assertFalse(rect.containsPoint(-1.0f, -1.0f));
        Assertions.assertFalse(rect.containsPoint(2.0f, 1.0f));
        Assertions.assertTrue(rect.containsPoint(2.0f, 1.0f, true));
    }

    @Test
    public void testIntersects() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(3.0f, 2.0f, 4.0f, 3.0f);
        Assertions.assertTrue(rect1.intersects(rect2));
        var rect3 = new Rect2(8.0f, 1.0f, 3.0f, 2.0f);
        Assertions.assertFalse(rect1.intersects(rect3));
        var rect4 = new Rect2(6.0f, 2.0f, 4.0f, 3.0f);
        Assertions.assertFalse(rect1.intersects(rect4));
        Assertions.assertTrue(rect1.intersects(rect4, true));
        var rect5 = new Rect2(7.0f, 5.0f, -4.0f, -3.0f);
        Assertions.assertTrue(rect1.intersects(rect5));
    }

    @Test
    public void testFromPoints() {
        var p1 = new Vector2(2.0f, 1.0f);
        var p2 = new Vector2(6.0f, 4.0f);
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        Assertions.assertEquals(rect1, Rect2.fromPoints(p1, p2));
        var p3 = new Vector2(0.0f, 5.0f);
        var rect2 = new Rect2(0.0f, 1.0f, 2.0f, 4.0f);
        Assertions.assertEquals(rect2, Rect2.fromPoints(p1, p3));
    }

    @Test
    public void testIntersection() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(3.0f, 2.0f, 4.0f, 3.0f);
        var res = new Rect2(3.0f, 2.0f, 3.0f, 2.0f);
        Assertions.assertEquals(res, rect1.intersection(rect2));
        var rect3 = new Rect2(7.0f, 5.0f, -4.0f, -3.0f);
        Assertions.assertEquals(res, rect1.intersection(rect3));
        var rect4 = new Rect2(1.0f, 1.0f, 2.0f, 2.0f);
        var rect5 = new Rect2(-1.0f, -1.0f, -2.0f, -2.0f);
        Assertions.assertEquals(new Rect2(), rect4.intersection(rect5));
    }

    @Test
    public void testAbs() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        Assertions.assertEquals(rect1, rect2.abs());
    }

    @Test
    public void testEncloses() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(3.0f, 2.0f, 4.0f, 3.0f);
        Assertions.assertFalse(rect1.encloses(rect2));
        var rect3 = new Rect2(2.5f, 1.5f, 3.5f, 2.5f);
        Assertions.assertTrue(rect1.encloses(rect3));
        var rect4 = new Rect2(6.0f, 4.0f, -3.5f, -2.5f);
        Assertions.assertTrue(rect1.encloses(rect4));
    }

    @Test
    public void testGrow() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var res = new Rect2(1.5f, 1.5f, 5.5f, 2.0f);
        Assertions.assertEquals(res, rect1.grow(0.5f, -0.5f, 1.0f, -0.5f));
    }

    @Test
    public void testGrowAllSides() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var res1 = new Rect2(0.5f, -0.5f, 7.0f, 6.0f);
        Assertions.assertEquals(res1, rect1.grow(1.5f));
        var res2 = new Rect2(3.0f, 2.0f, 2.0f, 1.0f);
        Assertions.assertEquals(res2, rect1.grow(-1.0f));
        var rect2 = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        var res3 = new Rect2(5.0f, 3.0f, -2.0f, -1.0f);
        Assertions.assertEquals(res3, rect2.grow(1.0f));
        var res4 = new Rect2(7.0f, 5.0f, -6.0f, -5.0f);
        Assertions.assertEquals(res4, rect2.grow(-1.0f));
    }

    @Test
    public void testExpandToPoint() {
        var rect1 = new Rect2(0.0f, 0.0f, 5.0f, 2.0f);
        var p1 = new Vector2(10.0f, 0.0f);
        var p2 = new Vector2(-5.0f, 5.0f);
        var res1 = new Rect2(0.0f, 0.0f, 10.0f, 2.0f);
        Assertions.assertEquals(res1, rect1.expandTo(p1));
        var res2 = new Rect2(-5.0f, 0.0f, 10.0f, 5.0f);
        Assertions.assertEquals(res2, rect1.expandTo(p2));
        var rect2 = new Rect2(5.0f, 2.0f, -5.0f, -2.0f);
        Assertions.assertEquals(res1, rect2.expandTo(p1));
    }

    @Test
    public void testMerge() {
        var rect1 = new Rect2(1.0f, 1.0f, 3.0f, 2.0f);
        var rect2 = new Rect2(3.0f, 2.0f, 4.0f, 2.0f);
        var res = new Rect2(1.0f, 1.0f, 6.0f, 3.0f);
        Assertions.assertEquals(res, rect1.merge(rect2));
        var rect3 = new Rect2(7.0f, 4.0f, -4.0f, -2.0f);
        Assertions.assertEquals(res, rect1.merge(rect3));
    }

    @Test
    public void testBoundingCircle() {
        var rect = new Rect2(1.0f, 1.0f, 3.0f, 2.0f);
        var circle = new Circle(2.5f, 2.0f, (float) Math.sqrt(3.25));
        Assertions.assertEquals(circle, rect.boundingCircle());
    }

    @Test
    public void testTransform2x2() {
        var rotation = Matrix2.rotation(Math.PI / 2.0);
        var rect = new Rect2(0.0f, 0.0f, 4.0f, 3.0f);
        var res1 = new Rect2(-3.0f, 0.0f, 3.0f, 4.0f);
        Assertions.assertEquals(res1, rect.transform(rotation));
        var scaling = Matrix2.scaling(2.0f, 3.0f);
        var res2 = new Rect2(0.0f, 0.0f, 8.0f, 9.0f);
        Assertions.assertEquals(res2, rect.transform(scaling));
    }

    @Test
    public void testTransform2x3() {
        var translation = Matrix2x3.translation(2.0f, 3.0f);
        var rect = new Rect2(2.0f, 1.0f, 4.0f, 2.0f);
        var res1 = new Rect2(4.0f, 4.0f, 4.0f, 2.0f);
        Assertions.assertEquals(res1, rect.transform(translation));
        var transform = Matrix2.rotation(Math.PI / 2.0).multiply(translation);
        var res2 = new Rect2(-6.0f, 4.0f, 2.0f, 4.0f);
        Assertions.assertEquals(res2, rect.transform(transform));
    }

    @Test
    public void testTransform3x3() {
        var translation = Matrix3.translation(2.0f, 3.0f);
        var rect = new Rect2(2.0f, 1.0f, 4.0f, 2.0f);
        var res1 = new Rect2(4.0f, 4.0f, 4.0f, 2.0f);
        Assertions.assertEquals(res1, rect.transform(translation));
        var transform = Matrix3.rotationZ(Math.PI / 2.0).multiply(translation);
        var res2 = new Rect2(-6.0f, 4.0f, 2.0f, 4.0f);
        Assertions.assertEquals(res2, rect.transform(transform));
    }

    // TODO: Inverse transform

    @Test
    public void testIsCongruentTo() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        Assertions.assertTrue(rect1.isCongruentTo(rect2));
        Assertions.assertTrue(rect1.isCongruentTo(rect1));
    }

    @Test
    public void testEqualsApprox() {
        var rect1 = new Rect2(2.0f, 1.0f, 4.0f, 3.0f);
        var rect2 = new Rect2(1.9999999f, 1.0000001f, 4.0000001f, 2.9999999f);
        var rect3 = new Rect2(6.0f, 4.0f, -4.0f, -3.0f);
        Assertions.assertNotEquals(rect1, rect2);
        Assertions.assertTrue(rect1.equalsApprox(rect2));
        Assertions.assertFalse(rect1.equalsApprox(rect3));
    }

    @Test
    public void testToInt() {
        var rect1 = new Rect2(2.1f, 1.9f, 4.4f, 3.2f);
        var rect2 = new Rect2i(2, 1, 4, 3);
        Assertions.assertEquals(rect2, rect1.toInt());
    }
}
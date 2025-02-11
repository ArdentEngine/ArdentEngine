package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCircle {

    @Test
    public void testCircleFromCenterAndPoint() {
        var res = new Circle(1.0f, 1.0f, (float) Math.sqrt(2.0));
        Assertions.assertEquals(res, new Circle(Vector2.ONE, new Vector2(2.0f, 2.0f)));
        Assertions.assertEquals(res, new Circle(1.0f, 1.0f, new Vector2(2.0f, 2.0f)));
        Assertions.assertEquals(res, new Circle(Vector2.ONE, 2.0f, 2.0f));
    }

    @Test
    public void testCircleFromThreePoints() {
        var p1 = new Vector2(-1.0f, 2.0f);
        var p2 = new Vector2(2.0f, 3.0f);
        var p3 = new Vector2(4.0f, 2.0f);
        var circle = new Circle(1.5f, -0.5f, (float) Math.sqrt(12.5));
        Assertions.assertEquals(circle, new Circle(p1, p2, p3));
    }

    @Test
    public void testDiameter() {
        var circle = new Circle(1.0f, 1.0f, 2.0f);
        Assertions.assertEquals(4.0f, circle.diameter(), MathUtils.EPSILON);
    }

    @Test
    public void testCircumference() {
        var circle = new Circle(1.0f, 1.0f, 2.0f);
        Assertions.assertEquals(4.0 * Math.PI, circle.circumference(), MathUtils.EPSILON);
    }

    @Test
    public void testRadiusSquared() {
        var circle = new Circle(1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(9.0, circle.radiusSquared(), MathUtils.EPSILON);
    }

    @Test
    public void testArea() {
        var circle = new Circle(1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(9.0 * Math.PI, circle.area(), MathUtils.EPSILON);
        Assertions.assertEquals(0.0f, new Circle().area(), MathUtils.EPSILON);
    }

    @Test
    public void testContainsPoint() {
        var circle = new Circle(1.0f, 1.0f, 3.0f);
        var p1 = new Vector2(1.0f, 2.0f);
        var p2 = new Vector2(1.0f, 4.0f);
        var p3 = new Vector2(-1.0f, -5.0f);
        Assertions.assertTrue(circle.containsPoint(p1));
        Assertions.assertFalse(circle.containsPoint(p2, false));
        Assertions.assertTrue(circle.containsPoint(p2, true));
        Assertions.assertFalse(circle.containsPoint(p3, false));
        Assertions.assertFalse(circle.containsPoint(p3, true));
    }

    @Test
    public void testIsPointOnCircumference() {
        var circle = new Circle(1.0f, 1.0f, 3.0f);
        var p1 = new Vector2(1.0f, 2.0f);
        var p2 = new Vector2(1.0f, 4.0f);
        var p3 = new Vector2(-1.0f, -5.0f);
        Assertions.assertFalse(circle.isPointOnCircumference(p1));
        Assertions.assertTrue(circle.isPointOnCircumference(p2));
        Assertions.assertFalse(circle.isPointOnCircumference(p3));
    }

    @Test
    public void testIntersects() {
        var c1 = new Circle(1.0f, 1.0f, 2.0f);
        var c2 = new Circle(1.0f, 1.5f, 2.5f);
        var c3 = new Circle(5.0f, 1.0f, 2.0f);
        var c4 = new Circle(-1.0f, -5.0f, 1.0f);
        Assertions.assertTrue(c1.intersects(c2));
        Assertions.assertFalse(c1.intersects(c3, false));
        Assertions.assertTrue(c1.intersects(c3, true));
        Assertions.assertFalse(c1.intersects(c4, false));
        Assertions.assertFalse(c1.intersects(c4, true));
    }

    @Test
    public void testEncloses() {
        var c1 = new Circle(0.0f, 0.0f, 3.0f);
        var c2 = new Circle(0.0f, 0.0f, 1.3f);
        var c3 = new Circle(0.0f, 0.1f, 3.0f);
        var c4 = new Circle(0.0f, 2.5f, 0.3f);
        Assertions.assertTrue(c1.encloses(c2));
        Assertions.assertFalse(c1.encloses(c3));
        Assertions.assertTrue(c1.encloses(c4));
    }

    @Test
    public void testMerge() {
        var c1 = new Circle(0.0f, 0.0f, 2.0f);
        var c2 = new Circle(3.0f, 0.0f, 2.0f);
        var res = new Circle(1.5f, 0.0f, 3.5f);
        Assertions.assertEquals(res, c1.merge(c2));
    }

    @Test
    public void testBoundingRect() {
        var circle = new Circle(1.0f, 1.0f, 2.0f);
        var rectangle = new Rect2(-1.0f, -1.0f, 4.0f, 4.0f);
        Assertions.assertEquals(rectangle, circle.boundingRect());
    }

    @Test
    public void testEqualsApprox() {
        var c1 = new Circle(1.0f, 1.0f, 2.0f);
        var c2 = new Circle(1.0000001f, 0.9999999f, 2.0000001f);
        Assertions.assertNotEquals(c1, c2);
        Assertions.assertTrue(c1.equalsApprox(c2));
    }
}
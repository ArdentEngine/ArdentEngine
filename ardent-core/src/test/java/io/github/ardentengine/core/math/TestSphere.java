package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSphere {

    @Test
    public void testSphereFromCenterAndPoint() {
        var res = new Sphere(1.0f, 1.0f, 1.0f, (float) Math.sqrt(3.0));
        Assertions.assertEquals(res, new Sphere(Vector3.ONE, new Vector3(2.0f, 2.0f, 2.0f)));
        Assertions.assertEquals(res, new Sphere(1.0f, 1.0f, 1.0f, new Vector3(2.0f, 2.0f, 2.0f)));
        Assertions.assertEquals(res, new Sphere(Vector3.ONE, 2.0f, 2.0f, 2.0f));
    }

    @Test
    public void testDiameter() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 2.0f);
        Assertions.assertEquals(4.0f, sphere.diameter());
    }

    @Test
    public void testRadiusSquared() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(9.0f, sphere.radiusSquared());
    }

    @Test
    public void testSurfaceArea() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(36.0 * Math.PI, sphere.surfaceArea(), MathUtils.EPSILON);
        Assertions.assertEquals(0.0, new Sphere().surfaceArea(), MathUtils.EPSILON);
    }

    @Test
    public void testRadiusCubed() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(27.0f, sphere.radiusCubed());
    }

    @Test
    public void testVolume() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        Assertions.assertEquals(36.0 * Math.PI, sphere.volume(), MathUtils.EPSILON);
        Assertions.assertEquals(0.0, new Sphere().volume(), MathUtils.EPSILON);
    }

    @Test
    public void testContainsPoint() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        var p1 = new Vector3(1.0f, 2.0f, 1.0f);
        var p2 = new Vector3(1.0f, 4.0f, 1.0f);
        var p3 = new Vector3(-1.0f, -5.0f, 1.0f);
        Assertions.assertTrue(sphere.containsPoint(p1));
        Assertions.assertFalse(sphere.containsPoint(p2, false));
        Assertions.assertTrue(sphere.containsPoint(p2, true));
        Assertions.assertFalse(sphere.containsPoint(p3, false));
        Assertions.assertFalse(sphere.containsPoint(p3, true));
    }

    @Test
    public void testIsPointOnSurface() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 3.0f);
        var p1 = new Vector3(1.0f, 2.0f, 1.0f);
        var p2 = new Vector3(1.0f, 4.0f, 1.0f);
        var p3 = new Vector3(-1.0f, -5.0f, 1.0f);
        Assertions.assertFalse(sphere.isPointOnSurface(p1));
        Assertions.assertTrue(sphere.isPointOnSurface(p2));
        Assertions.assertFalse(sphere.isPointOnSurface(p3));
    }

    @Test
    public void testIntersects() {
        var s1 = new Sphere(1.0f, 1.0f, 1.0f, 2.0f);
        var s2 = new Sphere(1.0f, 1.5f, 1.0f, 2.5f);
        var s3 = new Sphere(5.0f, 1.0f, 1.0f, 2.0f);
        var s4 = new Sphere(-1.0f, -5.0f, 1.0f, 1.0f);
        Assertions.assertTrue(s1.intersects(s2));
        Assertions.assertFalse(s1.intersects(s3, false));
        Assertions.assertTrue(s1.intersects(s3, true));
        Assertions.assertFalse(s1.intersects(s4, false));
        Assertions.assertFalse(s1.intersects(s4, true));
    }

    @Test
    public void testEncloses() {
        var s1 = new Sphere(Vector3.ZERO, 3.0f);
        var s2 = new Sphere(Vector3.ZERO, 1.3f);
        var s3 = new Sphere(0.0f, 0.1f, 0.1f, 3.0f);
        var s4 = new Sphere(0.0f, 2.5f, 0.0f, 0.3f);
        Assertions.assertTrue(s1.encloses(s2));
        Assertions.assertFalse(s1.encloses(s3));
        Assertions.assertTrue(s1.encloses(s4));
    }

    @Test
    public void testMerge() {
        var s1 = new Sphere(0.0f, 0.0f, 2.0f, 2.0f);
        var s2 = new Sphere(3.0f, 0.0f, 2.0f, 2.0f);
        var res = new Sphere(1.5f, 0.0f, 2.0f, 3.5f);
        Assertions.assertEquals(res, s1.merge(s2));
    }

    @Test
    public void testBoundingBox() {
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, 2.0f);
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 4.0f, 4.0f, 4.0f);
        Assertions.assertEquals(aabb, sphere.boundingBox());
    }

    @Test
    public void testEqualsApprox() {
        var s1 = new Sphere(1.0f, 1.0f, 2.0f, 3.0f);
        var s2 = new Sphere(1.0000001f, 0.9999999f, 2.0000001f, 2.9999999f);
        Assertions.assertNotEquals(s1, s2);
        Assertions.assertTrue(s1.equalsApprox(s2));
    }
}
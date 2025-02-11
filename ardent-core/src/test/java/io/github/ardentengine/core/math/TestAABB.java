package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAABB {

    @Test
    public void testPosition() {
        var aabb = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var position = new Vector3(-1.0f, -2.0f, -3.0f);
        Assertions.assertEquals(position, aabb.position());
    }

    @Test
    public void testSize() {
        var aabb = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var size = new Vector3(3.0f, 2.0f, 1.0f);
        Assertions.assertEquals(size, aabb.size());
    }

    @Test
    public void testEnd() {
        var aabb = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var end = new Vector3(2.0f, 0.0f, -2.0f);
        Assertions.assertEquals(end, aabb.end());
    }

    @Test
    public void testCenter() {
        var aabb = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var center = new Vector3(0.5f, -1.0f, -2.5f);
        Assertions.assertEquals(center, aabb.center());
    }

    @Test
    public void testVolume() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(24.0f, aabb.volume());
    }

    @Test
    public void testLeft() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        Assertions.assertEquals(0.0f, aabb.x());
        Assertions.assertEquals(-2.0f, aabb.left());
    }

    @Test
    public void testLeftPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        var plane = new Plane(-1.0f, 0.0f, 0.0f, -2.0f);
        Assertions.assertEquals(plane, aabb.leftPlane());
    }

    @Test
    public void testRight() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(0.0f, aabb.x());
        Assertions.assertEquals(2.0f, aabb.right());
    }

    @Test
    public void testRightPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        var plane = new Plane(1.0f, 0.0f, 0.0f, 2.0f);
        Assertions.assertEquals(plane, aabb.rightPlane());
    }

    @Test
    public void testTop() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(0.0f, aabb.y());
        Assertions.assertEquals(3.0f, aabb.top());
    }

    @Test
    public void testTopPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        var plane = new Plane(0.0f, 1.0f, 0.0f, 3.0f);
        Assertions.assertEquals(plane, aabb.topPlane());
    }

    @Test
    public void testBottom() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        Assertions.assertEquals(0.0f, aabb.y());
        Assertions.assertEquals(-3.0f, aabb.bottom());
    }

    @Test
    public void testBottomPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        var plane = new Plane(0.0f, -1.0f, 0.0f, -3.0f);
        Assertions.assertEquals(plane, aabb.bottomPlane());
    }

    @Test
    public void testFront() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        Assertions.assertEquals(0.0f, aabb.z());
        Assertions.assertEquals(-4.0f, aabb.front());
    }

    @Test
    public void testFrontPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        var plane = new Plane(0.0f, 0.0f, 1.0f, -4.0f);
        Assertions.assertEquals(plane, aabb.frontPlane());
    }

    @Test
    public void testBack() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(0.0f, aabb.z());
        Assertions.assertEquals(4.0f, aabb.back());
    }

    @Test
    public void testBackPlane() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 4.0f);
        var plane = new Plane(0.0f, 0.0f, -1.0f, 4.0f);
        Assertions.assertEquals(plane, aabb.backPlane());
    }

    @Test
    public void testContainsPoint() {
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 3.0f, 2.0f, 3.0f);
        var p1 = new Vector3(1.0f, 0.5f, 1.0f);
        Assertions.assertTrue(aabb.containsPoint(p1));
        var p2 = new Vector3(2.0f, 1.0f, -1.0f);
        Assertions.assertTrue(aabb.containsPoint(p2, true));
        Assertions.assertFalse(aabb.containsPoint(p2, false));
        var p3 = new Vector3(3.0f, 2.0f, 0.5f);
        Assertions.assertFalse(aabb.containsPoint(p3, true));
        Assertions.assertFalse(aabb.containsPoint(p3, false));
    }

    @Test
    public void testIntersectsAABB() {
        var aabb1 = new AABB(-1.0f, -1.0f, -1.0f, 3.0f, 2.0f, 3.0f);
        var aabb2 = new AABB(0.0f, 0.0f, 0.0f, 3.0f, 3.0f, 2.0f);
        Assertions.assertTrue(aabb1.intersects(aabb2));
        var aabb3 = new AABB(2.0f, -1.0f, 0.0f, 2.0f, 3.0f, 3.0f);
        Assertions.assertFalse(aabb1.intersects(aabb3, false));
        Assertions.assertTrue(aabb1.intersects(aabb3, true));
        var aabb4 = new AABB(4.0f, 5.0f, 2.0f, 1.0f, 1.0f, 1.0f);
        Assertions.assertFalse(aabb1.intersects(aabb4, false));
        Assertions.assertFalse(aabb1.intersects(aabb4, true));
    }

    @Test
    public void testFromPoints() {
        var p1 = new Vector3(-1.0f, -2.0f, -3.0f);
        var p2 = new Vector3(2.0f, 0.0f, -2.0f);
        var aabb = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        Assertions.assertEquals(aabb, AABB.fromPoints(p1, p2));
    }

    @Test
    public void testIntersectionWithAABB() {
        var aabb1 = new AABB(-1.0f, -1.0f, -1.0f, 3.0f, 2.0f, 3.0f);
        var aabb2 = new AABB(0.0f, 0.0f, 0.0f, 3.0f, 3.0f, 2.0f);
        var res = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 1.0f, 2.0f);
        Assertions.assertEquals(res, aabb1.intersection(aabb2));
        var aabb3 = new AABB(4.0f, 5.0f, 2.0f, 1.0f, 1.0f, 1.0f);
        Assertions.assertEquals(new AABB(), aabb1.intersection(aabb3));
    }

    @Test
    public void testIntersectsPlane() {
        var aabb1 = new AABB(-1.0f, -1.0f, -1.0f, 3.0f, 3.0f, 2.0f);
        var plane = new Plane(1.0f, 0.0f, 1.0f, 2.0f);
        Assertions.assertTrue(aabb1.intersects(plane));
        var aabb2 = new AABB(-3.0f, -2.0f, -1.0f, 3.0f, 3.0f, 2.0f);
        Assertions.assertFalse(aabb2.intersects(plane));
    }

    @Test
    public void testAbs() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, -2.0f, -3.0f, -4.0f);
        var res = new AABB(-2.0f, -3.0f, -4.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(res, aabb.abs());
    }

    @Test
    public void testEncloses() {
        var aabb1 = new AABB(-2.0f, -2.0f, -2.0f, 4.0f, 4.0f, 4.0f);
        var aabb2 = new AABB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
        var aabb3 = new AABB(-0.5f, -0.5f, -0.5f, 3.0f, 3.0f, 3.0f);
        Assertions.assertTrue(aabb1.encloses(aabb2));
        Assertions.assertFalse(aabb1.encloses(aabb3));
    }

    @Test
    public void testGrow() {
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
        var res = new AABB(-2.0f, -2.0f, -2.0f, 4.0f, 4.0f, 4.0f);
        Assertions.assertEquals(res, aabb.grow(1.0f));
    }

    @Test
    public void testExpandToPoint() {
        var aabb = new AABB(-0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 1.0f);
        var p1 = new Vector3(2.0f, 0.0f, 0.0f);
        var res1 = new AABB(-0.5f, -0.5f, -0.5f, 2.5f, 1.0f, 1.0f);
        Assertions.assertEquals(res1, aabb.expandTo(p1));
        var p2 = new Vector3(0.0f, -1.0f, 0.0f);
        var res2 = new AABB(-0.5f, -1.0f, -0.5f, 1.0f, 1.5f, 1.0f);
        Assertions.assertEquals(res2, aabb.expandTo(p2));
    }

    @Test
    public void testMerge() {
        var aabb1 = new AABB(-1.0f, -1.0f, -2.0f, 2.0f, 3.0f, 4.0f);
        var aabb2 = new AABB(0.0f, 0.0f, 1.0f, 2.0f, 4.0f, 3.0f);
        var res = new AABB(-1.0f, -1.0f, -2.0f, 3.0f, 5.0f, 6.0f);
        Assertions.assertEquals(res, aabb1.merge(aabb2));
    }

    @Test
    public void testBoundingSphere() {
        var aabb = new AABB(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f);
        var sphere = new Sphere(1.0f, 1.0f, 1.0f, (float) Math.sqrt(3.0));
        Assertions.assertEquals(sphere, aabb.boundingSphere());
    }

    @Test
    public void testTransform3x3() {
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
        var transform = Matrix3.scaling(2.0f, 3.0f, 2.0f);
        var res = new AABB(-2.0f, -3.0f, -2.0f, 4.0f, 6.0f, 4.0f);
        Assertions.assertEquals(res, aabb.transform(transform));
    }

    @Test
    public void testTransform3x4() {
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
        var transform = Matrix3x4.translation(2.0f, 3.0f, 2.0f);
        var res = new AABB(1.0f, 2.0f, 1.0f, 2.0f, 2.0f, 2.0f);
        Assertions.assertEquals(res, aabb.transform(transform));
    }

    @Test
    public void testTransform4x4() {
        var aabb = new AABB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
        var transform = Matrix4.translation(2.0f, 3.0f, 2.0f).multiply(Matrix4.scaling(2.0f, 3.0f, 2.0f));
        var res = new AABB(0.0f, 0.0f, 0.0f, 4.0f, 6.0f, 4.0f);
        Assertions.assertEquals(res, aabb.transform(transform));
    }

    // TODO: Inverse transform

    // TODO: Support

    @Test
    public void testIsCongruentTo() {
        var aabb1 = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var aabb2 = new AABB(2.0f, 0.0f, -2.0f, -3.0f, -2.0f, -1.0f);
        Assertions.assertTrue(aabb1.isCongruentTo(aabb2));
    }

    @Test
    public void testEqualsApprox() {
        var aabb1 = new AABB(-1.0f, -2.0f, -3.0f, 3.0f, 2.0f, 1.0f);
        var aabb2 = new AABB(-1.0000001f, -1.9999999f, -2.9999999f, 3.0000001f, 2.0000001f, 0.99999999f);
        Assertions.assertNotEquals(aabb1, aabb2);
        Assertions.assertTrue(aabb1.equalsApprox(aabb2));
    }
}
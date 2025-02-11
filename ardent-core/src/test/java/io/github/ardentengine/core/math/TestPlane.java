package io.github.ardentengine.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPlane {

    @Test
    public void testPlaneThroughThreePoints() {
        var p1 = new Vector3(-1.0f, -2.0f, 0.0f);
        var p2 = new Vector3(-2.0f, 1.0f, 2.0f);
        var p3 = new Vector3(1.5f, 1.5f, 1.0f);
        var plane = new Plane(p1, p2, p3);
        var res = new Plane(-4.0f, 6.0f, -11.0f, -8.0f);
        Assertions.assertEquals(res, plane);
    }

    @Test
    public void testPlaneEquation() {
        var plane = new Plane(1.0f, 2.0f, 3.0f, 4.0f);
        Assertions.assertEquals(1.0f, plane.a());
        Assertions.assertEquals(2.0f, plane.b());
        Assertions.assertEquals(3.0f, plane.c());
        Assertions.assertEquals(4.0f, plane.d());
    }

    // TODO: Signed distance

    @Test
    public void testDistancePointPlane() {
        var plane = new Plane(1.0f, 2.0f, 3.0f, -4.0f);
        var point = new Vector3(3.0f, 4.0f, 5.0f);
        Assertions.assertEquals(11.0 / 7.0 * Math.sqrt(14.0), plane.distanceTo(point), MathUtils.EPSILON);
    }

    @Test
    public void testFlip() {
        var plane = new Plane(1.0f, 0.0f, 1.0f, -2.0f);
        var res = new Plane(-1.0f, -0.0f, -1.0f, 2.0f);
        Assertions.assertEquals(res, plane.flip());
    }

    @Test
    public void testContainsPoint() {
        var p1 = new Vector3(-1.0f, -2.0f, 0.0f);
        var p2 = new Vector3(-2.0f, 1.0f, 2.0f);
        var p3 = new Vector3(1.5f, 1.5f, 1.0f);
        var plane = new Plane(-4.0f, 6.0f, -11.0f, -8.0f);
        Assertions.assertTrue(plane.containsPoint(p1));
        Assertions.assertTrue(plane.containsPoint(p2));
        Assertions.assertTrue(plane.containsPoint(p3));
        Assertions.assertFalse(plane.containsPoint(3.5f, -1.0f, 2.9f));
    }

    @Test
    public void testIsParallelTo() {
        var plane1 = new Plane(0.0f, 1.0f, 0.0f, 0.0f);
        var plane2 = new Plane(0.0f, 2.0f, 0.0f, 5.0f);
        Assertions.assertTrue(plane1.isParallelTo(plane2));
        var plane3 = new Plane(1.0f, 0.0f, 1.0f, 2.0f);
        Assertions.assertFalse(plane1.isParallelTo(plane3));
        Assertions.assertTrue(plane1.isParallelTo(plane1));
        var plane4 = new Plane(2.0f, 0.0f, 2.0f, 4.0f);
        Assertions.assertTrue(plane3.isParallelTo(plane4));
    }

    @Test
    public void testIsCongruentTo() {
        var plane1 = new Plane(0.0f, 1.0f, 0.0f, 3.0f);
        var plane2 = new Plane(0.0f, 1.0f, 0.0f, 3.0f);
        var plane3 = new Plane(0.0f, 2.0f, 0.0f, 6.0f);
        Assertions.assertTrue(plane1.isCongruentTo(plane2));
        Assertions.assertTrue(plane1.isCongruentTo(plane3));
        var plane4 = new Plane(0.0f, 2.0f, 0.0f, 5.0f);
        Assertions.assertFalse(plane1.isCongruentTo(plane4));
        var plane5 = new Plane(2.0f, 0.0f, 2.0f, 4.0f);
        Assertions.assertFalse(plane1.isCongruentTo(plane5));
        var plane6 = new Plane(0.2f, 0.0f, 0.2f, 0.4f);
        Assertions.assertTrue(plane5.isCongruentTo(plane6));
    }

    @Test
    public void testIntersects() {
        var plane1 = new Plane(0.0f, 1.0f, 0.0f, 3.0f);
        var plane2 = new Plane(1.0f, 0.0f, 1.0f, 2.0f);
        Assertions.assertTrue(plane1.intersects(plane2));
        var plane3 = new Plane(0.0f, 1.0f, 0.0f, 5.0f);
        Assertions.assertFalse(plane1.intersects(plane3));
        var plane4 = new Plane(3.0f, 0.0f, 3.0f, 6.0f);
        Assertions.assertTrue(plane2.intersects(plane4));
    }

    @Test
    public void testIntersectsInPoint() {
        var plane1 = new Plane(1.0f, 2.0f, 1.0f, 1.0f);
        var plane2 = new Plane(2.0f, 0.0f, 1.0f, 1.0f);
        var plane3 = new Plane(1.0f, 1.0f, 2.0f, 1.0f);
        Assertions.assertTrue(plane1.intersectsInPoint(plane2, plane3));
        var plane4 = new Plane(0.0f, 1.0f, 0.0f, 3.0f);
        var plane5 = new Plane(0.0f, 1.0f, 0.0f, 7.0f);
        var plane6 = new Plane(1.0f, 0.0f, 0.0f, 2.0f);
        Assertions.assertFalse(plane4.intersectsInPoint(plane5, plane6));
    }

    @Test
    public void testIntersectionPoint() {
        var plane1 = new Plane(1.0f, 2.0f, 1.0f, 1.0f);
        var plane2 = new Plane(2.0f, 1.0f, 1.0f, 1.0f);
        var plane3 = new Plane(1.0f, 1.0f, 2.0f, 1.0f);
        var res = new Vector3(0.25f, 0.25f, 0.25f);
        Assertions.assertEquals(res, plane1.intersection(plane2, plane3));
    }

    // TODO: Project point onto plane

    @Test
    public void testIsPointOver() {
        var plane1 = new Plane(0.0f, 1.0f, 0.0f, 2.0f);
        var p1 = new Vector3(1.0f, 4.0f, 1.0f);
        var p2 = new Vector3(2.0f, -3.0f, 3.0f);
        Assertions.assertTrue(plane1.isPointOver(p1));
        Assertions.assertFalse(plane1.isPointOver(p2));
        var plane2 = new Plane(0.0f, -1.0f, 0.0f, -2.0f);
        Assertions.assertFalse(plane2.isPointOver(p1));
        Assertions.assertTrue(plane2.isPointOver(p2));
        var plane3 = new Plane(1.0f, 0.0f, 1.0f, 0.0f);
        Assertions.assertTrue(plane3.isPointOver(1.0f, 0.0f, 1.0f));
        Assertions.assertFalse(plane3.isPointOver(-1.0f, 0.0f, -1.0f));
    }

    // TODO: IsPointBelow

    @Test
    public void testTransform3x3() {
        var transform = Matrix3.rotation(Math.PI / 2.0, 0.0, Math.PI / 4.0);
        var plane = new Plane(1.0f, 1.0f, 0.0f, 1.0f);
        var res = new Plane(0.0f, 0.0f, 1.0f, (float) Math.sqrt(2.0));
        Assertions.assertTrue(plane.transform(transform).isCongruentTo(res));
    }

    @Test
    public void testTransform4x4() {
        var transform = Matrix4.translation(0.0f, 0.0f, 1.0f).multiply(Matrix4.rotation(Math.PI / 2.0, 0.0, Math.PI / 4.0));
        var plane = new Plane(1.0f, 1.0f, 0.0f, 1.0f);
        var res = new Plane(0.0f, 0.0f, 1.0f, (float) (Math.sqrt(2.0) + 1.0));
        Assertions.assertTrue(plane.transform(transform).isCongruentTo(res));
    }

    @Test
    public void testInverseTransform3x3() {
        var transform = Matrix3.rotation(Math.PI / 2.0, 0.0, Math.PI / 4.0);
        var plane = new Plane(1.0f, 1.0f, 0.0f, 1.0f);
        var res = new Plane(0.5f, -0.5f, (float) (-Math.sqrt(2.0) / 2.0f), (float) Math.sqrt(2.0));
        Assertions.assertTrue(plane.inverseTransform(transform).isCongruentTo(res));
    }

    @Test
    public void testInverseTransform4x4() {
        var transform = Matrix4.translation(0.0f, 1.0f, 0.0f).multiply(Matrix4.rotation(Math.PI / 2.0, 0.0, Math.PI / 4.0));
        var plane = new Plane(1.0f, 1.0f, 0.0f, 1.0f);
        var res = new Plane(0.5f, -0.5f, (float) (-Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 2.0));
        Assertions.assertTrue(plane.inverseTransform(transform).isCongruentTo(res));
    }

    @Test
    public void testEqualsApprox() {
        var plane1 = new Plane(1.0f, 2.0f, 3.0f, 4.0f);
        var plane2 = new Plane(0.9999999f, 1.9999999f, 3.0000001f, 4.0000001f);
        var plane3 = plane1.flip();
        Assertions.assertNotEquals(plane1, plane2);
        Assertions.assertTrue(plane1.equalsApprox(plane2));
        Assertions.assertTrue(plane3.isCongruentTo(plane1));
        Assertions.assertFalse(plane3.equalsApprox(plane1));
    }
}
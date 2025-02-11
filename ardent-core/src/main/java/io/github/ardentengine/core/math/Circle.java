package io.github.ardentengine.core.math;

/**
 * A 2D circle, defined by a center and a radius.
 * <p>
 *     The radius of a circle is expected to be positive.
 *     Many methods in this class will return incorrect results if the radius is negative.
 * </p>
 * <p>
 *     The 3D equivalent is a {@link Sphere}.
 * </p>
 *
 * @param center The center of the circle.
 * @param radius The radius of the circle. Should be positive.
 */
public record Circle(Vector2 center, float radius) {

    /**
     * Constructs a circle with the given center and radius.
     *
     * @param x The x coordinate of the center of the circle.
     * @param y The y coordinate of the center of the circle.
     * @param radius The radius of the circle. Should be positive.
     */
    public Circle(float x, float y, float radius) {
        this(new Vector2(x, y), radius);
    }

    /**
     * Constructs an empty circle centered in the origin with radius {@code 0}.
     */
    public Circle() {
        this(0.0f, 0.0f, 0.0f);
    }

    /**
     * Constructs a circle with the given center and the given point.
     * The radius of the circle will be the distance from the center to the given point.
     *
     * @param center The center of the circle.
     * @param point A point on the circumference.
     */
    public Circle(Vector2 center, Vector2 point) {
        this(center, center.distanceTo(point));
    }

    /**
     * Constructs a circle with the given center and the given point.
     * The radius of the circle will be the distance from the center to the given point.
     *
     * @param x The x coordinate of the center of the circle.
     * @param y The y coordinate of the center of the circle.
     * @param point A point on the circumference.
     */
    public Circle(float x, float y, Vector2 point) {
        this(x, y, point.distanceTo(x, y));
    }

    /**
     * Constructs a circle with the given center and the given point.
     * The radius of the circle will be the distance from the center to the given point.
     *
     * @param center The center of the circle.
     * @param x The x coordinate of a point on the circumference.
     * @param y The y coordinate of a point on the circumference.
     */
    public Circle(Vector2 center, float x, float y) {
        this(center, center.distanceTo(x, y));
    }

    /**
     * Private constructor used as a helper to create a circle given three points.
     *
     * @param d D value.
     * @param p1 First point.
     * @param l1 Length squared of the first point.
     * @param p2 Second point.
     * @param l2 Length squared of the second point.
     * @param p3 Third point.
     * @param l3 Length squared of the third point.
     */
    private Circle(float d, Vector2 p1, float l1, Vector2 p2, float l2, Vector2 p3, float l3) {
        this(
            d * (l1 * (p2.y() - p3.y()) + l2 * (p3.y() - p1.y()) + l3 * (p1.y() - p2.y())),
            d * (l1 * (p3.x() - p2.x()) + l2 * (p1.x() - p3.x()) + l3 * (p2.x() - p1.x())),
            p1
        );
    }

    /**
     * Constructs a circle passing through the three given points.
     * <p>
     *     The result is undefined if the given points lie on the same line.
     * </p>
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Circle(Vector2 p1, Vector2 p2, Vector2 p3) {
        this(0.5f / (p1.x() * (p2.y() - p3.y()) + p2.x() * (p3.y() - p1.y()) + p3.x() * (p1.y() - p2.y())), p1, p1.lengthSquared(), p2, p2.lengthSquared(), p3, p3.lengthSquared());
    }

    /**
     * Returns the x coordinate of the circle's center.
     *
     * @return The x coordinate of the circle's center.
     */
    public float x() {
        return this.center().x();
    }

    /**
     * Returns the y coordinate of the circle's center.
     *
     * @return The y coordinate of the circle's center.
     */
    public float y() {
        return this.center().y();
    }

    /**
     * Returns the diameter of the circle.
     *
     * @return The diameter of the circle.
     */
    public float diameter() {
        return this.radius() * 2.0f;
    }

    /**
     * Returns the length of the circumference of the circle.
     *
     * @return The length of the circumference of the circle.
     */
    public float circumference() {
        return (float) (this.diameter() * Math.PI);
    }

    /**
     * Returns the squared radius of this circle.
     *
     * @return The squared radius of this circle.
     */
    public float radiusSquared() {
        return this.radius() * this.radius();
    }

    /**
     * Returns the area of this circle.
     *
     * @return The area of this circle.
     */
    public float area() {
        return (float) (this.radiusSquared() * Math.PI);
    }

    /**
     * Checks if this circle contains the point at the given coordinates.
     * Points laying on the circumference are not considered to be inside the circle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return True if the point at the given coordinates is inside this circle, otherwise false.
     */
    public boolean containsPoint(float x, float y) {
        return this.center().distanceSquaredTo(x, y) < this.radiusSquared();
    }

    /**
     * Checks if this circle contains the given point.
     * Points laying on the circumference are not considered to be inside the circle.
     *
     * @param point The point.
     * @return True if the given point is inside this circle, otherwise false.
     */
    public boolean containsPoint(Vector2 point) {
        return this.containsPoint(point.x(), point.y());
    }

    /**
     * Checks if this circle contains the point at the given coordinates.
     * If {@code includeCircumference} is true, points laying on the circumference will be considered to be inside the circle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param includeCircumference If true, points laying on the circumference will be considered to be inside the circle.
     * @return True if the point at the given coordinates is inside this circle, otherwise false.
     */
    public boolean containsPoint(float x, float y, boolean includeCircumference) {
        return includeCircumference ? this.center().distanceSquaredTo(x, y) <= this.radiusSquared() : this.containsPoint(x, y);
    }

    /**
     * Checks if this circle contains the given point.
     * If {@code includeCircumference} is true, points laying on the circumference will be considered inside the circle.
     *
     * @param point The point.
     * @param includeCircumference If true, points laying on the circumference will be considered inside the circle.
     * @return True if the given point is inside this circle, otherwise false.
     */
    public boolean containsPoint(Vector2 point, boolean includeCircumference) {
        return this.containsPoint(point.x(), point.y(), includeCircumference);
    }

    /**
     * Checks if the point at the given coordinates lies on the circumference of this circle by checking if its distance from the center is approximately equal to the radius.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return True if the point at the given coordinates lies on the circumference of this circle, otherwise false.
     */
    public boolean isPointOnCircumference(float x, float y) {
        return MathUtils.equalsApprox(this.center().distanceSquaredTo(x, y), this.radiusSquared());
    }

    /**
     * Checks if the given point lies on the circumference of this circle by checking if its distance from the center is approximately equal to the radius.
     *
     * @param point The point.
     * @return True if the given point lies on the circumference of this circle, otherwise false.
     */
    public boolean isPointOnCircumference(Vector2 point) {
        return this.isPointOnCircumference(point.x(), point.y());
    }

    /**
     * Checks if this circle intersects the given one.
     * Circles touching each other by a single point are not considered to be intersecting.
     *
     * @param circle The other circle.
     * @return True if this circle intersects the given one, otherwise false.
     */
    public boolean intersects(Circle circle) {
        return this.center().distanceTo(circle.center()) < this.radius() + circle.radius();
    }

    /**
     * Checks if this circle intersects the given one.
     * If {@code includeCircumference} is true, circles touching each other by a single point will be considered to be intersecting.
     *
     * @param circle The other circle.
     * @param includeCircumference If true, circles touching each other by a single point will be considered to be intersecting.
     * @return True if this circle intersects the given one, otherwise false.
     */
    public boolean intersects(Circle circle, boolean includeCircumference) {
        return includeCircumference ? this.center().distanceTo(circle.center()) <= this.radius() + circle.radius() : this.intersects(circle);
    }

    /**
     * Checks if this circle completely encloses the given one.
     *
     * @param circle The other circle.
     * @return True if this circle completely encloses the given one, otherwise false.
     */
    public boolean encloses(Circle circle) {
        return this.containsPoint(circle.x(), circle.y()) && this.radius() - this.center().distanceTo(circle.center()) >= circle.radius();
    }

    /**
     * Returns a circle that encloses both this one and the given one.
     *
     * @param circle The other circle.
     * @return A circle that encloses both this one and the given one.
     */
    public Circle merge(Circle circle) {
        return new Circle(this.center().plus(circle.center()).divide(2.0f), (this.center().distanceTo(circle.center()) + this.radius() + circle.radius()) / 2.0f);
    }

    /**
     * Returns a rectangle that completely encloses this circle.
     *
     * @return The bounding rectangle of this circle.
     */
    public Rect2 boundingRect() {
        return new Rect2(this.x() - this.radius(), this.y() - this.radius(), this.diameter(), this.diameter());
    }

    /**
     * Checks if this circle is approximately equal to the given one by checking if positions and radii are approximately equal using an internal epsilon.
     *
     * @param circle The other circle.
     * @return True if this circle is approximately equal to the given one, otherwise false.
     */
    public boolean equalsApprox(Circle circle) {
        return this.center().equalsApprox(circle.center()) && MathUtils.equalsApprox(this.radius(), circle.radius());
    }

    @Override
    public String toString() {
        return "Circle[x=" + this.x() + ", y=" + this.y() + ", r=" + this.radius() + "]";
    }
}
package io.github.ardentengine.core.math;

/**
 * A 3D sphere, defined by a center and a radius.
 * <p>
 *     The radius of a sphere is expected to be positive.
 *     Many methods in this class will return incorrect results if the radius is negative.
 * </p>
 * <p>
 *     The 2D equivalent is a {@link Circle}.
 * </p>
 *
 * @param center The center of the sphere.
 * @param radius The radius of the sphere. Should be positive.
 */
public record Sphere(Vector3 center, float radius) {

    /**
     * Constructs a sphere with the given center and radius.
     *
     * @param x The x coordinate of the center of the sphere.
     * @param y The y coordinate of the center of the sphere.
     * @param z The z coordinate of the center of the sphere.
     * @param radius The radius of the sphere. Should be positive.
     */
    public Sphere(float x, float y, float z, float radius) {
        this(new Vector3(x, y, z), radius);
    }

    /**
     * Constructs an empty sphere centered in the origin with radius {@code 0}.
     */
    public Sphere() {
        this(Vector3.ZERO, 0.0f);
    }

    /**
     * Constructs a sphere with the given center and the given point.
     * The radius of the sphere will be the distance from the center to the given point.
     *
     * @param center The center of the sphere.
     * @param point A point on the surface of the sphere.
     */
    public Sphere(Vector3 center, Vector3 point) {
        this(center, center.distanceTo(point));
    }

    /**
     * Constructs a sphere with the given center and the given point.
     * The radius of the sphere will be the distance from the center to the given point.
     *
     * @param x The x coordinate of the center of the sphere.
     * @param y The y coordinate of the center of the sphere.
     * @param z The z coordinate of the center of the sphere.
     * @param point A point on the surface of the sphere.
     */
    public Sphere(float x, float y, float z, Vector3 point) {
        this(x, y, z, point.distanceTo(x, y, z));
    }

    /**
     * Constructs a sphere with the given center and the given point.
     * The radius of the sphere will be the distance from the center to the given point.
     *
     * @param center The center of the sphere.
     * @param x The x coordinate of a point on the surface of the sphere.
     * @param y The y coordinate of a point on the surface of the sphere.
     * @param z The z coordinate of a point on the surface of the sphere
     */
    public Sphere(Vector3 center, float x, float y, float z) {
        this(center, center.distanceTo(x, y, z));
    }

    /**
     * Returns the x coordinate of the sphere's center.
     *
     * @return The x coordinate of the sphere's center.
     */
    public float x() {
        return this.center().x();
    }

    /**
     * Returns the y coordinate of the sphere's center.
     *
     * @return The y coordinate of the sphere's center.
     */
    public float y() {
        return this.center().y();
    }

    /**
     * Returns the z coordinate of the sphere's center.
     *
     * @return The z coordinate of the sphere's center.
     */
    public float z() {
        return this.center().z();
    }

    /**
     * Returns the diameter of the sphere.
     *
     * @return The diameter of the sphere.
     */
    public float diameter() {
        return this.radius() * 2.0f;
    }

    /**
     * Returns the squared radius of the sphere.
     *
     * @return The squared radius of the sphere.
     */
    public float radiusSquared() {
        return this.radius() * this.radius();
    }

    /**
     * Returns the surface area of the sphere.
     *
     * @return The surface area of the sphere.
     */
    public float surfaceArea() {
        return (float) (4.0 * Math.PI * this.radiusSquared());
    }

    /**
     * Returns the cubed radius of the sphere.
     *
     * @return The cubed radius of the sphere.
     */
    public float radiusCubed() {
        return this.radiusSquared() * this.radius();
    }

    /**
     * Returns the volume of the sphere.
     *
     * @return The volume of the sphere.
     */
    public float volume() {
        return (float) (4.0 / 3.0 * Math.PI * this.radiusCubed());
    }

    /**
     * Checks if this sphere contains the point at the given coordinates.
     * Points laying on the surface of the sphere are not considered to be inside the sphere.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates is inside this sphere, otherwise false.
     */
    public boolean containsPoint(float x, float y, float z) {
        return this.center().distanceSquaredTo(x, y, z) < this.radiusSquared();
    }

    /**
     * Checks if this sphere contains the point at the given coordinates.
     * If {@code includeSurface} is true, points laying on the surface of the sphere will be considered to be inside the sphere.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @param includeSurface If true, points laying on the surface of the sphere will be considered to be inside the sphere.
     * @return True if the point at the given coordinates is inside this sphere, otherwise false.
     */
    public boolean containsPoint(float x, float y, float z, boolean includeSurface) {
        return includeSurface ? this.center().distanceSquaredTo(x, y, z) <= this.radiusSquared() : this.containsPoint(x, y, z);
    }

    /**
     * Checks if this sphere contains the given point.
     * Points laying on the surface of the sphere are not considered to be inside the sphere.
     *
     * @param point The point.
     * @return True if the given point is inside this sphere, otherwise false.
     */
    public boolean containsPoint(Vector3 point) {
        return this.containsPoint(point.x(), point.y(), point.z());
    }

    /**
     * Checks if this sphere contains the given point.
     * If {@code includeSurface} is true, points laying on the surface of the sphere will be considered to be inside the sphere.
     *
     * @param point The point.
     * @param includeSurface If true, points laying on the surface of the sphere will be considered to be inside the sphere.
     * @return True if the given point is inside this sphere, otherwise false.
     */
    public boolean containsPoint(Vector3 point, boolean includeSurface) {
        return this.containsPoint(point.x(), point.y(), point.z(), includeSurface);
    }

    /**
     * Checks if the point at the given coordinates is on the surface of the sphere by checking if its distance from the center is approximately equal to the radius.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates is on the surface of the sphere, otherwise false.
     */
    public boolean isPointOnSurface(float x, float y, float z) {
        return MathUtils.equalsApprox(this.center().distanceSquaredTo(x, y, z), this.radiusSquared());
    }

    /**
     * Checks if the given point is on the surface of the sphere by checking if its distance from the center is approximately equal to the radius.
     *
     * @param point The point.
     * @return True if the given point is on the surface of the sphere, otherwise false.
     */
    public boolean isPointOnSurface(Vector3 point) {
        return this.isPointOnSurface(point.x(), point.y(), point.z());
    }

    /**
     * Checks if this sphere intersects the given one.
     * Spheres touching each other by a single point are not considered to be intersecting.
     *
     * @param sphere The other sphere.
     * @return True if this sphere intersects the given one, otherwise false.
     */
    public boolean intersects(Sphere sphere) {
        return this.center().distanceTo(sphere.center()) < this.radius() + sphere.radius();
    }

    /**
     * Checks if this sphere intersects the given one.
     * If {@code includeSurface} is true, spheres touching each other by a single point will be considered to be intersecting.
     *
     * @param sphere The other sphere.
     * @param includeSurface If true, spheres touching each other by a single point will be considered to be intersecting.
     * @return True if this sphere intersects the given one, otherwise false.
     */
    public boolean intersects(Sphere sphere, boolean includeSurface) {
        return includeSurface ? this.center().distanceTo(sphere.center()) <= this.radius() + sphere.radius() : this.intersects(sphere);
    }

    /**
     * Checks if this sphere completely encloses the given one.
     *
     * @param sphere The other sphere.
     * @return True if this sphere completely encloses the given one, otherwise false.
     */
    public boolean encloses(Sphere sphere) {
        return this.containsPoint(sphere.center()) && this.radius() - this.center().distanceTo(sphere.center()) >= sphere.radius();
    }

    /**
     * Returns a sphere that encloses both this one and the given one.
     *
     * @param sphere The other sphere.
     * @return A sphere that encloses both this one and the given one.
     */
    public Sphere merge(Sphere sphere) {
        return new Sphere(this.center().plus(sphere.center()).divide(2.0f), (this.center().distanceTo(sphere.center()) + this.radius() + sphere.radius()) / 2.0f);
    }

    /**
     * Returns an axis-aligned bounding box that completely encloses this sphere.
     *
     * @return The bounding box of this sphere.
     */
    public AABB boundingBox() {
        return new AABB(this.x() - this.radius(), this.y() - this.radius(), this.z() - this.radius(), this.diameter(), this.diameter(), this.diameter());
    }

    /**
     * Checks if this sphere is approximately equal to the given one by checking if potions and radii are approximately equal using an internal epsilon.
     *
     * @param sphere The other sphere.
     * @return True if this sphere is approximately equal to the given one, otherwise false.
     */
    public boolean equalsApprox(Sphere sphere) {
        return this.center().equalsApprox(sphere.center()) && MathUtils.equalsApprox(this.radius(), sphere.radius());
    }

    @Override
    public String toString() {
        return "Sphere[x=" + this.x() + ", y=" + this.y() + ", z=" + this.z() + ", r=" + this.radius() + "]";
    }
}
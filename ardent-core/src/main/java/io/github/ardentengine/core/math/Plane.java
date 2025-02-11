package io.github.ardentengine.core.math;

/**
 * A plane defined by a normal vector and its distance from the origin.
 * <p>
 *     The equation of a plane is {@code ax + by + cz = d}, where the vector {@code (a, b, c)} is the normal of the plane and {@code d} is the distance from the origin to the plane.
 * </p>
 * <p>
 *     The normal vector is not required to be a unit vector.
 *     The plane's normal should not be the {@link Vector3#ZERO} vector.
 * </p>
 * <p>
 *     The direction of the plane's normal determines which side of the plane is "above" and which side is "below".
 * </p>
 *
 * @param normal The normal of the plane. Not required to be a unit vector. Should not be the zero vector.
 * @param d The distance from the origin to the plane.
 */
public record Plane(Vector3 normal, float d) {

    /**
     * Constructs a plane with equation {@code ax + by + cz = d}.
     *
     * @param a The x component of the plane's normal.
     * @param b The y component of the plane's normal.
     * @param c The z component of the plane's normal.
     * @param d The distance from the origin to the plane.
     */
    public Plane(float a, float b, float c, float d) {
        this(new Vector3(a, b, c), d);
    }

    /**
     * Constructs a plane passing through the given point with the given normal.
     *
     * @param normal The normal of the plane. Not required to be a unit vector. Should not be the zero vector.
     * @param point A point the plane is passing through.
     */
    public Plane(Vector3 normal, Vector3 point) {
        this(normal, normal.dot(point));
    }

    /**
     * Constructs a plane passing through the three given points.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Vector3 p1, Vector3 p2, Vector3 p3) {
        this(p1.minus(p2).cross(p1.minus(p3)), p1);
    }

    /**
     * Returns the x component of the plane's normal vector.
     * Usually denoted as {@code a} in the plane's equation.
     *
     * @return The x component of the plane's normal vector.
     */
    public float a() {
        return this.normal().x();
    }

    /**
     * Returns the y component of the plane's normal vector.
     * Usually denoted as {@code b} in the plane's equation.
     *
     * @return The y component of the plane's normal vector.
     */
    public float b() {
        return this.normal().y();
    }

    /**
     * Returns the z component of the plane's normal vector.
     * Usually denoted as {@code c} in the plane's equation.
     *
     * @return The z component of the plane's normal vector.
     */
    public float c() {
        return this.normal.z();
    }

    /**
     * Returns the signed distance from the plane to the point at the given coordinates.
     * <p>
     *     If the point is "above" the plane, the distance will be positive.
     *     If "below", the distance will be negative.
     *     Which side of the plane is above and which is below is determined by the direction of its normal.
     * </p>
     *
     * @param x The x coordinate of the point.
     * @param y The z coordinate of the point.
     * @param z The z coordinate of the point.
     * @return The signed distance from the plane to the point at the given coordinates.
     */
    public float signedDistanceTo(float x, float y, float z) {
        return (this.normal().dot(x, y, z) + this.d()) / this.normal().length();
    }

    /**
     * Returns the signed distance from the plane to the given point.
     * <p>
     *     If the point is "above" the plane, the distance will be positive.
     *     If "below", the distance will be negative.
     *     Which side of the plane is above and which is below is determined by the direction of its normal.
     * </p>
     *
     * @param point The point.
     * @return The signed distance from the plane to the given point.
     */
    public float signedDistanceTo(Vector3 point) {
        return this.signedDistanceTo(point.x(), point.y(), point.z());
    }

    /**
     * Returns the distance from the plane to the point at the given coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The z coordinate of the point.
     * @param z The z coordinate of the point.
     * @return The distance from the plane to the point at the given coordinates.
     */
    public float distanceTo(float x, float y, float z) {
        return Math.abs(this.signedDistanceTo(x, y, z));
    }

    /**
     * Returns the distance from the plane to the given point.
     *
     * @param point The point.
     * @return The distance from the plane to the given point.
     */
    public float distanceTo(Vector3 point) {
        return this.distanceTo(point.x(), point.y(), point.z());
    }

    /**
     * Flips the direction of this plane's normal and its distance value and returns the result.
     * The resulting plane is congruent to the original one, but faces the opposite direction.
     *
     * @return A plane that is congruent to the original one, but faces the opposite direction.
     */
    public Plane flip() {
        return new Plane(this.normal().negated(), -this.d());
    }

    /**
     * Checks if the point at the given coordinates belongs to this plane.
     *
     * @param x The x coordinate of the point.
     * @param y The z coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates belongs to this plane, otherwise false.
     */
    public boolean containsPoint(float x, float y, float z) {
        return MathUtils.equalsApprox(this.normal().dot(x, y, z), this.d());
    }

    /**
     * Checks if the given point belongs to this plane.
     *
     * @param point The point.
     * @return True if the given point belongs to this plane, otherwise false.
     */
    public boolean containsPoint(Vector3 point) {
        return this.containsPoint(point.x(), point.y(), point.z());
    }

    /**
     * Checks if this plane is parallel to the given one.
     * <p>
     *     Congruent planes are parallel.
     * </p>
     *
     * @param plane The second plane.
     * @return True if this plane is parallel to the given one, otherwise false.
     * @see Plane#isCongruentTo(Plane)
     */
    public boolean isParallelTo(Plane plane) {
        return MathUtils.isZeroApprox(this.normal().cross(plane.normal()).lengthSquared());
    }

    /**
     * Checks if this plane is congruent to the given one.
     *
     * @param plane The second plane.
     * @return True if this plane is congruent to the given one, otherwise false.
     * @see Plane#isParallelTo(Plane)
     */
    public boolean isCongruentTo(Plane plane) {
        return this.isParallelTo(plane) && MathUtils.equalsApprox(Math.abs(this.d() / this.normal().length()), Math.abs(plane.d() / plane.normal().length()));
    }

    /**
     * Checks if this plane intersects the given one.
     *
     * @param plane The second plane.
     * @return True if this plane intersects the given one, otherwise false.
     */
    public boolean intersects(Plane plane) {
        return !this.isParallelTo(plane) || MathUtils.equalsApprox(this.d() / this.normal().length(), plane.d() / plane.normal().length());
    }

    /**
     * Checks if this plane and the given ones intersect in a single point.
     * <p>
     *     Can be used to check the validity of the result of {@link Plane#intersection(Plane, Plane)}.
     * </p>
     *
     * @param plane1 The second plane.
     * @param plane2 The third plane.
     * @return True if the three planes intersect in a single point, otherwise false.
     */
    public boolean intersectsInPoint(Plane plane1, Plane plane2) {
        return !MathUtils.isZeroApprox(this.normal().cross(plane1.normal()).dot(plane2.normal()));
    }

    /**
     * Returns the point of intersection between this plane and the given ones.
     * <p>
     *     If all the three planes are parallel, all the components of the resulting vector will be {@code NaN}.
     *     If the intersection between the three planes is a line, the components of the resulting vector will be either {@code NaN} or {@code Infinity}.
     *     The validity of the result can be checked using {@link Plane#intersectsInPoint(Plane, Plane)}.
     * </p>
     *
     * @param plane1 The second plane.
     * @param plane2 The third plane.
     * @return The point of intersection between the three planes.
     */
    public Vector3 intersection(Plane plane1, Plane plane2) {
        var c1 = plane1.normal().cross(plane2.normal());
        var c2 = plane2.normal().cross(this.normal());
        var c3 = this.normal().cross(plane1.normal());
        return c1.plus(c2).plus(c3).divide(c3.dot(plane2.normal()));
    }

    /**
     * Returns the projection of the point at the given coordinates onto this plane.
     *
     * @param x X coordinate of the point to project.
     * @param y Y coordinate of the point to project.
     * @param z Z coordinate of the point to project.
     * @return The projection of the given point onto this plane.
     */
    public Vector3 project(float x, float y, float z) {
        var distance = this.signedDistanceTo(x, y, z);
        return new Vector3((x - this.a()) * distance, (y - this.b()) * distance, (z - this.c()) * distance);
    }

    /**
     * Returns the projection of the given point onto this plane.
     *
     * @param point The point to project.
     * @return The projection of the given point onto this plane.
     */
    public Vector3 project(Vector3 point) {
        return this.project(point.x(), point.y(), point.z());
    }

    /**
     * Checks if the point at the given coordinates is "above" this plane.
     * Which side of the plane is the "above" side is determined by the direction of the plane's normal.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates is "above" this plane, otherwise false.
     */
    public boolean isPointOver(float x, float y, float z) {
        return this.normal().dot(x, y, z) > this.d();
    }

    /**
     * Checks if the given point is "above" this plane.
     * Which side of the plane is the "above" side is determined by the direction of the plane's normal.
     *
     * @param point The point.
     * @return True if the given point is "above" this plane, otherwise false.
     */
    public boolean isPointOver(Vector3 point) {
        return this.isPointOver(point.x(), point.y(), point.z());
    }

    /**
     * Checks if the point at the given coordinates is "below" this plane.
     * Which side of the plane is the "below" side is determined by the direction of the plane's normal.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates is "below" this plane, otherwise false.
     */
    public boolean isPointUnder(float x, float y, float z) {
        return this.normal().dot(x, y, z) < this.d();
    }

    /**
     * Checks if the given point is "below" this plane.
     * Which side of the plane is the "below" side is determined by the direction of the plane's normal.
     *
     * @param point The point.
     * @return True if the given point is "below" this plane, otherwise false.
     */
    public boolean isPointUnder(Vector3 point) {
        return this.isPointUnder(point.x(), point.y(), point.z());
    }

    /**
     * Transforms this plane by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param m A 3x3 transformation matrix.
     * @return The resulting plane.
     */
    public Plane transform(Matrix3 m) {
        return new Plane(m.inverse().transposed().multiply(this.normal()), m.multiply(this.normal().multiply(this.d())));
    }

    // TODO: Transform with 3x4

    /**
     * Transforms this plane by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param m A 4x4 transformation matrix.
     * @return The resulting plane.
     */
    public Plane transform(Matrix4 m) {
        return new Plane(m.inverse().transposed().multiply(this.normal(), 1.0f).xyz(), m.multiply(this.normal().multiply(this.d()), 1.0f).xyz());
    }

    /**
     * Inversely transforms this plane by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link Plane#transform(Matrix3)} with the {@link Matrix3#inverse()} of the matrix.
     * </p>
     *
     * @param m A 3x3 transformation matrix.
     * @return The resulting plane.
     */
    public Plane inverseTransform(Matrix3 m) {
        return new Plane(m.transposed().multiply(this.normal()), m.inverse().multiply(this.normal().multiply(this.d())));
    }

    /**
     * Inversely transforms this plane by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link Plane#transform(Matrix4)} with the {@link Matrix4#inverse()} of the matrix.
     * </p>
     *
     * @param m A 4x4 transformation matrix.
     * @return The resulting plane.
     */
    public Plane inverseTransform(Matrix4 m) {
        return new Plane(m.transposed().multiply(this.normal(), 1.0f).xyz(), m.inverse().multiply(this.normal().multiply(this.d()), 1.0f).xyz());
    }

    /**
     * Checks if this plane is approximately equal to the given one using an internal epsilon.
     * <p>
     *     This method checks if the normals and the distances {@code d} are approximately equal, therefore it may still return false for congruent planes with a different equation.
     *     Use {@link Plane#isCongruentTo(Plane)} to check if two planes are congruent.
     * </p>
     *
     * @param plane The second plane.
     * @return True if this plane is approximately equal to the given one, otherwise false.
     */
    public boolean equalsApprox(Plane plane) {
        return this.normal().equalsApprox(plane.normal()) && MathUtils.equalsApprox(this.d(), plane.d());
    }

    @Override
    public String toString() {
        return "Plane[a=" + this.a() + ", b=" + this.b() + ", c=" + this.c() + ", d=" + this.d() + "]";
    }
}
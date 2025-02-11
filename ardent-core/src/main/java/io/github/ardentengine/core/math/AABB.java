package io.github.ardentengine.core.math;

/**
 * A 3D axis-aligned bounding box, defined by a {@link AABB#position()} and a {@link AABB#size()}.
 * <p>
 *     Negative values for the size are allowed.
 *     The size is considered to be the vector from the origin of the AABB to its {@link AABB#end()} point.
 * </p>
 * <p>
 *     The 2D counterpart is {@link Rect2}.
 * </p>
 *
 * @param x The x coordinate of the position of the AABB.
 * @param y The y coordinate of the position of the AABB.
 * @param z The z coordinate of the position of the AABB.
 * @param width The size of the AABB on the x axis.
 * @param height The size of the AABB on the y axis.
 * @param depth The size of the AABB on the z axis.
 */
public record AABB(float x, float y, float z, float width, float height, float depth) {

    /**
     * Constructs an AABB at the given position with the given size.
     *
     * @param position The position of the AABB.
     * @param size The size of the AABB.
     */
    public AABB(Vector3 position, Vector3 size) {
        this(position.x(), position.y(), position.z(), size.x(), size.y(), size.z());
    }

    /**
     * Constructs an AABB at the given position with the given width, height, and depth.
     *
     * @param position The position of the AABB.
     * @param width The size of the AABB on the x axis.
     * @param height The size of the AABB on the y axis.
     * @param depth The size of the AABB on the z axis.
     */
    public AABB(Vector3 position, float width, float height, float depth) {
        this(position.x(), position.y(), position.z(), width, height, depth);
    }

    /**
     * Constructs an AABB at the given x, y, and z coordinates with the given size.
     *
     * @param x The x coordinate of the position of the AABB.
     * @param y The y coordinate of the position of the AABB.
     * @param z The z coordinate of the position of the AABB.
     * @param size The size of the AABB.
     */
    public AABB(float x, float y, float z, Vector3 size) {
        this(x, y, z, size.x(), size.y(), size.z());
    }

    /**
     * Constructs an empty AABB, whose position and size are both {@link Vector3#ZERO}.
     */
    public AABB() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Returns the position of this AABB.
     *
     * @return The origin position of this AABB.
     */
    public Vector3 position() {
        return new Vector3(this.x(), this.y(), this.z());
    }

    /**
     * Returns the size of this AABB.
     * Can be negative.
     * <p>
     *     The size of the AABB is the vector from its origin {@link AABB#position()} to its {@link AABB#end()}.
     * </p>
     *
     * @return The size of this AABB.
     */
    public Vector3 size() {
        return new Vector3(this.width(), this.height(), this.depth());
    }

    /**
     * Returns the end point of this AABB.
     * <p>
     *     Equivalent to the AABB's {@link AABB#position()} plus its {@link AABB#size()}.
     * </p>
     *
     * @return The end point of this AABB.
     */
    public Vector3 end() {
        return new Vector3(this.x() + this.width(), this.y() + this.height(), this.z() + this.depth());
    }

    /**
     * Returns the center point of this AABB.
     * <p>
     *     Equivalent to the AABB's {@link AABB#position()} plus half its {@link AABB#size()}.
     * </p>
     *
     * @return The center point of this AABB.
     */
    public Vector3 center() {
        return new Vector3(this.x() + this.width() / 2.0f, this.y() + this.height() / 2.0f, this.z() + this.depth() / 2.0f);
    }

    /**
     * Returns the volume of this AABB.
     *
     * @return The volume of this AABB.
     */
    public float volume() {
        return this.width() * this.height() * this.depth();
    }

    /**
     * Returns the x coordinate of the leftmost point of this AABB.
     * This method is guaranteed to return the left extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the leftmost point of this AABB.
     * @see AABB#leftPlane()
     */
    public float left() {
        return Math.min(this.x(), this.x() + this.width());
    }

    /**
     * Returns the plane passing through the left face of this AABB, with its normal pointing left.
     *
     * @return The plane passing through the left face of this AABB.
     * @see AABB#left()
     */
    public Plane leftPlane() {
        return new Plane(-1.0f, 0.0f, 0.0f, this.left());
    }

    /**
     * Returns the x coordinate of the rightmost point of this AABB.
     * This method is guaranteed to return the right extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the rightmost point of this AABB.
     * @see AABB#rightPlane()
     */
    public float right() {
        return Math.max(this.x(), this.x() + this.width());
    }

    /**
     * Returns the plane passing through the right face of this AABB, with its normal pointing right.
     *
     * @return The plane passing through the right face of this AABB.
     * @see AABB#right()
     */
    public Plane rightPlane() {
        return new Plane(1.0f, 0.0f, 0.0f, this.right());
    }

    /**
     * Returns the y coordinate of the highest point of this AABB.
     * This method is guaranteed to return the top extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the highest point of this AABB.
     * @see AABB#topPlane()
     */
    public float top() {
        return Math.max(this.y(), this.y() + this.height());
    }

    /**
     * Returns the plane passing through the top face of this AABB, with its normal pointing up.
     *
     * @return The plane passing through the top face of this AABB.
     * @see AABB#top()
     */
    public Plane topPlane() {
        return new Plane(0.0f, 1.0f, 0.0f, this.top());
    }

    /**
     * Returns the y coordinate of the lowest point of this AABB.
     * This method is guaranteed to return the bottom extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the lowest point of this AABB.
     * @see AABB#bottomPlane()
     */
    public float bottom() {
        return Math.min(this.y(), this.y() + this.height());
    }

    /**
     * Returns the plane passing through the bottom face of this AABB, with its normal pointing down.
     *
     * @return The plane passing through the bottom face of this AABB.
     * @see AABB#bottom()
     */
    public Plane bottomPlane() {
        return new Plane(0.0f, -1.0f, 0.0f, this.bottom());
    }

    /**
     * Returns the z coordinate of the foremost point of this AABB.
     * This method is guaranteed to return the front extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the foremost point of this AABB.
     * @see AABB#frontPlane()
     */
    public float front() {
        return Math.min(this.z(), this.z() + this.depth());
    }

    /**
     * Returns the plane passing through the front face of this AABB, with its normal pointing forward.
     *
     * @return The plane passing through the front face of this AABB.
     * @see AABB#front()
     */
    public Plane frontPlane() {
        return new Plane(0.0f, 0.0f, 1.0f, this.front());
    }

    /**
     * Returns the z coordinate of the hindmost point of this AABB.
     * This method is guaranteed to return the back extent of the AABB regardless of the sign of its {@link AABB#size()}.
     *
     * @return The x coordinate of the hindmost point of this AABB.
     * @see AABB#backPlane()
     */
    public float back() {
        return Math.max(this.z(), this.z() + this.depth());
    }

    /**
     * Returns the plane passing through the back face of this AABB, with its normal pointing backwards.
     *
     * @return The plane passing through the back face of this AABB.
     * @see AABB#back()
     */
    public Plane backPlane() {
        return new Plane(0.0f, 0.0f, -1.0f, this.back());
    }

    /**
     * Checks if this AABB contains the point at the given coordinates.
     * Points laying on the faces of the AABB are not considered to be inside the AABB.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return True if the point at the given coordinates is inside this AABB, otherwise false.
     */
    public boolean containsPoint(float x, float y, float z) {
        return x > this.left() && x < this.right() && y > this.bottom() && y < this.top() && z > this.front() && z < this.back();
    }

    /**
     * Checks if this AABB contains the given point.
     * Points laying on the faces of the AABB are not considered to be inside the AABB.
     *
     * @param point The point.
     * @return True if the given point is inside this AABB, otherwise false.
     */
    public boolean containsPoint(Vector3 point) {
        return this.containsPoint(point.x(), point.y(), point.z());
    }

    /**
     * Checks if this AABB contains the point at the given coordinates.
     * If {@code includeFaces} is true, points laying on the faces of the AABB will be considered inside the AABB.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @param includeFaces If true, points laying on the faces of the AABB will be considered inside the AABB.
     * @return True if the point at the given coordinates is inside this AABB, otherwise false.
     */
    public boolean containsPoint(float x, float y, float z, boolean includeFaces) {
        return includeFaces ? x >= this.left() && x <= this.right() && y >= this.bottom() && y <= this.top() && z >= this.front() && z <= this.back() : this.containsPoint(x, y, z);
    }

    /**
     * Checks if this AABB contains the given point.
     * If {@code includeFaces} is true, points laying on the faces of the AABB will be considered inside the AABB.
     *
     * @param point The point.
     * @param includeFaces If true, points laying on the faces of the AABB will be considered inside the AABB.
     * @return True if the given point is inside this AABB, otherwise false.
     */
    public boolean containsPoint(Vector3 point, boolean includeFaces) {
        return this.containsPoint(point.x(), point.y(), point.z(), includeFaces);
    }

    /**
     * Checks if this AABB overlaps with the given one.
     * The faces of both AABBs are excluded.
     *
     * @param aabb The other AABB.
     * @return True if this AABB overlaps with the given one, otherwise false.
     */
    public boolean intersects(AABB aabb) {
        return !(this.right() <= aabb.left() || this.left() >= aabb.right() || this.top() <= aabb.bottom() || this.bottom() >= aabb.top() || this.front() >= aabb.back() || this.back() <= aabb.front());
    }

    /**
     * Checks if this AABB overlaps with the given one.
     * If {@code includeFaces} is true, AABBs touching each other by a face are considered as overlapping.
     *
     * @param aabb The other AABB.
     * @param includeFaces If true, AABBs touching each other by a face are considered as overlapping.
     * @return True if this AABB overlaps with the given one, otherwise false.
     */
    public boolean intersects(AABB aabb, boolean includeFaces) {
        return includeFaces ? !(this.right() < aabb.left() || this.left() > aabb.right() || this.top() < aabb.bottom() || this.bottom() > aabb.top() || this.front() > aabb.back() || this.back() < aabb.front()) : this.intersects(aabb);
    }

    /**
     * Constructs an AABB using the two given points as the bounds of the AABB.
     * The given points will be two of the corners of the resulting AABB.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The newly instantiated AABB.
     */
    public static AABB fromPoints(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new AABB(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.abs(x1 - x2), Math.abs(y1 - y2), Math.abs(z1 - z2));
    }

    /**
     * Constructs an AABB using the two given points as the bounds of the AABB.
     * The given points will be two of the corners of the resulting AABB.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param p2 The second point.
     * @return The newly instantiated AABB.
     */
    public static AABB fromPoints(float x1, float y1, float z1, Vector3 p2) {
        return fromPoints(x1, y1, z1, p2.x(), p2.y(), p2.z());
    }

    /**
     * Constructs an AABB using the two given points as the bounds of the AABB.
     * The given points will be two of the corners of the resulting AABB.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param p1 The first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The newly instantiated AABB.
     */
    public static AABB fromPoints(Vector3 p1, float x2, float y2, float z2) {
        return fromPoints(p1.x(), p1.y(), p1.z(), x2, y2, z2);
    }

    /**
     * Constructs an AABB using the two given points as the bounds of the AABB.
     * The given points will be two of the corners of the resulting AABB.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @return The newly instantiated AABB.
     */
    public static AABB fromPoints(Vector3 p1, Vector3 p2) {
        return fromPoints(p1.x(), p1.y(), p1.z(), p2.x(), p2.y(), p2.z());
    }

    /**
     * Returns the intersection between this AABB and the given one or an empty AABB if the two do not intersect.
     * This method always returns an AABB with positive {@link AABB#size()}.
     *
     * @param aabb The other AABB.
     * @return The intersection between this AABB and the given one.
     */
    public AABB intersection(AABB aabb) {
        if(this.intersects(aabb, true)) {
            return fromPoints(
                Math.max(this.left(), aabb.left()), Math.max(this.bottom(), aabb.bottom()), Math.max(this.front(), aabb.front()),
                Math.min(this.right(), aabb.right()), Math.min(this.top(), aabb.top()), Math.min(this.back(), aabb.back())
            );
        }
        return new AABB();
    }

    /**
     * Checks if the given plane intersects this AABB.
     *
     * @param plane The plane.
     * @return True if the given plane intersects this AABB, otherwise false.
     */
    public boolean intersects(Plane plane) {
        var over = false;
        var under = false;
        for(var x = this.x(); x <= this.x() + this.width(); x += this.width()) {
            for(var y = this.y(); y <= this.y() + this.height(); y += this.height()) {
                for(var z = this.z(); z <= this.z() + this.depth(); z += this.depth()) {
                    if(plane.isPointOver(x, y, z)) {
                        over = true;
                    } else {
                        under = true;
                    }
                }
            }
        }
        return over && under;
    }

    /**
     * Returns an AABB equivalent to this one with non-negative {@link AABB#size()}.
     *
     * @return An AABB equivalent to this one with non-negative size.
     */
    public AABB abs() {
        return new AABB(this.left(), this.bottom(), this.front(), Math.abs(this.width()), Math.abs(this.height()), Math.abs(this.depth()));
    }

    /**
     * Checks if this AABB completely encloses the given one.
     *
     * @param aabb The other AABB.
     * @return True if this AABB completely encloses the given one, otherwise false.
     * @see AABB#merge(AABB)
     */
    public boolean encloses(AABB aabb) {
        return this.left() <= aabb.left() && this.right() >= aabb.right() && this.top() >= aabb.top() && this.bottom() <= aabb.bottom() && this.front() <= aabb.front() && this.back() >= aabb.back();
    }

    /**
     * Extends all the sides of this AABB by the given amount and returns the result.
     * A negative amount shrinks the AABB.
     * <p>
     *     If this size of this rectangle is negative on one of the axes, a positive amount will shrink it and a negative amount will grow it.
     * </p>
     *
     * @param amount Amount by which the AABB should grow.
     * @return A copy of this AABB with all sides extended by the given amount.
     */
    public AABB grow(float amount) {
        return new AABB(this.x() - amount, this.y() - amount, this.z() - amount, this.width() + amount * 2.0f, this.height() + amount * 2.0f, this.depth() + amount * 2.0f);
    }

    /**
     * Returns a copy of this AABB expanded to align its edges with the given point.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return A copy of this AABB expanded to align its edges with the given point.
     */
    public AABB expandTo(float x, float y, float z) {
        return fromPoints(
            Math.min(this.left(), x), Math.min(this.bottom(), y), Math.min(this.front(), z),
            Math.max(this.right(), x), Math.max(this.top(), y), Math.max(this.back(), z)
        );
    }

    /**
     * Returns a copy of this AABB expanded to align its edges with the given point.
     * <p>
     *     The resulting AABB is guaranteed to have a positive size.
     * </p>
     *
     * @param point The point.
     * @return A copy of this AABB expanded to align its edges with the given point.
     */
    public AABB expandTo(Vector3 point) {
        return this.expandTo(point.x(), point.y(), point.z());
    }

    /**
     * Returns an AABB that encloses both this one and the given one.
     *
     * @param aabb The other AABB.
     * @return An AABB that encloses both this one and the given one.
     * @see AABB#encloses(AABB)
     */
    public AABB merge(AABB aabb) {
        return fromPoints(
            Math.min(this.left(), aabb.left()), Math.min(this.bottom(), aabb.bottom()), Math.min(this.front(), aabb.front()),
            Math.max(this.right(), aabb.right()), Math.max(this.top(), aabb.top()), Math.max(this.back(), aabb.back())
        );
    }

    /**
     * Returns a sphere that completely encloses this AABB.
     * The center of the sphere corresponds to the {@link AABB#center()} of the AABB, its radius corresponds to the distance from the center to the corners of the AABB.
     *
     * @return A sphere that completely encloses this AABB.
     */
    public Sphere boundingSphere() {
        return new Sphere(this.center(), this.x(), this.y(), this.z());
    }

    /**
     * Transforms this AABB by multiplying it with the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 3x3 transformation matrix.
     * @return The resulting AABB.
     */
    public AABB transform(Matrix3 transform) {
        var px = transform.m00() * this.x() + transform.m01() * this.y() + transform.m02() * this.z();
        var py = transform.m10() * this.x() + transform.m11() * this.y() + transform.m12() * this.z();
        var pz = transform.m20() * this.x() + transform.m21() * this.y() + transform.m22() * this.z();
        return fromPoints(
            px, py, pz,
            px + transform.m00() * this.width() + transform.m10() * this.width() + transform.m20() * this.width(),
            py + transform.m01() * this.height() + transform.m11() * this.height() + transform.m21() * this.height(),
            pz + transform.m02() * this.depth() + transform.m12() * this.depth() + transform.m22() * this.depth()
        );
    }

    /**
     * Transforms this AABB by multiplying it with the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 3x4 transformation matrix.
     * @return The resulting AABB.
     */
    public AABB transform(Matrix3x4 transform) {
        var px = transform.m00() * this.x() + transform.m01() * this.y() + transform.m02() * this.z() + transform.m03();
        var py = transform.m10() * this.x() + transform.m11() * this.y() + transform.m12() * this.z() + transform.m13();
        var pz = transform.m20() * this.x() + transform.m21() * this.y() + transform.m22() * this.z() + transform.m23();
        return fromPoints(
            px, py, pz,
            px + transform.m00() * this.width() + transform.m10() * this.width() + transform.m20() * this.width(),
            py + transform.m01() * this.height() + transform.m11() * this.height() + transform.m21() * this.height(),
            pz + transform.m02() * this.depth() + transform.m12() * this.depth() + transform.m22() * this.depth()
        );
    }

    /**
     * Transforms this AABB by multiplying it with the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 4x4 transformation matrix.
     * @return The resulting AABB.
     */
    public AABB transform(Matrix4 transform) {
        var px = transform.m00() * this.x() + transform.m01() * this.y() + transform.m02() * this.z() + transform.m03();
        var py = transform.m10() * this.x() + transform.m11() * this.y() + transform.m12() * this.z() + transform.m13();
        var pz = transform.m20() * this.x() + transform.m21() * this.y() + transform.m22() * this.z() + transform.m23();
        return fromPoints(
            px, py, pz,
            px + transform.m00() * this.width() + transform.m10() * this.width() + transform.m20() * this.width(),
            py + transform.m01() * this.height() + transform.m11() * this.height() + transform.m21() * this.height(),
            pz + transform.m02() * this.depth() + transform.m12() * this.depth() + transform.m22() * this.depth()
        );
    }

    /**
     * Inversely transforms this AABB by multiplying it with the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link AABB#transform(Matrix3)} with the {@link Matrix3#inverse()} of the matrix.
     * </p>
     *
     * @param m A 3x3 transformation matrix.
     * @return The resulting AABB.
     */
    public AABB inverseTransform(Matrix3 m) {
        return this.transform(m.inverse());
    }

    /**
     * Inversely transforms this AABB by multiplying it with the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link AABB#transform(Matrix4)} with the {@link Matrix4#inverse()} of the matrix.
     * </p>
     *
     * @param m A 4x4 transformation matrix.
     * @return The resulting AABB.
     */
    public AABB inverseTransform(Matrix4 m) {
        return this.transform(m.inverse());
    }

    /**
     * Returns the support point of this AABB for a collision with the given normal.
     * This point is the one that is the furthest away in the given direction.
     *
     * @param normal The collision normal.
     * @return The support point.
     */
    public Vector3 support(Vector3 normal) {
        return new Vector3(
            normal.x() > 0.0f ? this.x() + this.width() : this.x(),
            normal.y() > 0.0f ? this.y() + this.height() : this.y(),
            normal.z() > 0.0f ? this.z() + this.depth() : this.z()
        );
    }

    /**
     * Checks if this AABB is congruent to the given one.
     * <p>
     *     Unlike {@link AABB#equalsApprox(AABB)}, this method returns true for AABBs with different origins and sizes if they represent the same AABB.
     * </p>
     *
     * @param aabb The other AABB.
     * @return True if this AABB is congruent to the given one, otherwise false.
     */
    public boolean isCongruentTo(AABB aabb) {
        return MathUtils.equalsApprox(this.left(), aabb.left())
            && MathUtils.equalsApprox(this.right(), aabb.right())
            && MathUtils.equalsApprox(this.top(), aabb.top())
            && MathUtils.equalsApprox(this.bottom(), aabb.bottom())
            && MathUtils.equalsApprox(this.front(), aabb.front())
            && MathUtils.equalsApprox(this.back(), aabb.back());
    }

    /**
     * Checks if this AABB is approximately equal to the given one by checking if positions and sizes are approximately equal using an internal epsilon.
     * <p>
     *     Unlike {@link AABB#isCongruentTo(AABB)}, this method returns false if the given AABB has a different origin or size even if it represents an AABB that is equivalent to this one.
     * </p>
     *
     * @param aabb The other AABB.
     * @return True if this AABB is approximately equal to the given one, otherwise false.
     */
    public boolean equalsApprox(AABB aabb) {
        return MathUtils.equalsApprox(this.x(), aabb.x())
            && MathUtils.equalsApprox(this.y(), aabb.y())
            && MathUtils.equalsApprox(this.z(), aabb.z())
            && MathUtils.equalsApprox(this.width(), aabb.width())
            && MathUtils.equalsApprox(this.height(), aabb.height())
            && MathUtils.equalsApprox(this.depth(), aabb.depth());
    }
}
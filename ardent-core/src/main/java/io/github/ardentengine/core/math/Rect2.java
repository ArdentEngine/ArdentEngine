package io.github.ardentengine.core.math;

/**
 * A 2D axis-aligned bounding box using floating point coordinates.
 * It is defined by two 2D vectors: a {@link Rect2#position()}, usually the bottom left corner, and a {@link Rect2#size()}, generally positive.
 * <p>
 *     The size of the rectangle can be negative.
 *     Rectangles with a negative size are considered valid and do not have their origin at the bottom left corner.
 *     The size is considered to be the vector from the rectangle's origin to its {@link Rect2#end()} point.
 *     Use {@link Rect2#abs()} to get an equivalent rectangle with a non-negative size.
 * </p>
 * <p>
 *     For integer coordinates, use {@link Rect2i}.
 *     The 3D equivalent is {@link AABB}.
 * </p>
 *
 * @param x The x coordinate of the rectangle's position.
 * @param y The y coordinate of the rectangle's position.
 * @param width The width of the rectangle.
 * @param height The height of the rectangle.
 */
public record Rect2(float x, float y, float width, float height) {

    /**
     * Constructs a rectangle at the given position with the given size.
     *
     * @param position The rectangle's position.
     * @param size The size of the rectangle.
     */
    public Rect2(Vector2 position, Vector2 size) {
        this(position.x(), position.y(), size.x(), size.y());
    }

    /**
     * Constructs a rectangle at the given position with the given width and height.
     *
     * @param position The rectangle's position.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rect2(Vector2 position, float width, float height) {
        this(position.x(), position.y(), width, height);
    }

    /**
     * Constructs a rectangle at the given x and y coordinates with the given size.
     *
     * @param x The x coordinate of the rectangle's position.
     * @param y The y coordinate of the rectangle's position.
     * @param size The size of the rectangle.
     */
    public Rect2(float x, float y, Vector2 size) {
        this(x, y, size.x(), size.y());
    }

    /**
     * Constructs an empty rectangle, whose position and size are both {@link Vector2#ZERO}.
     */
    public Rect2() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Returns the origin position of this rectangle.
     * Corresponds to the rectangle's {@link Rect2#bottomLeft()} corner if the {@link Rect2#size()} of the rectangle is positive.
     *
     * @return The origin position of this rectangle.
     */
    public Vector2 position() {
        return new Vector2(this.x(), this.y());
    }

    /**
     * Returns the size of this rectangle.
     * Can be negative.
     * <p>
     *     The size of the rectangle is the vector from the rectangle's {@link Rect2#position()} to its {@link Rect2#end()}.
     * </p>
     *
     * @return The size of this rectangle.
     */
    public Vector2 size() {
        return new Vector2(this.width(), this.height());
    }

    /**
     * Returns this rectangle's end point.
     * Corresponds to the rectangle's {@link Rect2#topRight()} corner if the {@link Rect2#size()} of the rectangle is positive.
     * <p>
     *     Equivalent to the rectangle's {@link Rect2#position()} plus its {@link Rect2#size()}.
     * </p>
     *
     * @return The end point of this rectangle.
     */
    public Vector2 end() {
        return new Vector2(this.x() + this.width(), this.y() + this.height());
    }

    /**
     * Returns the center point of this rectangle.
     * <p>
     *     Equivalent to the rectangle's {@link Rect2#position()} plus half its {@link Rect2#size()}.
     * </p>
     *
     * @return The center point of this rectangle.
     */
    public Vector2 center() {
        return new Vector2(this.x() + this.width() / 2.0f, this.y() + this.height() / 2.0f);
    }

    /**
     * Returns the area of this rectangle.
     *
     * @return The area of this rectangle.
     */
    public float area() {
        return this.width() * this.height();
    }

    /**
     * Returns the x coordinate of the leftmost point of the rectangle.
     * This method is guaranteed to return the left extent of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The x coordinate of the leftmost point of the rectangle.
     * @see Rect2#bottomLeft()
     * @see Rect2#topLeft()
     */
    public float left() {
        return Math.min(this.x(), this.x() + this.width());
    }

    /**
     * Returns the x coordinate of the rightmost point of the rectangle.
     * This method is guaranteed to return the right extent of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The x coordinate of the rightmost point of the rectangle.
     * @see Rect2#bottomRight()
     * @see Rect2#topRight()
     */
    public float right() {
        return Math.max(this.x(), this.x() + this.width());
    }

    /**
     * Returns the y coordinate of the highest point of the rectangle.
     * This method is guaranteed to return the top extent of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The y coordinate of the highest point of the rectangle.
     * @see Rect2#topLeft()
     * @see Rect2#topRight()
     */
    public float top() {
        return Math.max(this.y(), this.y() + this.height());
    }

    /**
     * Returns the y coordinate of the lowest point of the rectangle.
     * This method is guaranteed to return the bottom extent of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The y coordinate of the lowest point of the rectangle.
     * @see Rect2#bottomLeft()
     * @see Rect2#bottomRight()
     */
    public float bottom() {
        return Math.min(this.y(), this.y() + this.height());
    }

    /**
     * Returns the position of the bottom left corner of the rectangle.
     * This method is guaranteed to return the bottom left corner of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The position of the bottom left corner of the rectangle.
     * @see Rect2#bottom()
     * @see Rect2#left()
     */
    public Vector2 bottomLeft() {
        return new Vector2(this.left(), this.bottom());
    }

    /**
     * Returns the position of the top left corner of the rectangle.
     * This method is guaranteed to return the top left corner of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The position of the top left corner of the rectangle.
     * @see Rect2#top()
     * @see Rect2#left()
     */
    public Vector2 topLeft() {
        return new Vector2(this.left(), this.top());
    }

    /**
     * Returns the position of the bottom right corner of the rectangle.
     * This method is guaranteed to return the bottom right corner of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The position of the bottom right corner of the rectangle.
     * @see Rect2#bottom()
     * @see Rect2#right()
     */
    public Vector2 bottomRight() {
        return new Vector2(this.right(), this.bottom());
    }

    /**
     * Returns the position of the top right corner of the rectangle.
     * This method is guaranteed to return the top right corner of the rectangle regardless of the sign of its {@link Rect2#size()}.
     *
     * @return The position of the top right corner of the rectangle.
     * @see Rect2#top()
     * @see Rect2#right()
     */
    public Vector2 topRight() {
        return new Vector2(this.right(), this.top());
    }

    /**
     * Checks if this rectangle contains the point at the given coordinates.
     * Points laying on the borders of the rectangle are not considered to be inside the rectangle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return True if the point at the given coordinates is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(float x, float y) {
        return x > this.left() && x < this.right() && y > this.bottom() && y < this.top();
    }

    /**
     * Checks if the given point is inside this rectangle.
     * Points laying on the borders of the rectangle are not considered to be inside the rectangle.
     *
     * @param point The point.
     * @return True if the given point is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(Vector2 point) {
        return this.containsPoint(point.x(), point.y());
    }

    /**
     * Checks if this rectangle contains the point at the given coordinates.
     * If {@code includeBorders} is true, points laying on the borders of the rectangle will be considered inside the rectangle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param includeBorders If true, points laying on the borders of the rectangle will be considered inside the rectangle.
     * @return True if the point at the given coordinates is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(float x, float y, boolean includeBorders) {
        return includeBorders ? x >= this.left() && x <= this.right() && y >= this.bottom() && y <= this.top() : this.containsPoint(x, y);
    }

    /**
     * Checks if the given point is inside this rectangle.
     * If {@code includeBorders} is true, points laying on the borders of the rectangle will be considered inside the rectangle.
     *
     * @param point The point.
     * @param includeBorders If true, points laying on the borders of the rectangle will be considered inside the rectangle.
     * @return True if the given point is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(Vector2 point, boolean includeBorders) {
        return this.containsPoint(point.x(), point.y(), includeBorders);
    }

    /**
     * Checks if this rectangle overlaps with the given one.
     * The edges of both rectangles are excluded.
     *
     * @param rect The other rectangle.
     * @return True if this rectangle overlaps with the given one, otherwise false.
     * @see Rect2#encloses(Rect2)
     */
    public boolean intersects(Rect2 rect) {
        return !(this.right() <= rect.left() || this.left() >= rect.right() || this.top() <= rect.bottom() || this.bottom() >= rect.top());
    }

    /**
     * Checks if this rectangle overlaps with the given one.
     * If {@code includeBorders} is true, rectangles touching each other by their edge are considered as overlapping.
     *
     * @param rect The other rectangle.
     * @param includeBorders If true, rectangles touching each other by their edge are considered as overlapping.
     * @return True if this rectangle overlaps with the given one, otherwise false.
     * @see Rect2#encloses(Rect2)
     */
    public boolean intersects(Rect2 rect, boolean includeBorders) {
        return includeBorders ? !(this.right() < rect.left() || this.left() > rect.right() || this.top() < rect.bottom() || this.bottom() > rect.top()) : this.intersects(rect);
    }

    /**
     * Constructs a rectangle using the two given points as the bounds of the rectangle.
     * The given points will be two of the corners of the resulting rectangle.
     * <p>
     *     The resulting rectangle is guaranteed to have a positive size.
     * </p>
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The newly instantiated rectangle.
     */
    public static Rect2 fromPoints(float x1, float y1, float x2, float y2) {
        return new Rect2(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Constructs a rectangle using the two given points as the bounds of the rectangle.
     * The given points will be two of the corners of the resulting rectangle.
     * <p>
     *     The resulting rectangle is guaranteed to have a positive size.
     * </p>
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param p2 The second point.
     * @return The newly instantiated rectangle.
     */
    public static Rect2 fromPoints(float x1, float y1, Vector2 p2) {
        return fromPoints(x1, y1, p2.x(), p2.y());
    }

    /**
     * Constructs a rectangle using the two given points as the bounds of the rectangle.
     * The given points will be two of the corners of the resulting rectangle.
     * <p>
     *     The resulting rectangle is guaranteed to have a positive size.
     * </p>
     *
     * @param p1 The first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The newly instantiated rectangle.
     */
    public static Rect2 fromPoints(Vector2 p1, float x2, float y2) {
        return fromPoints(p1.x(), p1.y(), x2, y2);
    }

    /**
     * Constructs a rectangle using the two given points as the bounds of the rectangle.
     * The given points will be two of the corners of the resulting rectangle.
     * <p>
     *     The resulting rectangle is guaranteed to have a positive size.
     * </p>
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @return The newly instantiated rectangle.
     */
    public static Rect2 fromPoints(Vector2 p1, Vector2 p2) {
        return fromPoints(p1.x(), p1.y(), p2.x(), p2.y());
    }

    /**
     * Returns the intersection between this rectangle and the given one or an empty rectangle if the two do not intersect.
     * This method always returns a rectangle with positive {@link Rect2#size()}.
     *
     * @param rect The other rectangle.
     * @return The intersection between this rectangle and the given one.
     * @see Rect2#intersects(Rect2, boolean)
     */
    public Rect2 intersection(Rect2 rect) {
        if(this.intersects(rect, true)) {
            return fromPoints(Math.max(this.left(), rect.left()), Math.max(this.bottom(), rect.bottom()), Math.min(this.right(), rect.right()), Math.min(this.top(), rect.top()));
        }
        return new Rect2();
    }

    /**
     * Returns a rectangle equivalent to this one with non-negative {@link Rect2#size()} and its {@link Rect2#position()} being the bottom left corner.
     *
     * @return A rectangle equivalent to this one with non-negative size and its position being the bottom left corner.
     */
    public Rect2 abs() {
        return new Rect2(this.left(), this.bottom(), Math.abs(this.width()), Math.abs(this.height()));
    }

    /**
     * Checks if this rectangle completely encloses the given one.
     *
     * @param rect The other rectangle.
     * @return True if this rectangle completely encloses the given one, otherwise false.
     * @see Rect2#intersects(Rect2)
     */
    public boolean encloses(Rect2 rect) {
        return this.left() <= rect.left() && this.right() >= rect.right() && this.top() >= rect.top() && this.bottom() <= rect.bottom();
    }

    /**
     * Extends all the sides of this rectangle by the given amounts and returns the result.
     * A negative amount shrinks the rectangle.
     * <p>
     *     If the size of this rectangle is negative on one of the axes, a positive amount will shrink it and a negative amount will grow it.
     * </p>
     *
     * @param left Amount by which the left side should grow.
     * @param top Amount by which the top side should grow.
     * @param right Amount by which the right side should grow.
     * @param bottom Amount by which the bottom side should grow.
     * @return A copy of this rectangle with all sides extended by the given amounts.
     */
    public Rect2 grow(float left, float top, float right, float bottom) {
        return new Rect2(this.x() - left, this.y() - bottom, this.width() + left + right, this.height() + top + bottom);
    }

    /**
     * Extends all the sides of this rectangle by the given amount and returns the result.
     * A negative amount shrinks the rectangle.
     * <p>
     *     If the size of this rectangle is negative on one of the axes, a positive amount will shrink it and a negative amount will grow it.
     * </p>
     *
     * @param amount Amount by which the rectangle should grow.
     * @return A copy of this rectangle with all sides extended by the given amount.
     */
    public Rect2 grow(float amount) {
        return this.grow(amount, amount, amount, amount);
    }

    /**
     * Returns a copy of this rectangle expanded to align the edges with the given point.
     * <p>
     *     If the given point is outside the rectangle, the resulting rectangle will have the given point on one of its edges.
     *     The result will be an equivalent rectangle if the given point is inside this rectangle.
     * </p>
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return A copy of this rectangle expanded to align the edges with the given point.
     */
    public Rect2 expandTo(float x, float y) {
        return fromPoints(Math.min(this.left(), x), Math.min(this.bottom(), y), Math.max(this.right(), x), Math.max(this.top(), y));
    }

    /**
     * Returns a copy of this rectangle expanded to align the edges with the given point.
     * <p>
     *     If the given point is outside the rectangle, the resulting rectangle will have the given point on one of its edges.
     *     The result will be an equivalent rectangle if the given point is inside this rectangle.
     * </p>
     *
     * @param point The point.
     * @return A copy of this rectangle expanded to align the edges with the given point.
     */
    public Rect2 expandTo(Vector2 point) {
        return this.expandTo(point.x(), point.y());
    }

    /**
     * Returns a rectangle that encloses both this one and the given one around the edges.
     *
     * @param rect The other rectangle.
     * @return A rectangle that encloses both this one and the given one around the edges.
     * @see Rect2#encloses(Rect2)
     */
    public Rect2 merge(Rect2 rect) {
        return fromPoints(Math.min(this.left(), rect.left()), Math.min(this.bottom(), rect.bottom()), Math.max(this.right(), rect.right()), Math.max(this.top(), rect.top()));
    }

    /**
     * Returns a circle that completely encloses this rectangle.
     * The center of the circle corresponds to the center of the rectangle, its radius corresponds to the distance from the center to the corners of the rectangle.
     *
     * @return A circle that completely encloses this rectangle.
     */
    public Circle boundingCircle() {
        return new Circle(this.center(), this.x(), this.y());
    }

    /**
     * Transforms this rectangle by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 2x2 transformation matrix.
     * @return The resulting rectangle.
     */
    public Rect2 transform(Matrix2 transform) {
        var x = transform.column0().multiply(this.width());
        var y = transform.column1().multiply(this.height());
        var p = transform.multiply(this.x(), this.y());
        return fromPoints(p, p.x() + x.x() + y.x(), p.y() + x.y() + y.y());
    }

    /**
     * Transforms this rectangle by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 2x3 transformation matrix.
     * @return The resulting rectangle.
     */
    public Rect2 transform(Matrix2x3 transform) {
        var x = transform.column0().multiply(this.width());
        var y = transform.column1().multiply(this.height());
        var p = transform.multiply(this.x(), this.y(), 1.0f);
        return fromPoints(p, p.x() + x.x() + y.x(), p.y() + x.y() + y.y());
    }

    /**
     * Transforms this rectangle by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     *
     * @param transform A 3x3 transformation matrix.
     * @return The resulting rectangle.
     */
    public Rect2 transform(Matrix3 transform) {
        var x = transform.column0().multiply(this.width());
        var y = transform.column1().multiply(this.height());
        var p = transform.multiply(this.x(), this.y(), 1.0f);
        return fromPoints(p.x(), p.y(), p.x() + x.x() + y.x(), p.y() + x.y() + y.y());
    }

    /**
     * Inversely transforms this rectangle by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link Rect2#transform(Matrix2)} with the {@link Matrix2#inverse()} of the matrix.
     * </p>
     *
     * @param transform A 2x2 transformation matrix.
     * @return The resulting rectangle.
     */
    public Rect2 inverseTransform(Matrix2 transform) {
        return this.transform(transform.inverse());
    }

    /**
     * Inversely transforms this rectangle by the given matrix under the assumption that it is a valid transformation matrix and returns the result.
     * <p>
     *     This method is equivalent to {@link Rect2#transform(Matrix3)} with the {@link Matrix3#inverse()} of the matrix.
     * </p>
     *
     * @param transform A 3x3 transformation matrix.
     * @return The resulting rectangle.
     */
    public Rect2 inverseTransform(Matrix3 transform) {
        return this.transform(transform.inverse());
    }

    /**
     * Returns the support point of this rectangle for a collision with the given normal.
     * This point is the one that is the furthest away in the given direction.
     *
     * @param normal The collision normal.
     * @return The support point.
     */
    public Vector2 support(Vector2 normal) {
        return new Vector2(
            normal.x() > 0.0f ? this.x() + this.width() : this.x(),
            normal.y() > 0.0f ? this.y() + this.height() : this.y()
        );
    }

    /**
     * Checks if this rectangle is congruent to the given one.
     * <p>
     *     Unlike {@link Rect2#equalsApprox(Rect2)}, this method returns true for rectangles with different origins and sizes if they represent the same rectangle.
     * </p>
     *
     * @param rect The other rectangle.
     * @return True if this rectangle is congruent to the given one, otherwise false.
     */
    public boolean isCongruentTo(Rect2 rect) {
        return MathUtils.equalsApprox(this.left(), rect.left())
            && MathUtils.equalsApprox(this.right(), rect.right())
            && MathUtils.equalsApprox(this.top(), rect.top())
            && MathUtils.equalsApprox(this.bottom(), rect.bottom());
    }

    /**
     * Checks if this rectangle is approximately equal to the given one by checking if positions and sizes are approximately equal using an internal epsilon.
     * <p>
     *     Unlike {@link Rect2#isCongruentTo(Rect2)}, this operator returns false if the given rectangle has a different origin or size even if it represents a rectangle equal to this one.
     * </p>
     *
     * @param rect The other rectangle.
     * @return True if this rectangle is approximately equal to the given one, otherwise false.
     */
    public boolean equalsApprox(Rect2 rect) {
        return MathUtils.equalsApprox(this.x(), rect.x())
            && MathUtils.equalsApprox(this.y(), rect.y())
            && MathUtils.equalsApprox(this.width(), rect.width())
            && MathUtils.equalsApprox(this.height(), rect.height());
    }

    /**
     * Casts this rectangle to a rectangle using integer coordinates.
     *
     * @return This rectangle cast to int.
     */
    public Rect2i toInt() {
        return new Rect2i((int) this.x(), (int) this.y(), (int) this.width(), (int) this.height());
    }
}
package io.github.ardentengine.core.math;

/**
 * A 2D axis-aligned bounding box using integer coordinates.
 * It is defined by two 2D a vectors {@link Rect2i#position()}, usually the bottom left corner, and a {@link Rect2i#size()}, generally positive.
 * <p>
 *     The size of the rectangle can be negative.
 *     Rectangles with a negative size are considered valid and do not have their origin at the bottom left corner.
 *     The size is considered to be the vector from the rectangle's origin to its {@link Rect2i#end()} point.
 *     Use {@link Rect2i#abs()} to get an equivalent rectangle with a non-negative size.
 * </p>
 * <p>
 *     For floating point coordinates, use {@link Rect2}.
 * </p>
 *
 * @param x The x coordinate of the rectangle's position.
 * @param y The y coordinate of the rectangle's position.
 * @param width The width of the rectangle.
 * @param height The height of the rectangle.
 */
public record Rect2i(int x, int y, int width, int height) {

    /**
     * Constructs a rectangle at the given position with the given size.
     *
     * @param position The rectangle's position.
     * @param size The size of the rectangle.
     */
    public Rect2i(Vector2i position, Vector2i size) {
        this(position.x(), position.y(), size.x(), size.y());
    }

    /**
     * Constructs a rectangle at the given position with the given width and height.
     *
     * @param position The rectangle's position.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rect2i(Vector2i position, int width, int height) {
        this(position.x(), position.y(), width, height);
    }

    /**
     * Constructs a rectangle at the given x and y coordinates with the given size.
     *
     * @param x The x coordinate of the rectangle's position.
     * @param y The y coordinate of the rectangle's position.
     * @param size The size of the rectangle.
     */
    public Rect2i(int x, int y, Vector2i size) {
        this(x, y, size.x(), size.y());
    }

    /**
     * Constructs an empty rectangle, whose position and size are both {@link Vector2i#ZERO}.
     */
    public Rect2i() {
        this(0, 0, 0, 0);
    }

    /**
     * Returns the origin position of this rectangle.
     * Corresponds to the rectangle's {@link Rect2i#bottomLeft()} corner if the {@link Rect2i#size()} of the rectangle is positive.
     *
     * @return The origin position of this rectangle.
     */
    public Vector2i position() {
        return new Vector2i(this.x(), this.y());
    }

    /**
     * Returns the size of this rectangle.
     * Can be negative.
     * <p>
     *     The size of the rectangle is the vector from the rectangle's {@link Rect2i#position()} to its {@link Rect2i#end()}.
     * </p>
     *
     * @return The size of this rectangle.
     */
    public Vector2i size() {
        return new Vector2i(this.width(), this.height());
    }

    /**
     * Returns this rectangle's end point.
     * Corresponds to the rectangle's {@link Rect2i#topRight()} corner if the {@link Rect2i#size()} of the rectangle is positive.
     * <p>
     *     Equivalent to the rectangle's {@link Rect2i#position()} plus its {@link Rect2i#size()}.
     * </p>
     *
     * @return The end point of this rectangle.
     */
    public Vector2i end() {
        return new Vector2i(this.x() + this.width(), this.y() + this.height());
    }

    /**
     * Returns the center point of this rectangle.
     * <p>
     *     Equivalent to the rectangle's {@link Rect2i#position()} plus half its {@link Rect2i#size()}.
     * </p>
     * <p>
     *     Note that this method uses an integer division.
     * </p>
     *
     * @return The center point of this rectangle.
     */
    public Vector2i center() {
        return new Vector2i(this.x() + this.width() / 2, this.y() + this.height() / 2);
    }

    /**
     * Returns the area of this rectangle.
     *
     * @return The area of this rectangle.
     */
    public int area() {
        return this.width() * this.height();
    }

    /**
     * Returns the x coordinate of the leftmost point of the rectangle.
     * This method is guaranteed to return the left extent of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The x coordinate of the leftmost point of the rectangle.
     * @see Rect2i#bottomLeft()
     * @see Rect2i#topLeft()
     */
    public int left() {
        return Math.min(this.x(), this.x() + this.width());
    }

    /**
     * Returns the x coordinate of the rightmost point of the rectangle.
     * This method is guaranteed to return the right extent of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The x coordinate of the rightmost point of the rectangle.
     * @see Rect2i#bottomRight()
     * @see Rect2i#topRight()
     */
    public int right() {
        return Math.max(this.x(), this.x() + this.width());
    }

    /**
     * Returns the y coordinate of the highest point of the rectangle.
     * This method is guaranteed to return the top extent of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The y coordinate of the highest point of the rectangle.
     * @see Rect2i#topLeft()
     * @see Rect2i#topRight()
     */
    public int top() {
        return Math.max(this.y(), this.y() + this.height());
    }

    /**
     * Returns the y coordinate of the lowest point of the rectangle.
     * This method is guaranteed to return the bottom extent of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The y coordinate of the lowest point of the rectangle.
     * @see Rect2i#bottomLeft()
     * @see Rect2i#bottomRight()
     */
    public int bottom() {
        return Math.min(this.y(), this.y() + this.height());
    }

    /**
     * Returns the position of the bottom left corner of the rectangle.
     * This method is guaranteed to return the bottom left corner of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The position of the bottom left corner of the rectangle.
     * @see Rect2i#bottom()
     * @see Rect2i#left()
     */
    public Vector2i bottomLeft() {
        return new Vector2i(this.left(), this.bottom());
    }

    /**
     * Returns the position of the top left corner of the rectangle.
     * This method is guaranteed to return the top left corner of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The position of the top left corner of the rectangle.
     * @see Rect2i#top()
     * @see Rect2i#left()
     */
    public Vector2i topLeft() {
        return new Vector2i(this.left(), this.top());
    }

    /**
     * Returns the position of the bottom right corner of the rectangle.
     * This method is guaranteed to return the bottom right corner of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The position of the bottom right corner of the rectangle.
     * @see Rect2i#bottom()
     * @see Rect2i#right()
     */
    public Vector2i bottomRight() {
        return new Vector2i(this.right(), this.bottom());
    }

    /**
     * Returns the position of the top right corner of the rectangle.
     * This method is guaranteed to return the top right corner of the rectangle regardless of the sign of its {@link Rect2i#size()}.
     *
     * @return The position of the top right corner of the rectangle.
     * @see Rect2i#top()
     * @see Rect2i#right()
     */
    public Vector2i topRight() {
        return new Vector2i(this.right(), this.top());
    }

    /**
     * Checks if this rectangle contains the point at the given coordinates.
     * Points laying on the borders of the rectangle are not considered to be inside the rectangle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return True if the point at the given coordinates is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(int x, int y) {
        return x > this.left() && x < this.right() && y > this.bottom() && y < this.top();
    }

    /**
     * Checks if the given point is inside this rectangle.
     * Points laying on the borders of the rectangle are not considered to be inside the rectangle.
     *
     * @param point The point.
     * @return True if the given point is inside this rectangle, otherwise false.
     */
    public boolean containsPoint(Vector2i point) {
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
    public boolean containsPoint(int x, int y, boolean includeBorders) {
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
    public boolean containsPoint(Vector2i point, boolean includeBorders) {
        return this.containsPoint(point.x(), point.y(), includeBorders);
    }

    /**
     * Checks if this rectangle overlaps with the given one.
     * The edges of both rectangles are excluded.
     *
     * @param rect The other rectangle.
     * @return True if this rectangle overlaps with the given one, otherwise false.
     * @see Rect2i#encloses(Rect2i)
     */
    public boolean intersects(Rect2i rect) {
        return !(this.right() <= rect.left() || this.left() >= rect.right() || this.top() <= rect.bottom() || this.bottom() >= rect.top());
    }

    /**
     * Checks if this rectangle overlaps with the given one.
     * If {@code includeBorders} is true, rectangles touching each other by their edge are considered as overlapping.
     *
     * @param rect The other rectangle.
     * @param includeBorders If true, rectangles touching each other by their edge are considered as overlapping.
     * @return True if this rectangle overlaps with the given one, otherwise false.
     * @see Rect2i#encloses(Rect2i)
     */
    public boolean intersects(Rect2i rect, boolean includeBorders) {
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
    public static Rect2i fromPoints(int x1, int y1, int x2, int y2) {
        return new Rect2i(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
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
    public static Rect2i fromPoints(int x1, int y1, Vector2i p2) {
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
    public static Rect2i fromPoints(Vector2i p1, int x2, int y2) {
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
    public static Rect2i fromPoints(Vector2i p1, Vector2i p2) {
        return fromPoints(p1.x(), p1.y(), p2.x(), p2.y());
    }

    /**
     * Returns the intersection between this rectangle and the given one or an empty rectangle if the two do not intersect.
     * This method always returns a rectangle with positive {@link Rect2i#size()}.
     *
     * @param rect The other rectangle.
     * @return The intersection between this rectangle and the given one.
     * @see Rect2i#intersects(Rect2i, boolean)
     */
    public Rect2i intersection(Rect2i rect) {
        if(this.intersects(rect, true)) {
            return fromPoints(Math.max(this.left(), rect.left()), Math.max(this.bottom(), rect.bottom()), Math.min(this.right(), rect.right()), Math.min(this.top(), rect.top()));
        }
        return new Rect2i();
    }

    /**
     * Returns a rectangle equivalent to this one with non-negative {@link Rect2i#size()} and its {@link Rect2i#position()} being the bottom left corner.
     *
     * @return A rectangle equivalent to this one with non-negative size and its position being the bottom left corner.
     */
    public Rect2i abs() {
        return new Rect2i(this.left(), this.bottom(), Math.abs(this.width()), Math.abs(this.height()));
    }

    /**
     * Checks if this rectangle completely encloses the given one.
     *
     * @param rect The other rectangle.
     * @return True if this rectangle completely encloses the given one, otherwise false.
     * @see Rect2i#intersects(Rect2i)
     */
    public boolean encloses(Rect2i rect) {
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
    public Rect2i grow(int left, int top, int right, int bottom) {
        return new Rect2i(this.x() - left, this.y() - bottom, this.width() + left + right, this.height() + top + bottom);
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
    public Rect2i grow(int amount) {
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
    public Rect2i expandTo(int x, int y) {
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
    public Rect2i expandTo(Vector2i point) {
        return this.expandTo(point.x(), point.y());
    }

    /**
     * Returns a rectangle that encloses both this one and the given one around the edges.
     *
     * @param rect The other rectangle.
     * @return A rectangle that encloses both this one and the given one around the edges.
     * @see Rect2i#encloses(Rect2i)
     */
    public Rect2i merge(Rect2i rect) {
        return fromPoints(Math.min(this.left(), rect.left()), Math.min(this.bottom(), rect.bottom()), Math.max(this.right(), rect.right()), Math.max(this.top(), rect.top()));
    }

    /**
     * Checks if this rectangle is congruent to the given one.
     * <p>
     *     Unlike {@link Object#equals(Object)}, this method returns true for rectangles with different origins and sizes if they represent the same rectangle.
     * </p>
     *
     * @param rect The other rectangle.
     * @return True if this rectangle is congruent to the given one, otherwise false.
     */
    public boolean isCongruentTo(Rect2i rect) {
        return this.left() == rect.left() && this.right() == rect.right() && this.top() == rect.top() && this.bottom() == rect.bottom();
    }

    /**
     * Returns this rectangle as a rectangle using floating point coordinates.
     *
     * @return This rectangle using floating point coordinates.
     */
    public Rect2 toFloat() {
        return new Rect2(this.x(), this.y(), this.width(), this.height());
    }
}
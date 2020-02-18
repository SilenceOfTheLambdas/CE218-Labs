package utilities;

// mutable 2D vectors
public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() {
        x = 0;
        y = 0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
        set(v);
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        x = v.x;
        y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Vector2D other = (Vector2D)o;
        return Math.hypot(x, y) == Math.hypot(other.x, other.y);
    }

    // String for displaying vector as text
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
// can be calculated using Math.atan2
    public double angle() {
        return Math.atan2(y, x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {
        double cross = x * other.y - y * other.x;
        return Math.atan2(cross, dot(other));
    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        x += v.x;
        y += v.y;
        return this;
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.y * fac;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // multiply with factor
    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double newX = this.x * cos - this.y * sin;
        double newY = this.x * sin + this.y * cos;

        this.x = newX;
        this.y = newY;

        return this;
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        return Math.sqrt((x - v.x) * (x - v.x) + (y - v.y) * (y - v.y) );
    }

    // normalise vector so that magnitude becomes 1
    public Vector2D normalise() {
        double len = mag();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    // remember to manage negative values of the coordinates
    public Vector2D wrap(double w, double h) {
//        If ships x position of off to the left of the screen
        if (this.x < 0) return new Vector2D((this.x + w) % w, this.y); // Set the new x position to the far right
//        If ships x position if off to the right of the screen
        if (this.x > w) return new Vector2D((this.x + -w) % -w, this.y); // Set the new y position to the far left

//        If both x, y are negative
        if (this.x < 0 && this.y < h) return new Vector2D((this.x + w) % w, (this.y + -h) % -h);
//        If ships y position is above the limit of height
        if (this.y > 0) return new Vector2D(this.x, (this.y + -h) % -h); // Bring is back down
//        If ships y position is below the bottom
        if (this.y < h) return new Vector2D(this.x, (this.y + h) % h); // Bring it back up

        return new Vector2D((this.x + -w) % -w, (this.y + h) % h);
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {

        return new Vector2D(mag * Math.cos(angle),
                mag * Math.sin(angle));
    }
}

package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 *
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // aname of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment {
    private final String name;
    private final GeoPoint p1;
    private final GeoPoint p2;

    // Abs. Function:
    //   represents a straight geographic line that starts at this.p1 and ends at this.p2
    //   with a name of this.name

    // Rep. Invariant:
    //   this.name != null
    //   this.p1 != null
    //   this.p2 != null

    private void checkRep() {
        assert this.name != null : "Name cannot be null";
        assert this.p1 != null : "First endpoint cannot be null";
        assert this.p2 != null : "Second endpoint cannot be null";
    }

    /**
     * Constructs a new GeoSegment with the specified name and endpoints.
     *
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
    public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        checkRep();
    }


    /**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     *
     * @return a new GeoSegment gs such that gs.name = this.name
     * && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
    public GeoSegment reverse() {
        checkRep();
        return new GeoSegment(this.name, this.p2, this.p1);
    }


    /**
     * Returns the name of this GeoSegment.
     *
     * @return the name of this GeoSegment.
     */
    public String getName() {
        checkRep();
        return this.name;
    }


    /**
     * Returns first endpoint of the segment.
     *
     * @return first endpoint of the segment.
     */
    public GeoPoint getP1() {
        checkRep();
        return this.p1;
    }


    /**
     * Returns second endpoint of the segment.
     *
     * @return second endpoint of the segment.
     */
    public GeoPoint getP2() {
        checkRep();
        return this.p2;
    }


    /**
     * Returns the length of the segment.
     *
     * @return the length of the segment, using the flat-surface, near the
     * Technion approximation.
     */
    public double getLength() {
        checkRep();
        return this.p1.distanceTo(this.p2);
    }


    /**
     * Returns the compass heading from p1 to p2.
     *
     * @return the compass heading from p1 to p2, in degrees, using the
     * flat-surface, near the Technion approximation. If p1 and p2 are
     * equal (length == 0), returns 0.
     **/
    public double getHeading() {
        checkRep();
        if (this.p1.equals(this.p2)) {
            return 0;
        }
        return this.p1.headingTo(this.p2);
    }


    /**
     * Compares the specified Object with this GeoSegment for equality.
     *
     * @return gs != null && (gs instanceof GeoSegment)
     * && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
     **/
    public boolean equals(Object gs) {
        checkRep();
        if (this == gs) return true;
        if (!(gs instanceof GeoSegment other)) return false;

        boolean sameName = this.name.equals(other.name);
        boolean sameP1 = this.p1.equals(other.p1);
        boolean sameP2 = this.p2.equals(other.p2);
        checkRep();
        return sameName && sameP1 && sameP2;
    }


    /**
     * Returns a hash code value for this.
     *
     * @return a hash code value for this.
     **/
    public int hashCode() {
        checkRep();
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + Double.hashCode(getLength());
        return result;
    }


    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        StringBuilder sb = new StringBuilder();
        sb.append("GeoSegment: ");
        sb.append(this.name);
        sb.append(" [");
        sb.append(this.p1.toString());
        sb.append(", ");
        sb.append(this.p2.toString());
        sb.append("]");
        checkRep();
        return sb.toString();
    }

}


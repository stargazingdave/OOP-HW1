package homework1;

import java.util.*;
import java.util.ArrayList;


/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
    private final List<GeoSegment> geoSegments;
    private final String name;
    private final GeoPoint start;
    private final GeoPoint end;
    private final double startHeading;
    private final double endHeading;
    private final double length;


    // Abs. Function:
    //   represents a geographic feature that starts at this.start and ends at this.end
    //   with a name of this.name.
    //   The feature is made up of a sequence of GeoSegments, all with the same name (this.name).
    //   The length of the feature is the sum of the lengths of the segments.
    //   this.geoSegments is the list of segments that make up this feature.
    //   The order of the segments in this.geoSegments is the order in which they
    //   are traversed from start to end.
    //   The start and end headings are the headings at the start and end of the
    //   feature, respectively.

    // Rep. Invariant:
    //   geoSegments is non-empty
    //   All GeoSegments in geoSegments have the same name as this.name
    //   Each segment (except the first) in geoSegments has its p1 equal to the p2 of the previous segment
    //   this.start equals the p1 of the first segment in geoSegments
    //   this.end equals the p2 of the last segment in geoSegments
    //   this.startHeading equals the heading of the first segment in geoSegments
    //   this.endHeading equals the heading of the last segment in geoSegments
    //   this.length equals the sum of the lengths of all segments in geoSegments

    private void checkRep() {
        assert geoSegments != null && !geoSegments.isEmpty() : "GeoSegments must be non-empty";
        for (int i = 0; i < geoSegments.size() - 1; i++) {
            assert geoSegments.get(i).getName().equals(name) : "Segment names must match";
            assert geoSegments.get(i).getP2().equals(geoSegments.get(i + 1).getP1()) : "Segments must be connected";
        }
        assert start.equals(geoSegments.getFirst().getP1()) : "Start point mismatch";
        assert end.equals(geoSegments.getLast().getP2()) : "End point mismatch";
        assert startHeading == geoSegments.getFirst().getHeading() : "Start heading mismatch";
        assert endHeading == geoSegments.getLast().getHeading() : "End heading mismatch";
        double sum = geoSegments.stream().mapToDouble(GeoSegment::getLength).sum();
        assert Math.abs(sum - length) < 1e-6 : "Length mismatch";
    }


    /**
     * Constructs a new GeoFeature.
     *
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     * r.name = gs.name &&
     * r.startHeading = gs.heading &&
     * r.endHeading = gs.heading &&
     * r.start = gs.p1 &&
     * r.end = gs.p2
     **/
    public GeoFeature(GeoSegment gs) {
        this.geoSegments = new ArrayList<>();
        this.geoSegments.add(gs);
        this.name = gs.getName();
        this.start = gs.getP1();
        this.end = gs.getP2();
        this.startHeading = gs.getHeading();
        this.endHeading = gs.getHeading();
        this.length = gs.getLength();
        checkRep();
    }

    private GeoFeature(List<GeoSegment> segments) {
        this.geoSegments = Collections.unmodifiableList(segments);
        this.name = segments.getFirst().getName();
        this.start = segments.getFirst().getP1();
        this.end = segments.getLast().getP2();
        this.startHeading = segments.getFirst().getHeading();
        this.endHeading = segments.getLast().getHeading();
        this.length = segments.stream().mapToDouble(GeoSegment::getLength).sum();
        checkRep();
    }

    /**
     * Returns name of geographic feature.
     *
     * @return name of geographic feature
     */
    public String getName() {
        checkRep();
        return name;
    }


    /**
     * Returns location of the start of the geographic feature.
     *
     * @return location of the start of the geographic feature.
     */
    public GeoPoint getStart() {
        checkRep();
        return start;
    }


    /**
     * Returns location of the end of the geographic feature.
     *
     * @return location of the end of the geographic feature.
     */
    public GeoPoint getEnd() {
        checkRep();
        return end;
    }


    /**
     * Returns direction of travel at the start of the geographic feature.
     *
     * @return direction (in standard heading) of travel at the start of the
     * geographic feature, in degrees.
     * If start.length == 0, returns 0.
     */
    public double getStartHeading() {
        checkRep();
        return startHeading;
    }


    /**
     * Returns direction of travel at the end of the geographic feature.
     *
     * @return direction (in standard heading) of travel at the end of the
     * geographic feature, in degrees.
     * If end.length == 0, returns 0.
     */
    public double getEndHeading() {
        checkRep();
        return endHeading;
    }


    /**
     * Returns total length of the geographic feature, in kilometers.
     *
     * @return total length of the geographic feature, in kilometers.
     * NOTE: this is NOT as-the-crow-flies, but rather the total
     * distance required to traverse the geographic feature. These
     * values are not necessarily equal.
     */
    public double getLength() {
        checkRep();
        return length;
    }


    /**
     * Creates a new GeoFeature that is equal to this GeoFeature with gs
     * appended to its end.
     *
     * @return a new GeoFeature r such that
     * r.end = gs.p2 &&
     * r.endHeading = gs.heading &&
     * r.length = this.length + gs.length
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     **/
    public GeoFeature addSegment(GeoSegment gs) {
        checkRep();
        if (!gs.getName().equals(this.name)) {
            throw new IllegalArgumentException("Segment name must match the feature name.");
        }
        if (!gs.getP1().equals(this.end)) {
            throw new IllegalArgumentException("Segment must start where the feature ends.");
        }

        List<GeoSegment> newSegments = new ArrayList<>(this.geoSegments);
        newSegments.add(gs);
        checkRep();
        return new GeoFeature(newSegments);
    }

    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     *
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
    public Iterator<GeoSegment> getGeoSegments() {
        checkRep();
        return geoSegments.iterator();
    }


    /**
     * Compares the argument with this GeoFeature for equality.
     *
     * @return o != null && (o instanceof GeoFeature) &&
     * (o.geoSegments and this.geoSegments contain
     * the same elements in the same order).
     **/
    public boolean equals(Object o) {
        checkRep();
        if (this == o)
            return true;
        if (!(o instanceof GeoFeature other))
            return false;
        return this.geoSegments.equals(other.geoSegments);
    }


    /**
     * Returns a hash code for this.
     *
     * @return a hash code for this.
     **/
    public int hashCode() {
        checkRep();
        return 31 * geoSegments.hashCode() +
                17 * name.hashCode() +
                13 * Double.hashCode(length) +
                11 * start.hashCode() +
                7 * end.hashCode() +
                5 * Double.hashCode(startHeading) +
                3 * Double.hashCode(endHeading);
    }


    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "GeoFeature{name='" + name + "', length=" + length + ", segments=" + geoSegments.size() + "}";
    }
}

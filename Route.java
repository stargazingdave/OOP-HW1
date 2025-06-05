package homework1;

import java.util.*;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment
 * to the end of a Route. An added segment must be properly oriented; that
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {
    private final List<GeoSegment> segments;
    private final List<GeoFeature> features;
    private final GeoPoint start;
    private final GeoPoint end;
    private final double startHeading;
    private final double endHeading;
    private final double length;

    // Abs. Function:
    //   represents a path that traverses arbitrary GeoSegments, regardless
    //   of their names.
    //   The path is represented by a sequence of GeoSegments and
    //   GeoFeatures, with start and end points, and their respective headings.
    //   The length of the path is the total distance traveled by following
    //   the path from start to end.

    // Rep. Invariant:
    //   segments and features are non-empty
    //   segments[0].p1 == start
    //   segments[segments.length - 1].p2 == end
    //   segments[0].heading == startHeading
    //   segments[segments.length - 1].heading == endHeading
    //   length is the sum of the lengths of all segments
    //   For all i in [0, segments.length - 1]: segments[i].p2 == segments[i+1].p1
    //   For all i in [0, features.length - 1]:
    //      features[i].getEnd().equals(features[i+1].getStart())
    //      && !features[i].getName().equals(features[i+1].getName())
    //   Features cover the same segments in the same order as `segments`

    private void checkRep() {
        assert segments != null && !segments.isEmpty() : "Route must have segments";
        assert features != null && !features.isEmpty() : "Route must have features";

        assert segments.getFirst().getP1().equals(start) : "Start mismatch";
        assert segments.getLast().getP2().equals(end) : "End mismatch";
        assert startHeading == segments.getFirst().getHeading() : "Start heading mismatch";
        assert endHeading == segments.getLast().getHeading() : "End heading mismatch";

        double segmentSum = segments.stream().mapToDouble(GeoSegment::getLength).sum();
        assert Math.abs(length - segmentSum) < 1e-6 : "Length mismatch";

        for (int i = 0; i < segments.size() - 1; i++) {
            assert segments.get(i).getP2().equals(segments.get(i + 1).getP1()) : "Segments not connected";
        }

        for (int i = 0; i < features.size() - 1; i++) {
            assert features.get(i).getEnd().equals(features.get(i + 1).getStart()) : "Features not connected";
            assert !features.get(i).getName().equals(features.get(i + 1).getName()) : "Features must differ in name";
        }
    }

    /**
     * Constructs a new Route.
     *
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     * r.startHeading = gs.heading &&
     * r.endHeading = gs.heading &&
     * r.start = gs.p1 &&
     * r.end = gs.p2
     **/
    public Route(GeoSegment gs) {
        this.segments = new ArrayList<>();
        this.features = new ArrayList<>();
        this.segments.add(gs);
        this.features.add(new GeoFeature(gs));
        this.start = gs.getP1();
        this.end = gs.getP2();
        this.startHeading = gs.getHeading();
        this.endHeading = gs.getHeading();
        this.length = gs.getLength();
        checkRep();
    }

    private Route(List<GeoSegment> segments, List<GeoFeature> features) {
        this.segments = Collections.unmodifiableList(segments);
        this.features = Collections.unmodifiableList(features);
        this.start = segments.getFirst().getP1();
        this.end = segments.getLast().getP2();
        this.startHeading = segments.getFirst().getHeading();
        this.endHeading = segments.getLast().getHeading();
        this.length = segments.stream().mapToDouble(GeoSegment::getLength).sum();
        checkRep();
    }

    /**
     * Returns location of the start of the route.
     *
     * @return location of the start of the route.
     **/
    public GeoPoint getStart() {
        checkRep();
        return start;
    }


    /**
     * Returns location of the end of the route.
     *
     * @return location of the end of the route.
     **/
    public GeoPoint getEnd() {
        checkRep();
        return end;
    }


    /**
     * Returns direction of travel at the start of the route, in degrees.
     *
     * @return direction (in compass heading) of travel at the start of the
     * route, in degrees.
     * If start.length == 0, returns 0.
     **/
    public double getStartHeading() {
        checkRep();
        return startHeading;
    }


    /**
     * Returns direction of travel at the end of the route, in degrees.
     *
     * @return direction (in compass heading) of travel at the end of the
     * route, in degrees.
     * If end.length == 0, returns 0.
     **/
    public double getEndHeading() {
        checkRep();
        return endHeading;
    }


    /**
     * Returns total length of the route.
     *
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     * as-the-crow-flies, but rather the total distance required to
     * traverse the route. These values are not necessarily equal.
     **/
    public double getLength() {
        checkRep();
        return length;
    }


    /**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
     *
     * @return a new Route r such that
     * r.end = gs.p2 &&
     * r.endHeading = gs.heading &&
     * r.length = this.length + gs.length
     * @requires gs != null && gs.p1 == this.end
     **/
    public Route addSegment(GeoSegment gs) {
        checkRep();
        if (!gs.getP1().equals(this.end)) throw new IllegalArgumentException("Segment is not connected to route end.");

        List<GeoSegment> newSegments = new ArrayList<>(this.segments);
        newSegments.add(gs);

        List<GeoFeature> newFeatures = new ArrayList<>(this.features);
        GeoFeature lastFeature = newFeatures.getLast();

        if (gs.getName().equals(lastFeature.getName())) {
            newFeatures.set(newFeatures.size() - 1, lastFeature.addSegment(gs));
        } else {
            newFeatures.add(new GeoFeature(gs));
        }

        checkRep();
        return new Route(newSegments, newFeatures);
    }


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     *
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
    public Iterator<GeoFeature> getGeoFeatures() {
        checkRep();
        return features.iterator();
    }


    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     *
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
    public Iterator<GeoSegment> getGeoSegments() {
        checkRep();
        return segments.iterator();
    }


    /**
     * Compares the specified Object with this Route for equality.
     *
     * @return true iff (o instanceof Route) &&
     * (o.geoFeatures and this.geoFeatures contain
     * the same elements in the same order).
     **/
    public boolean equals(Object o) {
        checkRep();
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return this.features.equals(route.features);
    }


    /**
     * Returns a hash code for this.
     *
     * @return a hash code for this.
     **/
    public int hashCode() {
        checkRep();
        return 31 * features.hashCode() + 17 * start.hashCode() + 13 * Double.hashCode(length) + 7 * end.hashCode();
    }


    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "Route{start=" + start + ", end=" + end + ", length=" + length + ", segments=" + segments.size() + "}";
    }

}

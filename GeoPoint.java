package homework1;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {
    private final int latitude;
    private final int longitude;

    /**
     * Minimum value the latitude field can have in this class.
     **/
    public static final int MIN_LATITUDE = -90 * 1000000;

    /**
     * Maximum value the latitude field can have in this class.
     **/
    public static final int MAX_LATITUDE = 90 * 1000000;

    /**
     * Minimum value the longitude field can have in this class.
     **/
    public static final int MIN_LONGITUDE = -180 * 1000000;

    /**
     * Maximum value the longitude field can have in this class.
     **/
    public static final int MAX_LONGITUDE = 180 * 1000000;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LATITUDE = 110.901;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

    // Implementation hint:
    // Doubles and floating point math can cause some problems. The exact
    // value of a double can not be guaranteed except within some epsilon.
    // Because of this, using doubles for the equals() and hashCode()
    // methods can have erroneous results. Do not use floats or doubles for
    // any computations in hashCode(), equals(), or where any other time
    // exact values are required. (Exact values are not required for length
    // and distance computations). Because of this, you should consider
    // using ints for your internal representation of GeoPoint.


    // TODO Write abstraction function and representation invariant


    /**
     * Constructs GeoPoint from a latitude and longitude.
     *
     * @requires the point given by (latitude, longitude) in millionths
     * of a degree is valid such that:
     * (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
     * @effects constructs a GeoPoint from a latitude and longitude
     * given in millionths of degrees.
     **/
    public GeoPoint(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Returns the latitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLatitude() {
        return latitude;
    }


    /**
     * Returns the longitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLongitude() {
        return longitude;
    }


    /**
     * Computes the distance between GeoPoints.
     *
     * @return the distance from this to gp, using the flat-surface, near
     * the Technion approximation.
     * @requires gp != null
     **/
    public double distanceTo(GeoPoint gp) {
        double dLat = (gp.latitude - this.latitude) / 1_000_000.0;
        double dLon = (gp.longitude - this.longitude) / 1_000_000.0;
        double latDist = dLat * KM_PER_DEGREE_LATITUDE;
        double lonDist = dLon * KM_PER_DEGREE_LONGITUDE;
        return Math.sqrt(latDist * latDist + lonDist * lonDist);
    }


    /**
     * Computes the compass heading between GeoPoints.
     *
     * @return the compass heading h from this to gp, in degrees, using the
     * flat-surface, near the Technion approximation, such that
     * 0 <= h < 360. In compass headings, north = 0, east = 90,
     * south = 180, and west = 270.
     * @requires gp != null && !this.equals(gp)
     **/
    public double headingTo(GeoPoint gp) {
        double deltaLat = (gp.latitude - this.latitude) / 1_000_000.0;
        double deltaLon = (gp.longitude - this.longitude) / 1_000_000.0;
        double latDist = deltaLat * KM_PER_DEGREE_LATITUDE;
        double lonDist = deltaLon * KM_PER_DEGREE_LONGITUDE;
        double h = Math.atan2(lonDist, latDist) * 180 / Math.PI;
        if (h < 0) {
			h += 360;
		}
        return h;
    }


    /**
     * Compares the specified Object with this GeoPoint for equality.
     *
     * @return gp != null && (gp instanceof GeoPoint) &&
     * gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
    public boolean equals(Object gp) {
        if (this == gp) return true;
        if (gp == null || !(gp instanceof GeoPoint)) return false;

        GeoPoint other = (GeoPoint) gp;
        return this.latitude == other.latitude && this.longitude == other.longitude;
    }


    /**
     * Returns a hash code value for this GeoPoint.
     *
     * @return a hash code value for this GeoPoint.
     **/
    public int hashCode() {
        return 31 * this.latitude + this.longitude;
    }


    /**
     * Returns a string representation of this GeoPoint.
     *
     * @return a string representation of this GeoPoint.
     **/
    public String toString() {
        double latDeg = latitude / 1_000_000.0;
        double lonDeg = longitude / 1_000_000.0;
        return String.format("(%.6f°, %.6f°)", latDeg, lonDeg);
    }

}

package homework1;

import java.text.DecimalFormat;

/**
 * A WalkingDirections class knows how to create a textual description of
 * directions from one location to another suitable for a pedestrian.
 * <p>
 * Calling <tt>computeDirections</tt> should produce directions in the
 * following form:
 * <p>
 * <tt>
 * Turn slight right onto Hankin Road and walk for 2 minutes.<br>
 * Turn slight right onto Trumpeldor Avenue and walk for 15 minutes.<br>
 * Turn left onto Hagalil and walk for 27 minutes.<br>
 * Turn sharp left onto Hanita and walk for 27 minutes.<br>
 * </tt>
 * <p>
 * Each line should correspond to a single geographic feature of the route.
 * In the first line, "Hankin Road" is the name of the first
 * geographic feature of the route, and "2 minutes" is the length of
 * time that it would take to walk along the geographic feature, assuming a
 * walking speed of 20 minutes per kilometer. The time in minutes should
 * be reported to the nearest minute. Each line should be terminated by a
 * newline and should include no extra spaces other than those shown above.
 **/
public class WalkingRouteFormatter extends RouteFormatter {

    /**
     * Computes a single line of a multi-line directions String that
     * represents the instructions for walking along a single geographic
     * feature.
     *
     * @param geoFeature  the geographical feature to traverse.
     * @param origHeading the initial heading.
     * @return A newline-terminated <tt>String</tt> that gives directions
     * on how to walk along this geographical feature.<br>
     * Calling <tt>computeLine</tt> with a GeoFeature instance and an
     * initial heading should produce a newline-terminated String in the
     * following form:
     * <p>
     * <tt>
     * Turn sharp left onto Hanita and walk for 27 minutes.<br>
     * </tt>
     * <p>
     * In the output above, "Hanita" represents the name of the
     * geographic feature, and "27 minutes" is the length of time that it
     * would take to walk along the geographic feature, assuming a walking
     * speed of 20 minutes per kilometer. The time in minutes should be
     * reported to the nearest minute. Each line should be terminated by a
     * newline and should include no extra spaces other than those shown
     * above.
     * @requires 0 <= origHeading < 360
     **/
    public String computeLine(GeoFeature geoFeature, double origHeading) {
        String turn = getTurnString(origHeading, geoFeature.getStartHeading());
        String name = geoFeature.getName();
        long minutes = Math.round(geoFeature.getLength() * 20); // 20 min/km
        return String.format("%s onto %s and walk for %d minutes.\n", turn, name, minutes);
    }
}

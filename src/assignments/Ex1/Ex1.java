package assignments.Ex1;

/**
 * This class represents a set of static methods on polynomial functions.
 * A polynomial is represented as an array of doubles where index = power.
 * Example: {0.1, 0, -3, 0.2} represents 0.2x^3 - 3x^2 + 0.1
 */
public class Ex1 {
    public static final double EPS = 0.001; // precision for comparisons
    public static final double[] ZERO = {0}; // zero polynomial

    /****************************************************
     * Function: f
     * Purpose : Computes the value of polynomial at point x.
     * Params  :
     *    double[] poly - array of coefficients
     *    double x      - point to evaluate
     * Returns : double - f(x) value
     ****************************************************/
    public static double f(double[] poly, double x) {
        double ans = 0;
        for(int i = 0; i < poly.length; i++) {
            ans += poly[i] * Math.pow(x, i); // coef[i] * x^i
        }
        return ans;
    }

    /****************************************************
     * Function: root_rec
     * Purpose : Finds root of polynomial in range [x1,x2] using binary search.
     *           assume f(x1) and f(x2) have opposite signs.
     * Params  :
     *    double[] p  - polynomial
     *    double x1   - range start
     *    double x2   - range end
     *    double eps  - precision
     * Returns : double - x value where f(x) ≈ 0
     ****************************************************/
    public static double root_rec(double[] p, double x1, double x2, double eps) {
        double f1 = f(p, x1);
        while(Math.abs(x2 - x1) > eps) { // keep narrowing until range is small enough
            double mid = (x1 + x2) * 0.5;
            double fMid = f(p, mid);

            if(Math.abs(fMid) < eps) return mid; // found root
            if(fMid * f1 < 0) {
                x2 = mid; // root in left half - signs differ
            }
            else {
                x1 = mid; // root in right half
                f1 = fMid;
            }
        }
        return (x1 + x2) * 0.5; // return midpoint of final range
    }

    /****************************************************
     * Function: PolynomFromPoints
     * Purpose : Creates polynomial passing through given points using Lagrange interpolation.
     *           Supports 2 points (linear) or 3 points (quadratic).
     * Params  :
     *    double[] xx - x coordinates
     *    double[] yy - y coordinates
     * Returns : double[] - polynomial coefficients array, null if invalid input
     ****************************************************/
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double[] ans = null;
        if(xx == null || yy == null || xx.length != yy.length || xx.length < 2 || xx.length > 3) return ans; // invalid input

        if(xx.length == 2) {
            double m = (yy[1] - yy[0]) / (xx[1] - xx[0]); // slope
            double b = yy[0] - m * xx[0]; // y-intercept
            ans = new double[]{b, m}; // y = mx + b -> {b, m}
        }
        else {
            // quadratic using Lagrange formula
            double x0 = xx[0], x1 = xx[1], x2 = xx[2];
            double y0 = yy[0], y1 = yy[1], y2 = yy[2];

            double a = y0 / ((x0 - x1) * (x0 - x2)) + y1 / ((x1 - x0) * (x1 - x2)) + y2 / ((x2 - x0) * (x2 - x1));
            double b = -y0 * (x1 + x2) / ((x0 - x1) * (x0 - x2)) - y1 * (x0 + x2) / ((x1 - x0) * (x1 - x2)) - y2 * (x0 + x1) / ((x2 - x0) * (x2 - x1));
            double c = y0 * x1 * x2 / ((x0 - x1) * (x0 - x2)) + y1 * x0 * x2 / ((x1 - x0) * (x1 - x2)) + y2 * x0 * x1 / ((x2 - x0) * (x2 - x1));

            ans = new double[]{c, b, a}; // y = ax^2 + bx + c -> {c, b, a}
        }
        return ans;
    }

    /****************************************************
     * Function: equals
     * Purpose : Checks if two polynomials are equal.
     *           Uses EPS tolerance for non-zero coefficients, exact match for zeros.
     * Params  :
     *    double[] p1 - first polynomial
     *    double[] p2 - second polynomial
     * Returns : boolean - true if equal, false otherwise
     ****************************************************/
    public static boolean equals(double[] p1, double[] p2) {
        int maxLen = Math.max(p1.length, p2.length);
        for(int i = 0; i < maxLen; i++) {
            double c1 = (i < p1.length) ? p1[i] : 0; // check bounds to avoid overflow
            double c2 = (i < p2.length) ? p2[i] : 0;
            if(c1 == 0 && c2 == 0) continue; // both zero - equal at this index
            if(c1 == 0 || c2 == 0) return false; // one zero one not - different degree
            if(Math.abs(c1 - c2) >= EPS) return false; // difference too big
        }
        return true;
    }

    /****************************************************
     * Function: poly
     * Purpose : Converts polynomial array to string format.
     * Params  :
     *    double[] poly - polynomial coefficients
     * Returns : String - representation like "2.0x^2 +1.0"
     ****************************************************/
    public static String poly(double[] poly) {
        if(poly.length == 0) return "0";

        String ans = "";
        for(int i = poly.length - 1; i >= 0; i--) { // highest to lowest degree
            if(poly[i] == 0) continue; // skip zero terms

            if(!ans.isEmpty()) {
                ans += (poly[i] >= 0) ? " +" : " "; // add sign between terms
            }

            if(i == 0) ans += poly[i]; // constant term
            else if(i == 1) ans += poly[i] + "x"; // linear term
            else ans += poly[i] + "x^" + i; // higher power term
        }
        if(ans.isEmpty()) ans = "0"; // all zeros case
        return ans;
    }

    /****************************************************
     * Function: subtract
     * Purpose : Returns p1 - p2 as new polynomial.
     * Params  :
     *    double[] p1 - first polynomial
     *    double[] p2 - second polynomial
     * Returns : double[] - difference polynomial
     ****************************************************/
    private static double[] subtract(double[] p1, double[] p2) {
        double[] diff = new double[Math.max(p1.length, p2.length)];
        for(int i = 0; i < diff.length; i++) {
            double c1 = (i < p1.length) ? p1[i] : 0; // check bounds to avoid overflow
            double c2 = (i < p2.length) ? p2[i] : 0;
            diff[i] = c1 - c2;
        }
        return diff;
    }

    /****************************************************
     * Function: parseCoef
     * Purpose : Parses coefficient from term string.
     *           Handles implicit coefficients (x = 1x, -x = -1x).
     * Params  :
     *    String term - string like "3x" or "-2x^2"
     * Returns : double - coefficient value
     ****************************************************/
    private static double parseCoef(String term) {
        String coefStr = term.substring(0, term.indexOf("x")).trim(); // get part before x
        if(coefStr.isEmpty() || coefStr.equals("+")) return 1; // implicit 1
        if(coefStr.equals("-")) return -1; // implicit -1
        return Double.parseDouble(coefStr);
    }

    /****************************************************
     * Function: sameValue
     * Purpose : Finds x where p1(x) = p2(x) in range [x1,x2].
     *           Finds root of (p1 - p2).
     * Params  :
     *    double[] p1 - first polynomial
     *    double[] p2 - second polynomial
     *    double x1   - range start
     *    double x2   - range end
     *    double eps  - precision
     * Returns : double - x value where both polynomials have same value
     ****************************************************/
    public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        return root_rec(subtract(p1, p2), x1, x2, eps);
    }

    /****************************************************
     * Function: length
     * Purpose : Computes arc length of polynomial curve from x1 to x2.
     *           Uses distance formula on small line segments.
     * Params  :
     *    double[] p         - polynomial
     *    double x1          - range start
     *    double x2          - range end
     *    int numberOfSegments - number of segments for approximation
     * Returns : double - approximate curve length
     ****************************************************/
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = 0;
        double step = (x2 - x1) / numberOfSegments;
        double prevX = x1;
        double prevY = f(p, x1);

        for(int i = 1; i <= numberOfSegments; i++) {
            double currX = x1 + i * step;
            double currY = f(p, currX);
            ans += Math.sqrt(Math.pow(currX - prevX, 2) + Math.pow(currY - prevY, 2)); // distance formula
            prevX = currX;
            prevY = currY;
        }
        return ans;
    }

    /****************************************************
     * Function: area
     * Purpose : Computes area between two polynomials from x1 to x2.
     *           Uses trapezoid rule, handles curve crossings by splitting at intersection.
     * Params  :
     *    double[] p1          - first polynomial
     *    double[] p2          - second polynomial
     *    double x1            - range start
     *    double x2            - range end
     *    int numberOfTrapezoid - number of trapezoids for approximation
     * Returns : double - area between curves
     ****************************************************/
    public static double area(double[] p1, double[] p2, double x1, double x2, int numberOfTrapezoid) {
        double ans = 0;
        double step = (x2 - x1) / numberOfTrapezoid;
        double[] diff = subtract(p1, p2);

        for(int i = 0; i < numberOfTrapezoid; i++) {
            double left = x1 + i * step;
            double right = left + step;
            double fLeft = f(diff, left);
            double fRight = f(diff, right);

            if(fLeft * fRight < 0) { // sign change means curves cross
                double cross = root_rec(diff, left, right, EPS); // find intersection
                ans += Math.abs(fLeft) / 2 * (cross - left); // left triangle area
                ans += Math.abs(fRight) / 2 * (right - cross); // right triangle area
            }
            else {
                ans += (Math.abs(fLeft) + Math.abs(fRight)) / 2 * step; // trapezoid: (a+b)/2 * h
            }
        }
        return ans;
    }

    /****************************************************
     * Function: getPolynomFromString
     * Purpose : Parses string to polynomial array.
     *           Handles formats like "x", "-x^2", "3", "2.5x^3".
     * Params  :
     *    String p - string like "2x^2 +3x -1"
     * Returns : double[] - polynomial coefficients array
     ****************************************************/
    public static double[] getPolynomFromString(String p) {
        if(p == null || p.trim().isEmpty() || p.trim().equals("0")) return ZERO;

        int maxDeg = 0;
        String[] terms = p.replace("-", "+-").split("\\+"); // split keeping negative signs

        // first pass: find max degree
        for(String term : terms) {
            term = term.trim();
            if(term.isEmpty()) continue;

            if(term.contains("x^")) {
                int deg = Integer.parseInt(term.substring(term.indexOf("^") + 1).trim()); // get power after ^
                maxDeg = Math.max(maxDeg, deg);
            }
            else if(term.contains("x")) {
                maxDeg = Math.max(maxDeg, 1); // x means x^1
            }
        }

        double[] ans = new double[maxDeg + 1];

        // second pass: fill coefficients
        for(String term : terms) {
            term = term.trim();
            if(term.isEmpty()) continue;

            if(term.contains("x^")) {
                int deg = Integer.parseInt(term.substring(term.indexOf("^") + 1).trim());
                ans[deg] = parseCoef(term);
            }
            else if(term.contains("x")) {
                ans[1] = parseCoef(term); // linear term goes to index 1
            }
            else {
                ans[0] = Double.parseDouble(term); // constant goes to index 0
            }
        }
        return ans;
    }

    /****************************************************
     * Function: add
     * Purpose : Returns p1 + p2 as new polynomial.
     * Params  :
     *    double[] p1 - first polynomial
     *    double[] p2 - second polynomial
     * Returns : double[] - sum polynomial
     ****************************************************/
    public static double[] add(double[] p1, double[] p2) {
        double[] ans = new double[Math.max(p1.length, p2.length)];
        for(int i = 0; i < ans.length; i++) {
            double c1 = (i < p1.length) ? p1[i] : 0; // check bounds to avoid overflow
            double c2 = (i < p2.length) ? p2[i] : 0;
            ans[i] = c1 + c2;
        }
        return ans;
    }

    /****************************************************
     * Function: mul
     * Purpose : Returns p1 * p2 as new polynomial.
     *           Degree of result = deg(p1) + deg(p2).
     * Params  :
     *    double[] p1 - first polynomial
     *    double[] p2 - second polynomial
     * Returns : double[] - product polynomial
     ****************************************************/
    public static double[] mul(double[] p1, double[] p2) {
        if(p1.length == 1 && p1[0] == 0) return ZERO; // 0 * anything = 0
        if(p2.length == 1 && p2[0] == 0) return ZERO;

        double[] ans = new double[p1.length + p2.length - 1];
        for(int i = 0; i < p1.length; i++) {
            for(int j = 0; j < p2.length; j++) {
                ans[i + j] += p1[i] * p2[j]; // x^i * x^j = x^(i+j)
            }
        }
        return ans;
    }

    /****************************************************
     * Function: derivative
     * Purpose : Returns derivative of polynomial.
     *           Uses power rule: d/dx(ax^n) = n*a*x^(n-1).
     * Params  :
     *    double[] po - polynomial to differentiate
     * Returns : double[] - coefficients of the derivative polynomial
     ****************************************************/
    public static double[] derivative(double[] po) {
        if(po.length <= 1) return ZERO; // derivative of constant is 0

        double[] ans = new double[po.length - 1];
        for(int i = 1; i < po.length; i++) {
            ans[i - 1] = po[i] * i;
        }
        return ans;
    }
}
package assignments.Ex1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
    static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
    static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
    static double[] po3 = {2,1,-0.7, -0.02,0.02};
    static double[] po4 = {-3, 0.61, 0.2};

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testF() {
        double fx0 = Ex1.f(po1, 0);
        double fx1 = Ex1.f(po1, 1);
        double fx2 = Ex1.f(po1, 2);
        assertEquals(fx0, 2, Ex1.EPS);
        assertEquals(fx1, 4, Ex1.EPS);
        assertEquals(fx2, 6, Ex1.EPS);
    }
    @Test
    /**
     * Tests that p1(x) + p2(x) == (p1+p2)(x)
     */
    void testF2() {
        double x = Math.PI;
        double[] po12 = Ex1.add(po1, po2);
        double f1x = Ex1.f(po1, x);
        double f2x = Ex1.f(po2, x);
        double f12x = Ex1.f(po12, x);
        assertEquals(f1x + f2x, f12x, Ex1.EPS);
    }
    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testAdd() {
        double[] p12 = Ex1.add(po1, po2);
        double[] minus1 = {-1};
        double[] pp2 = Ex1.mul(po2, minus1);
        double[] p1 = Ex1.add(p12, pp2);
        assertTrue(Ex1.equals(p1, po1));
    }
    @Test
    /**
     * Tests that p1+p2 == p2+p1
     */
    void testAdd2() {
        double[] p12 = Ex1.add(po1, po2);
        double[] p21 = Ex1.add(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }
    @Test
    /**
     * Tests that p1+0 == p1
     */
    void testAdd3() {
        double[] p1 = Ex1.add(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, po1));
    }
    @Test
    /**
     * Tests that p1*0 == 0
     */
    void testMul1() {
        double[] p1 = Ex1.mul(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, Ex1.ZERO));
    }
    @Test
    /**
     * Tests that p1*p2 == p2*p1
     */
    void testMul2() {
        double[] p12 = Ex1.mul(po1, po2);
        double[] p21 = Ex1.mul(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }
    @Test
    /**
     * Tests that p1(x) * p2(x) = (p1*p2)(x),
     */
    void testMulDoubleArrayDoubleArray() {
        double[] xx = {0,1,2,3,4.1,-15.2222};
        double[] p12 = Ex1.mul(po1, po2);
        for(int i = 0;i<xx.length;i=i+1) {
            double x = xx[i];
            double f1x = Ex1.f(po1, x);
            double f2x = Ex1.f(po2, x);
            double f12x = Ex1.f(p12, x);
            assertEquals(f12x, f1x*f2x, Ex1.EPS);
        }
    }
    @Test
    /**
     * Tests a simple derivative examples - till ZERO.
     */
    void testDerivativeArrayDoubleArray() {
        double[] p = {1,2,3}; // 3X^2+2x+1
        double[] pt = {2,6}; // 6x+2
        double[] dp1 = Ex1.derivative(p); // 2x + 6
        double[] dp2 = Ex1.derivative(dp1); // 2
        double[] dp3 = Ex1.derivative(dp2); // 0
        double[] dp4 = Ex1.derivative(dp3); // 0
        assertTrue(Ex1.equals(dp1, pt));
        assertTrue(Ex1.equals(Ex1.ZERO, dp3));
        assertTrue(Ex1.equals(dp4, dp3));
    }
    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */
    public void testFromString() {
        double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if(!isSame1) {fail();}
        if(!isSame2) {fail();}
        assertEquals(sp, Ex1.poly(p1));
    }
    @Test
    /**
     * Tests the equality of pairs of arrays.
     */
    public void testEquals() {
        double[][] d1 = {{0}, {1}, {1,2,0,0}};
        double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
        double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
        for(int i=0;i<d1.length;i=i+1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for(int i=0;i<d1.length;i=i+1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue2() {
        double x1=-4, x2=0;
        double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
        assertEquals(rs1,rs2, Ex1.EPS);
    }
    @Test
    /**
     * Test the area function - it should be symmetric.
     */
    public void testArea() {
        double x1=-4, x2=0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1,a2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */
    public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0,1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
        double area =2.5;
        assertEquals(a1,area, Ex1.EPS);
        assertEquals(a2,area, Ex1.EPS);
        assertEquals(a3,area, Ex1.EPS);
        assertEquals(a100,area, Ex1.EPS);
    }
    @Test
    /**
     * Test the area function.
     */
    public void testArea3() {
        double[] po_a = {2,1,-0.7, -0.02,0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
        double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(a1,area, Ex1.EPS);
    }

    // ==================== ADDITIONAL TESTS ====================

    @Test
    /**
     * Test f with quadratic: p(x) = x^2 - 4
     */
    void testFQuadratic() {
        double[] p = {-4, 0, 1}; // x^2 - 4
        assertEquals(-4, Ex1.f(p, 0), Ex1.EPS);  // 0 - 4 = -4
        assertEquals(-3, Ex1.f(p, 1), Ex1.EPS);  // 1 - 4 = -3
        assertEquals(0, Ex1.f(p, 2), Ex1.EPS);   // 4 - 4 = 0
        assertEquals(5, Ex1.f(p, 3), Ex1.EPS);   // 9 - 4 = 5
    }

    @Test
    /**
     * Test f with cubic: p(x) = x^3
     */
    void testFCubic() {
        double[] p = {0, 0, 0, 1}; // x^3
        assertEquals(0, Ex1.f(p, 0), Ex1.EPS);
        assertEquals(1, Ex1.f(p, 1), Ex1.EPS);
        assertEquals(8, Ex1.f(p, 2), Ex1.EPS);
        assertEquals(-8, Ex1.f(p, -2), Ex1.EPS);
    }

    @Test
    /**
     * Test add with specific polynomials
     */
    void testAddSpecific() {
        double[] p1 = {1, 2, 3};    // 3x^2 + 2x + 1
        double[] p2 = {4, 5};       // 5x + 4
        double[] sum = Ex1.add(p1, p2);
        double[] expected = {5, 7, 3}; // 3x^2 + 7x + 5
        assertTrue(Ex1.equals(sum, expected));
    }

    @Test
    /**
     * Test mul with (x+1)(x-1) = x^2 - 1
     */
    void testMulDifferenceOfSquares() {
        double[] p1 = {1, 1};   // x + 1
        double[] p2 = {-1, 1};  // x - 1
        double[] prod = Ex1.mul(p1, p2);
        double[] expected = {-1, 0, 1}; // x^2 - 1
        assertTrue(Ex1.equals(prod, expected));
    }

    @Test
    /**
     * Test mul with (x+2)^2 = x^2 + 4x + 4
     */
    void testMulSquare() {
        double[] p = {2, 1}; // x + 2
        double[] prod = Ex1.mul(p, p);
        double[] expected = {4, 4, 1}; // x^2 + 4x + 4
        assertTrue(Ex1.equals(prod, expected));
    }

    @Test
    /**
     * Test derivative of x^3 + 2x^2 + x = 3x^2 + 4x + 1
     */
    void testDerivativeCubic() {
        double[] p = {0, 1, 2, 1}; // x^3 + 2x^2 + x
        double[] dp = Ex1.derivative(p);
        double[] expected = {1, 4, 3}; // 3x^2 + 4x + 1
        assertTrue(Ex1.equals(dp, expected));
    }

    @Test
    /**
     * Test derivative of constant is zero
     */
    void testDerivativeConstant() {
        double[] p = {5};
        double[] dp = Ex1.derivative(p);
        assertTrue(Ex1.equals(dp, Ex1.ZERO));
    }

    @Test
    /**
     * Test root_rec finds root of x-3=0 at x=3
     */
    void testRootLinear() {
        double[] p = {-3, 1}; // x - 3
        double root = Ex1.root_rec(p, 0, 5, Ex1.EPS);
        assertEquals(3, root, Ex1.EPS);
    }

    @Test
    /**
     * Test root_rec finds root of x^2-4=0 at x=2
     */
    void testRootQuadratic() {
        double[] p = {-4, 0, 1}; // x^2 - 4
        double root = Ex1.root_rec(p, 0, 3, Ex1.EPS);
        assertEquals(2, root, Ex1.EPS);
    }

    @Test
    /**
     * Test PolynomFromPoints with 2 points: line through (0,0) and (1,1)
     */
    void testPolynomFromPoints2() {
        double[] xx = {0, 1};
        double[] yy = {0, 1};
        double[] p = Ex1.PolynomFromPoints(xx, yy);
        double[] expected = {0, 1}; // y = x
        assertTrue(Ex1.equals(p, expected));
    }

    @Test
    /**
     * Test PolynomFromPoints with 3 points on y = x^2
     */
    void testPolynomFromPoints3() {
        double[] xx = {0, 1, 2};
        double[] yy = {0, 1, 4}; // y = x^2
        double[] p = Ex1.PolynomFromPoints(xx, yy);
        // Should pass through all points
        assertEquals(0, Ex1.f(p, 0), Ex1.EPS);
        assertEquals(1, Ex1.f(p, 1), Ex1.EPS);
        assertEquals(4, Ex1.f(p, 2), Ex1.EPS);
    }

    @Test
    /**
     * Test getPolynomFromString with simple cases
     */
    void testGetPolynomFromStringSimple() {
        double[] p1 = Ex1.getPolynomFromString("x");
        assertTrue(Ex1.equals(p1, new double[]{0, 1}));

        double[] p2 = Ex1.getPolynomFromString("5");
        assertTrue(Ex1.equals(p2, new double[]{5}));

        double[] p3 = Ex1.getPolynomFromString("x^2");
        assertTrue(Ex1.equals(p3, new double[]{0, 0, 1}));
    }

    @Test
    /**
     * Test length of horizontal line y=3 from x=0 to x=5 is 5
     */
    void testLengthHorizontal() {
        double[] p = {3}; // y = 3
        double len = Ex1.length(p, 0, 5, 100);
        assertEquals(5, len, Ex1.EPS);
    }

    @Test
    /**
     * Test area between y=0 and y=2 from x=0 to x=3 is 6
     */
    void testAreaRectangle() {
        double[] p1 = {0};
        double[] p2 = {2};
        double area = Ex1.area(p1, p2, 0, 3, 100);
        assertEquals(6, area, Ex1.EPS);
    }

    @Test
    /**
     * Test sameValue finds intersection of y=x and y=2
     */
    void testSameValueSimple() {
        double[] p1 = {0, 1}; // y = x
        double[] p2 = {2};    // y = 2
        double x = Ex1.sameValue(p1, p2, 0, 5, Ex1.EPS);
        assertEquals(2, x, Ex1.EPS); // they meet at x=2
    }

    @Test
    /**
     * Test poly string output
     */
    void testPolyString() {
        double[] p = {0, 0, 1}; // x^2
        String s = Ex1.poly(p);
        assertTrue(s.contains("x^2"));
    }

    // ==================== SUBTRACT TESTS (using add with -1 multiplication) ====================

    @Test
    /**
     * Test subtract: p - p = 0 (using p + (-1)*p)
     */
    void testSubtractSelf() {
        double[] p = {3, 2, 1}; // x^2 + 2x + 3
        double[] minusOne = {-1};
        double[] diff = Ex1.add(p, Ex1.mul(p, minusOne));
        assertTrue(Ex1.equals(diff, Ex1.ZERO));
    }

    @Test
    /**
     * Test subtract: p - 0 = p
     */
    void testSubtractZero() {
        double[] p = {5, -2, 3};
        double[] minusOne = {-1};
        double[] diff = Ex1.add(p, Ex1.mul(Ex1.ZERO, minusOne));
        assertTrue(Ex1.equals(diff, p));
    }

    @Test
    /**
     * Test subtract: 0 - p = -p
     */
    void testSubtractFromZero() {
        double[] p = {2, 3};
        double[] minusOne = {-1};
        double[] diff = Ex1.add(Ex1.ZERO, Ex1.mul(p, minusOne));
        double[] expected = {-2, -3};
        assertTrue(Ex1.equals(diff, expected));
    }

    @Test
    /**
     * Test subtract with specific polynomials: p1 - p2
     */
    void testSubtractSpecific() {
        double[] p1 = {5, 4, 3}; // 3x^2 + 4x + 5
        double[] p2 = {1, 2, 1}; // x^2 + 2x + 1
        double[] minusOne = {-1};
        double[] diff = Ex1.add(p1, Ex1.mul(p2, minusOne));
        double[] expected = {4, 2, 2}; // 2x^2 + 2x + 4
        assertTrue(Ex1.equals(diff, expected));
    }

    @Test
    /**
     * Test subtract with different lengths
     */
    void testSubtractDifferentLengths() {
        double[] p1 = {1, 2, 3, 4}; // 4x^3 + 3x^2 + 2x + 1
        double[] p2 = {1, 1};       // x + 1
        double[] minusOne = {-1};
        double[] diff = Ex1.add(p1, Ex1.mul(p2, minusOne));
        double[] expected = {0, 1, 3, 4}; // 4x^3 + 3x^2 + x
        assertTrue(Ex1.equals(diff, expected));
    }

    @Test
    /**
     * Test subtract: (p1-p2)(x) = p1(x) - p2(x)
     */
    void testSubtractEvaluation() {
        double[] p1 = {2, 3, 1};
        double[] p2 = {1, -1, 2};
        double[] minusOne = {-1};
        double[] diff = Ex1.add(p1, Ex1.mul(p2, minusOne));
        double x = 2.5;
        double expected = Ex1.f(p1, x) - Ex1.f(p2, x);
        assertEquals(expected, Ex1.f(diff, x), Ex1.EPS);
    }

    // ==================== MORE ADDITIONAL TESTS ====================

    @Test
    /**
     * Test f with negative x values
     */
    void testFNegativeX() {
        double[] p = {1, 1, 1}; // x^2 + x + 1
        assertEquals(3, Ex1.f(p, -2), Ex1.EPS);  // 4 - 2 + 1 = 3
        assertEquals(1, Ex1.f(p, -1), Ex1.EPS);  // 1 - 1 + 1 = 1
    }

    @Test
    /**
     * Test mul with identity: p * 1 = p
     */
    void testMulIdentity() {
        double[] p = {3, -2, 5};
        double[] one = {1};
        double[] prod = Ex1.mul(p, one);
        assertTrue(Ex1.equals(prod, p));
    }

    @Test
    /**
     * Test mul with scalar: p * 2
     */
    void testMulScalar() {
        double[] p = {1, 2, 3};
        double[] two = {2};
        double[] prod = Ex1.mul(p, two);
        double[] expected = {2, 4, 6};
        assertTrue(Ex1.equals(prod, expected));
    }

    @Test
    /**
     * Test derivative of linear: d/dx(3x + 2) = 3
     */
    void testDerivativeLinear() {
        double[] p = {2, 3}; // 3x + 2
        double[] dp = Ex1.derivative(p);
        double[] expected = {3};
        assertTrue(Ex1.equals(dp, expected));
    }

    @Test
    /**
     * Test root_rec with negative root
     */
    void testRootNegative() {
        double[] p = {2, 1}; // x + 2 = 0 -> x = -2
        double root = Ex1.root_rec(p, -5, 0, Ex1.EPS);
        assertEquals(-2, root, Ex1.EPS);
    }

    @Test
    /**
     * Test equals with trailing zeros
     */
    void testEqualsTrailingZeros() {
        double[] p1 = {1, 2, 3};
        double[] p2 = {1, 2, 3, 0, 0};
        assertTrue(Ex1.equals(p1, p2));
    }

    @Test
    /**
     * Test PolynomFromPoints: horizontal line y = 5
     */
    void testPolynomFromPointsHorizontal() {
        double[] xx = {0, 3};
        double[] yy = {5, 5};
        double[] p = Ex1.PolynomFromPoints(xx, yy);
        assertEquals(5, Ex1.f(p, 0), Ex1.EPS);
        assertEquals(5, Ex1.f(p, 3), Ex1.EPS);
        assertEquals(5, Ex1.f(p, 100), Ex1.EPS); // should be 5 everywhere
    }

    @Test
    /**
     * Test length of y=x from 0 to 1 is sqrt(2)
     */
    void testLengthDiagonal() {
        double[] p = {0, 1}; // y = x
        double len = Ex1.length(p, 0, 1, 1000);
        assertEquals(Math.sqrt(2), len, 0.01);
    }

    @Test
    /**
     * Test area of triangle: y=0 and y=x from 0 to 2, area = 2
     */
    void testAreaTriangle() {
        double[] p1 = {0};    // y = 0
        double[] p2 = {0, 1}; // y = x
        double area = Ex1.area(p1, p2, 0, 2, 100);
        assertEquals(2, area, Ex1.EPS); // triangle: (1/2) * 2 * 2 = 2
    }
}
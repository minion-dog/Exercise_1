![GUI graph](https://github.com/user-attachments/assets/5e350cff-4877-42f5-878b-9e8093dbcb5a)Pseudo Code
Function: f(poly, x)
What it does: Computes the polynomial value at point x. Each coefficient is multiplied by x raised to the power of its index.
The idea: Loop through all coefficients in the array. Each coefficient at index i represents the coefficient of x^i, so we just sum up all the products.
Function: root_rec(p, x1, x2, eps)
What it does: Finds a root of the polynomial in a given range using binary search.
The idea: If the signs at the endpoints are opposite, there's a root in between. Calculate the value at the midpoint and check which half has the sign change - that's where the root is. Keep narrowing until the range is small enough.
Function: PolynomFromPoints(xx, yy)
What it does: Creates a polynomial that passes through 2 or 3 given points.
The idea: I used the link the teacher provided about Lagrange interpolation. For 2 points it's simply a line equation, for 3 points I used the Lagrange formula for a quadratic polynomial and arranged the coefficients in order in the array.
Function: equals(p1, p2)
What it does: Checks if two polynomials are equal with EPS tolerance.
The idea: Go through all coefficients and compare. If both are zero - equal. If only one is zero - different. Otherwise check that the difference is less than EPS.
Function: poly(poly)
What it does: Converts a coefficient array to a readable string like "2x^2 +3x -1".
The idea: Go from highest power to lowest, skip coefficients that are 0, and add the appropriate sign between terms. Handle constant and linear terms separately.
Helper Function: subtract(p1, p2)
What it does: Computes p1 - p2 and returns a new polynomial.
The idea: I noticed that polynomial subtraction repeats itself in several places in the code (in sameValue and area), so I extracted it to a separate function for readability and reuse.
Helper Function: parseCoef(term)
What it does: Extracts the coefficient from a term string like "3x" or "-2x^2".
The idea: Take what's before the x. If empty or just + it's 1, if just - it's -1, otherwise convert to number.
Function: sameValue(p1, p2, x1, x2, eps)
What it does: Finds a point where two polynomials have the same value.
The idea: If p1(x) = p2(x) then p1(x) - p2(x) = 0, meaning we just need to find a root of the difference. Use subtract and then root_rec.
Function: length(p, x1, x2, numberOfSegments)
What it does: Computes the arc length of the polynomial curve between two points.
The idea: Divide the range into small segments and sum up the distances between them using the distance formula. The more segments, the more accurate the approximation.
Function: area(p1, p2, x1, x2, numberOfTrapezoid)
What it does: Computes the area between two polynomial curves.
The idea: Use the trapezoid method on the difference between the polynomials. If the curves cross in the middle of a segment, find the intersection point and calculate each part separately.
Function: getPolynomFromString(p)
What it does: Converts a string like "2x^2 +3x -1" to a coefficient array.
The idea: Split the string into terms by +, first find the highest degree to know the array size, then fill each coefficient in its correct position.
Function: add(p1, p2)
What it does: Adds two polynomials and returns a new polynomial.
The idea: Simply add coefficient against coefficient at the same power. If one array is shorter, treat missing coefficients as zeros.
Function: mul(p1, p2)
What it does: Multiplies two polynomials and returns a new polynomial.
The idea: Each term from the first polynomial is multiplied by each term from the second. When multiplying x^i by x^j we get x^(i+j), so the coefficient is added to position i+j in the result array.
Function: derivative(po)


![GUI graph](https://github.com/user-attachments/assets/398ab809-eada-4ff7-b9a6-93f74fea75bf)

What it does: Computes the derivative of a polynomial.
The idea: According to the power rule: the derivative of ax^n is n*a*x^(n-1). So each coefficient is multiplied by its index and drops one power.

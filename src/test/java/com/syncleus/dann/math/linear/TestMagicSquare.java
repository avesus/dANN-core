/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

/*
 * Derived from Public-Domain source as indicated at
 * http://math.nist.gov/javanumerics/jama/ as of 9/13/2009.
 */
package com.syncleus.dann.math.linear;

import com.syncleus.dann.math.RealNumber;
import com.syncleus.dann.math.linear.decomposition.*;
import org.junit.*;

import java.util.List;

public class TestMagicSquare {
    public static SimpleRealMatrix magic(final int n) {
        final double[][] M = new double[n][n];
        // Odd order
        if ((n % 2) == 1) {
            final int a = (n + 1) / 2;
            final int b = (n + 1);
            for (int j = 0; j < n; j++)
                for (int i = 0; i < n; i++)
                    M[i][j] = n * ((i + j + a) % n) + ((i + 2 * j + b) % n) + 1;
            // Doubly Even Order
        }
        else if ((n % 4) == 0)
            for (int j = 0; j < n; j++)
                for (int i = 0; i < n; i++)
                    if (((i + 1) / 2) % 2 == ((j + 1) / 2) % 2)
                        M[i][j] = n * n - n * i - j;
                    else
                        M[i][j] = n * i + j + 1;
        else {
            final int p = n / 2;
            final int k = (n - 2) / 4;
            final SimpleRealMatrix A = magic(p);
            for (int j = 0; j < p; j++)
                for (int i = 0; i < p; i++) {
                    final double aij = A.getDouble(i, j);
                    M[i][j] = aij;
                    M[i][j + p] = aij + 2 * p * p;
                    M[i + p][j] = aij + 3 * p * p;
                    M[i + p][j + p] = aij + p * p;
                }
            for (int i = 0; i < p; i++) {
                for (int j = 0; j < k; j++) {
                    final double t = M[i][j];
                    M[i][j] = M[i + p][j];
                    M[i + p][j] = t;
                }
                for (int j = n - k + 1; j < n; j++) {
                    final double t = M[i][j];
                    M[i][j] = M[i + p][j];
                    M[i + p][j] = t;
                }
            }
            double t = M[k][0];
            M[k][0] = M[k + p][0];
            M[k + p][0] = t;
            t = M[k][k];
            M[k][k] = M[k + p][k];
            M[k + p][k] = t;
        }
        return new SimpleRealMatrix(M);
    }

    public static boolean checkValues(final double value1, final double value2) {
        return checkValues(value1, value2, 0.01);
    }

    public static boolean checkValues(final double value1, final double value2, final double error) {
        if (Math.abs(value1 - value2) < error)
            return true;
        return (Double.isInfinite(value1) && Double.isInfinite(value2));
    }

    @Test
    public void testMagicSquare() {
        /*
		| Tests LU, QR, SVD and symmetric Eig decompositions.
		|
		|   n       = order of magic square.
		|   trace   = diagonal sum, should be the magic sum, (n^3 + n)/2.
		|   max_eig = maximum eigenvalue of (A + A')/2, should equal trace.
		|   rank    = linear algebraic rank,
		|             should equal n if n is odd, be less than n if n is even.
		|   cond    = L_2 condition number, ratio of singular values.
		|   lu_res  = test of LU factorization, norm1(L*U-A(p,:))/(n*eps).
		|   qr_res  = test of QR factorization, norm1(Q*R-A)/(n*eps).
		 */
        final double eps = Math.pow(2.0, -52.0);
        final double[][] solutions = new double[][]
                                             {
                                                     {15.0, 3.0, 4.33, 0.0, 3.333},
                                                     {34.0, 3.0, Double.POSITIVE_INFINITY, 0.0, 20.5},
                                                     {65.0, 5.0, 5.462, 0.0, 9.6},
                                                     {111.0, 5.0, Double.POSITIVE_INFINITY, 5.333, 22.667},
                                                     {175.0, 7.0, 7.111, 2.286, 49.143},
                                                     {260.0, 3.0, Double.POSITIVE_INFINITY, 0.0, 58.0},
                                                     {369.0, 9.0, 9.102, 7.111, 56.889},
                                                     {505.0, 7.0, Double.POSITIVE_INFINITY, 3.200, 121.600},
                                                     {671.0, 11.0, 11.102, 2.909, 78.545},
                                                     {870.0, 3.0, Double.POSITIVE_INFINITY, 0.0, 228.000},
                                                     {1105.0, 13.0, 13.060, 4.923, 196.923},
                                                     {1379.0, 9.0, Double.POSITIVE_INFINITY, 4.571, 165.143},
                                                     {1695.0, 15.0, 15.062, 4.267, 204.800},
                                                     {2056.0, 3.0, Double.POSITIVE_INFINITY, 0.0, 216.500},
                                                     {2465.0, 17.0, 17.042, 7.529, 692.706},
                                                     {2925.0, 11.0, Double.POSITIVE_INFINITY, 7.111, 421.778},
                                                     {3439.0, 19.0, 19.048, 16.842, 448.000},
                                                     {4010.0, 3.0, Double.POSITIVE_INFINITY, 14.400, 891.200},
                                                     {4641.0, 21.0, 21.035, 6.095, 563.048},
                                                     {5335.0, 13.0, Double.POSITIVE_INFINITY, 6.545, 570.182},
                                                     {6095.0, 23.0, 23.037, 11.130, 911.304},
                                                     {6924.0, 3.0, Double.POSITIVE_INFINITY, 10.667, 1079.333},
                                                     {7825.0, 25.0, 25.029, 35.840, 806.400},
                                                     {8801.0, 15.0, Double.POSITIVE_INFINITY, 4.923, 1226.462},
                                                     {9855.0, 27.0, 27.032, 37.926, 790.519},
                                                     {10990.0, 3.0, Double.POSITIVE_INFINITY, 34.286, 800.000},
                                                     {12209.0, 29.0, 29.025, 30.897, 1144.276},
                                                     {13515.0, 17.0, Double.POSITIVE_INFINITY, 8.533, 2257.067},
                                                     {14911.0, 31.0, 31.027, 33.032, 1212.903},
                                                     {16400.0, 3.0, Double.POSITIVE_INFINITY, 0.0, 1255.500}
                                             };
        for (int n = 3; n <= 32; n++) {
            final SimpleRealMatrix currentMatrix = magic(n);
            final double t = currentMatrix.trace();
            Assert.assertTrue("incorrect trace! obtained: " + t + " expects: " + solutions[n - 3][0] + " on matrix: " + currentMatrix, checkValues(t, solutions[n - 3][0]));
            final EigenvalueDecomposition currentEigen = Decompositions.createEigenvalueDecomposition(currentMatrix.add(currentMatrix.transpose()).multiply(0.5));
            final List<RealNumber> d = currentEigen.getRealEigenvalues();
            Assert.assertTrue("incorrect maximum eigen! obtained: " + d.get(n - 1).getValue() + " expected: " + solutions[n - 3][0] + " on matrix: " + currentMatrix, checkValues(d.get(n - 1).getValue(), solutions[n - 3][0], 0.01));
            final int r = currentMatrix.rank();
            Assert.assertTrue("incorrect rank!", checkValues(r, solutions[n - 3][1]));
            double c = currentMatrix.cond();
            if (c >= 1 / eps)
                c = Double.POSITIVE_INFINITY;
            Assert.assertTrue("incorrect cond!", checkValues(c, solutions[n - 3][2]));
            final LuDecomposition<RealMatrix, RealNumber> LU = new DoolittleLuDecomposition<RealMatrix, RealNumber>(currentMatrix);
            final RealMatrix L = LU.getLowerTriangularFactor();
            final RealMatrix U = LU.getUpperTriangularFactor();
            final int[] p = LU.getPivot();
            SimpleRealMatrix R = (SimpleRealMatrix) L.multiply(U).subtract(currentMatrix.getSubmatrix(p, 0, n - 1));
            double res = R.norm1Double() / (n * eps);
            Assert.assertTrue("incorrect lu_res!", checkValues(res, solutions[n - 3][3]));
            final QrDecomposition<RealMatrix, RealNumber> QR = new HouseholderQrDecomposition<RealMatrix, RealNumber>(currentMatrix);
            final RealMatrix Q = QR.getOrthogonalFactor();
            R = (SimpleRealMatrix) QR.getUpperTriangularFactor();
            R = (SimpleRealMatrix) Q.multiply(R).subtract(currentMatrix);
            res = R.norm1Double() / (n * eps);
            Assert.assertTrue("incorrect qr_res! obtained: " + res + " expects: " + solutions[n - 3][4] + " on matrix: " + currentMatrix, checkValues(res, solutions[n - 3][4]));
        }
    }
}


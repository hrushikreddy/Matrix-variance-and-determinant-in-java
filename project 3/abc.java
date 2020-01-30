/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

import java.util.Arrays;
import java.util.Random;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first dimension of the array.
     * @param n  The second dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {
        // You need to fill in this method.
		super(m,n);
		data = new double [m][n]; // creating a new 2D array of doubles and assigning them to the instance variable
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {
		super(A.m,A.n);
		data = new double[A.m][A.n]; // New array of suitable length
		for (int i = 0; i < A.m; i++) {
			for (int j = 0; j < A.n; j++) {
				data[i][j] = A.data[i][j]; // Simple for loop iterating over elements of matrix A and copying to instance variable
			}
		}
		// You need to fill in this method.
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        // You need to fill in this method. 
		// Only allows user to grab values from valid positions
		if (i >= m || j >= n) { 
			throw new MatrixException("Out of Bounds");
		}
		double num = data[i][j];
		return num; // returning element of data array in (i, j)th position
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        // You need to fill in this method.
		// Only allows user to input values to valid positions
		if (i >= m || j >= n) { 
			throw new MatrixException("Out of Bounds");
		}
		data[i][j] = val; // Setting (i, j)th position with value 'val'
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.
		double[] d = new double [1]; // creating array of length for decomp method
		GeneralMatrix decomp = decomp(d); 
		double mysum = 1.0;
		for (int i = 0; i < m; i++) {
			mysum *= decomp.data[i][i]; // multiplying leading diagonals
		}
		//System.out.println(d[0]);
		return mysum * d[0]; // returning determinant with appropiate sign
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {
        // You need to fill in this method.
		if (A.m != m || A.n != n) { // matrix additio only defined for matrices of the same size
			throw new MatrixException("Matrices are not the same size.");
		}
		Matrix summed = new GeneralMatrix(m, n);
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				summed.setIJ(i, j, getIJ(i, j) + A.getIJ(i, j));
				// for loop adding (i, j)th element of A to (i, j)th element of our matrix
			}
		}		
		
		return summed;
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        // You need to fill in this method.
		if (n != A.m) {
			throw new MatrixException("Matrices are not compatible for multiplication.");
			// Matrix multiplication only defined for coorect sized matrices
		}
		double [][] multi = new double[m][A.n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < A.n; j++) {
				for (int k = 0; k < n; k++) {
					multi[i][j] += data[i][k] * A.getIJ(k,j);
					// iterating over all elements and multiplying correct elements
				}	
			}
		}
		// System.out.println(Arrays.deepToString(multi));
		Matrix mult = new GeneralMatrix(m, A.n);
		for (int v = 0; v < m; v++) {
			for (int w = 0; w < A.n; w++) {
				mult.setIJ(v, w, multi[v][w]); // setting all elements of the resulting matrix  
			}
		}		
		return mult;
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        // You need to fill in this method.
		Matrix scamult = new GeneralMatrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				scamult.setIJ(i, j, getIJ(i, j) * a); // multiplying all elements in the matrix by the scalar double a
			}
		}		
		return scamult;

    }

    /**
     * Returns a matrix containing random numbers which are uniformly
     * distributed between 0 and 1.
     *
     * @param n  The first dimension of the matrix.
     * @param m  The second dimension of the matrix.
     */
    public void random() {
        // You need to fill in this method.
		Random rand = new Random(); // creating new instance of random from math library
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				data[i][j] = rand.nextDouble(); // setting all (i, j) elements with random soubles distributed between 0 and 1.
			}
		}
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * d[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which you can use to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        // This method is complete. You should not even attempt to change it!!
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        d[0] = 1.0;
        
        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // You need to fill in this method.
		GeneralMatrix g = new GeneralMatrix(3, 3);
		g.random();
		g.setIJ(2, 2, 10);
		double index = g.getIJ(2, 2);
		GeneralMatrix h = new GeneralMatrix(g);
		Matrix j = g.add(h);
		Matrix k = g.multiply(10);
		Matrix l = h.multiply(-0.5);
		
		System.out.println("Element at position (2, 2):\n"+index);
		System.out.println("MATRIX A:\n"+g);
		System.out.println("COPY OF MATRIX A:\n"+h);
		System.out.println("SUM OF THE TWO\n"+j);
		System.out.println("10 * A\n"+k);
		System.out.println("-1/2 * A\n"+l);
		System.out.println(k.multiply(g));
		System.out.println("Determinant of A: \n"+ g.determinant());
    }
}

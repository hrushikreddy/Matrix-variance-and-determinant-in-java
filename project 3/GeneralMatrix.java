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
import javax.print.MultiDoc;

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
        super(m,n);
		data = new double [m][n];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {
        super(A.m,A.n);
		data = new double[A.m][A.n];
		for (int i = 0; i < A.m; i++) {
			for (int j = 0; j < A.n; j++) {
                data[i][j] = A.data[i][j];
            }
        }    
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
    if(i >= m){
        if(j >= n){
            throw new MatrixException("invalid"); 
        }
    }    
    double res = data[i][j];   
    return res;
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        if (i >= m || j >= n) { 
			throw new MatrixException("invalid");
		}
		data[i][j] = val;
    }
            
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        double[] a = new double [1]; 
		GeneralMatrix decomp = decomp(a); 
		double mysum = 1.0;
		for (int i = 0; i < m; i++) {
			mysum *= decomp.data[i][i]; 
		}
		return mysum * a[0]; 
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {
       Matrix sum = new GeneralMatrix(m, n);
       
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				sum.setIJ(i, j, getIJ(i, j) + A.getIJ(i, j));
			}
        }
		return sum;
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        double [][] multiplication = new double[m][A.n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < A.n; j++) {
				for (int k = 0; k < n; k++) {
					multiplication[i][j] += data[i][k] * A.getIJ(k,j);
				}	
			}
		}
		Matrix multi = new GeneralMatrix(m, A.n);
		for (int l = 0; l < m; l++) {
			for (int p = 0; p < A.n; p++) {
				multi.setIJ(l, p, multiplication[l][p]); 
			}
		}		
		return multi;
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        Matrix scalar = new GeneralMatrix(m, n);
        for (int i = 0; i < m; i++){  
            for (int j = 0; j < n; j++){  
                scalar.setIJ(i, j, getIJ(i, j) * a);
                System.out.println(scalar.getIJ(i, j));
            }    
        } 
        return scalar;
    }


    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        Random ran = new Random();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				data[i][j] = ran.nextDouble(); 
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
     GeneralMatrix g = new GeneralMatrix(3, 3);
		g.random();
		g.setIJ(2, 2, 2);
		double index = g.getIJ(1, 2);
		GeneralMatrix h = new GeneralMatrix(g);
		Matrix i = g.add(h);
		Matrix j = g.multiply(2);
		Matrix k = h.multiply(3);
        
        System.out.println("value of g"+j);
		System.out.println("entry at point:\n"+index);
		System.out.println("matrix A:\n"+g);
		System.out.println("copy of matrix A:\n"+h);
		System.out.println("addition\n"+i);
		System.out.println("scalar A\n"+j);
		System.out.println("scalar A\n"+k);
		System.out.println(k.multiply(g));
		System.out.println("Determinant of A: \n"+ g.determinant());
    }
}        

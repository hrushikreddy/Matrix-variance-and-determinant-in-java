/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */
import java.util.Random;
public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;
    
    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {
        super(N,N);
		diag = new double [N]; // Creating 3 different arrays of appropiate sizes
		upper = new double [N-1];
		lower = new double [N-1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        double a;
		if (i >= diag.length ) {
			throw new MatrixException("invalid");
        }
        if (j>= diag.length) {
			throw new MatrixException("invalid");
		}
		
		if (i == j) {
			a = diag[i]; 
		}
		else if (i + 1 == j) {
			a = upper[i];
		}
		else if (i - 1 == j) {
			a = lower[j];
		}
		else {
			a = 0.0;
		}
        return a;
    }    
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        if (i == j) {
			diag[i] = val;
		}
		else if (i + 1 == j) {
			upper[i] = val;
		}
		else if (i - 1 == j) {
			lower[i] = val;
		}
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        TriMatrix decomp = decomp();
		double sum = 1.0;
		for (int i = 0; i < diag.length; i++) {
			sum = decomp.diag[i];
		}
		return sum;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {
        TriMatrix a = new TriMatrix(diag.length);
		a.diag[0] = diag[0];
		for (int i = 0; i < upper.length; i++) {
			a.upper[i] = upper[i]; 
		}
		for (int j = 0; j < lower.length; j++) { 
			a.lower[j] = lower[j] / a.diag[j];
			a.diag[j+1] = diag[j+1] - (a.lower[j] * a.upper[j]);
		}	
		return a;
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){
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
        double [][] multiplication = new double[diag.length][A.n];
		for (int i = 0; i < diag.length; i++) {
			for (int j = 0; j < A.n; j++) {
				for (int k = 0; k < n; k++) {
					multiplication[i][j] += getIJ(i, k) * A.getIJ(k,j);
				}	
			}
		}
		Matrix multi = new GeneralMatrix(diag.length, A.n);
		for (int l = 0; l < diag.length; l++) {
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
        Matrix scalar = new TriMatrix(diag.length);
        for (int i = 0; i < diag.length; i++){  
            for (int j = 0; j < diag.length; j++){  
                scalar.setIJ(i, j, getIJ(i, j) * a);
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
		for (int i = 0; i < diag.length; i++) {
			diag[i] = ran.nextDouble();
		}
		for (int j = 0; j < diag.length - 1; j++) {
			lower[j] = ran.nextDouble();
			upper[j] = ran.nextDouble();
    
        }
    }    

    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        TriMatrix m = new TriMatrix(3);
		m.random();
		Matrix k = new GeneralMatrix(3, 3);
		k.random();
		Matrix y = k.multiply(200000);
		Matrix hi = k.multiply(30000);
		TriMatrix l = new TriMatrix(4);
		l.diag = new double[] {5, 5, 5, 5};
		l.upper = new double[] {1, 1, 1};
		l.lower = new double[] {2, 2, 2};
		TriMatrix f = l.decomp();
        
        System.out.println("tri matrix A:\n"+m.toString());
		System.out.println("Determinant of A:\n"+m.determinant());
		System.out.println("Matrix B:\n"+k.toString());
		System.out.println("Matrix B scalar:\n"+y.toString());
        System.out.println("Matrix B scalar:\n"+hi);
        System.out.println("addition:\n"+ m.add(k));
		System.out.println("multiplcation:\n"+k.multiply(m));
		System.out.println("Matrix C:\n"+l);
		System.out.println("Determinant of C:\n"+l.determinant());
		
		 
    }
}
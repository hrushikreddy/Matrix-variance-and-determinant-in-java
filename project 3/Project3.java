/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class, as well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param m           The matrix object that will be filled with random
     *                    samples.
     * @param numSamples  The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix m, int numSamples) {
        double[] det = new double[numSamples]; 
		double sum = 1.0, sumsqr = 1.0;
		for (int i = 0; i < numSamples; i++) {
			m.random();  
			det[i] = m.determinant(); 
			sum += det[i]; 
			sumsqr += Math.pow(det[i], 2); 
		}
		double variance = (sumsqr/numSamples) - Math.pow((sum/numSamples) ,2);
		return variance;    
	}
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50. See the formulation for more detail.
     */
    public static void main(String[] args) {
        Project3 project = new Project3();
		int generalSample = 1000, triSample = 1000; 
		for (int n = 2; n <= 50; n++) {
			GeneralMatrix x = new GeneralMatrix(n, n);
			TriMatrix y = new TriMatrix(n); 
			double varA = project.matVariance(x, generalSample);
			double varB = project.matVariance(y, triSample); 
            System.out.println(n+"\t"+varA+"\t"+varB);
        }      
    }


    
}
/*
 * PROJECT III: MatrixException.java
 *
 * This file defines a new Exception type called MatrixException. You should
 * throw this exception inside the various matrix classes whenever an error
 * occurs (such as incorrect matrix sizes, etc).
 *
 */

class MatrixException extends RuntimeException {
    public MatrixException(String msg) {
        super(msg);
    }
}
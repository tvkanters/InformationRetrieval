package com.uva.ir.utils;

/**
 * A vector for linear algebra usage.
 */
public class Vector {

    /** The data within the vector */
    private final double[] mData;

    /**
     * Prepares a new vector with the specified data.
     * 
     * @param data
     *            Tha data that the vector contains
     */
    public Vector(final double[] data) {
        mData = data;
    }

    /**
     * Returns the dot product of this vector and the specified one.
     * 
     * @param vector
     *            The vector to take the dot product with
     * 
     * @return The dot product
     */
    public double dot(final Vector vector) {
        if (mData.length != vector.mData.length) {
            throw new RuntimeException("Vector dimensions don't match.");
        }

        double sum = 0;
        for (int i = 0; i < mData.length; ++i) {
            sum += mData[i] * vector.mData[i];
        }

        return sum;
    }

    /**
     * Retrieves the norm of this vector.
     * 
     * @return The vector's norm
     */
    public double norm() {
        return Math.sqrt(dot(this));
    }
}

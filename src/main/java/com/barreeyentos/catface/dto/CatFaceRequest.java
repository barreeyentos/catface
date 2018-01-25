package com.barreeyentos.catface.dto;

import java.util.Arrays;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/*
 * CatFaceRequest is what is expected as the request to this service
 *
 * ConfidenceThreshold is the required confidence needed for an image to be considered a cat face
 * ConfidenceThreshold must be between (0.0, 1.0)
 *
 * Image is the image depicted as a matrix of characters
 * Image cannot be null/empty
 */
public class CatFaceRequest {

    @DecimalMax("1.0")
    @DecimalMin("0.0")
    private float confidenceThreshold;

    private String imageUrl;
    private char[][] image;

    public float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public void setConfidenceThreshold(float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public char[][] getImage() {
        return image;
    }

    public void setImage(char[][] image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(confidenceThreshold);
        result = prime * result + Arrays.deepHashCode(image);
        result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatFaceRequest other = (CatFaceRequest) obj;
        if (Float.floatToIntBits(confidenceThreshold) != Float.floatToIntBits(other.confidenceThreshold))
            return false;
        if (!Arrays.deepEquals(image, other.image))
            return false;
        if (imageUrl == null) {
            if (other.imageUrl != null)
                return false;
        } else if (!imageUrl.equals(other.imageUrl))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CatFaceRequest [confidenceThreshold=" + confidenceThreshold + ", imageUrl=" + imageUrl + ", image="
                + Arrays.toString(image) + "]";
    }

}

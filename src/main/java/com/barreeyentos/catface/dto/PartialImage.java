package com.barreeyentos.catface.dto;

import java.util.Arrays;

/*
 * PartialImage represents a small decomposition of the original image
 * with the original position within the image
 */
public class PartialImage {
    Position position;
    char[] image;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public char[] getImage() {
        return image;
    }

    public void setImage(char[] image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(image);
        result = prime * result + ((position == null) ? 0 : position.hashCode());
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
        PartialImage other = (PartialImage) obj;
        if (!Arrays.equals(image, other.image))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PartialImage [position=" + position + ", image=" + Arrays.toString(image) + "]";
    }

}

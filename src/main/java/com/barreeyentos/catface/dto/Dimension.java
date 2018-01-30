package com.barreeyentos.catface.dto;

/*
 * Dimension is a class to represent the width,height dimension of an image
 */
public class Dimension {
    int width;
    int height;

    public static Dimension of(int width, int height) {
        Dimension dimension = new Dimension();
        dimension.setWidth(width);
        dimension.setHeight(height);
        return dimension;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int x) {
        this.width = x;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int y) {
        this.height = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + width;
        result = prime * result + height;
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
        Dimension other = (Dimension) obj;
        if (width != other.width)
            return false;
        if (height != other.height)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Dimension [x=" + width + ", y=" + height + "]";
    }

}

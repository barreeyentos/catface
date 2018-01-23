package com.barreeyentos.catface.dto;

import java.util.List;

/*
 * Wrapper DTO used to respond with multiple cat face solutions
 * Json response looks like:
 * {
 *    "catFaces": [ {...}, {...}, ...]
 * }
 */
public class CatFaceList {
    List<CatFace> catFaces;

    // Default constructor for jackson serialization
    public CatFaceList() {
    };

    public CatFaceList(List<CatFace> catFaces) {
        this.catFaces = catFaces;
    }

    public List<CatFace> getCatFaces() {
        return catFaces;
    }

    public void setCatFaces(List<CatFace> catFaces) {
        this.catFaces = catFaces;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catFaces == null) ? 0 : catFaces.hashCode());
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
        CatFaceList other = (CatFaceList) obj;
        if (catFaces == null) {
            if (other.catFaces != null)
                return false;
        } else if (!catFaces.equals(other.catFaces))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CatFaceList [catFaces=" + catFaces + "]";
    }
}

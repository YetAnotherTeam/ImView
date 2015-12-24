package jat.imview.model;

/**
 * Created by bulat on 23.12.15.
 */
public class Image {
    private int id;
    private int rating;
    private String path;
    private boolean isFeatured;

    public Image() {
    }

    public Image(int id, String path, boolean isFeatured, int rating) {
        this.id = id;
        this.path = path;
        this.isFeatured = isFeatured;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

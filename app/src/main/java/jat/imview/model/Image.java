package jat.imview.model;

import java.util.Date;

/**
 * Created by bulat on 23.12.15.
 */
public class Image {
    private int id;
    private int rating;
    private String path;
    private Date publishDate;

    public Image() {
    }

    public Image(int id, String path, Date publishDate, int rating) {
        this.id = id;
        this.path = path;
        this.publishDate = publishDate;
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
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}

package jat.imview.model;

import java.util.Date;

/**
 * Created by bulat on 23.12.15.
 */
public class Image {
    private int id;
    private int rating;
    private String filepath;
    private String netpath;
    private Date publishDate;

    public Image() {
    }

    public Image(int id, String netpath, Date publishDate, int rating) {
        this.id = id;
        this.netpath = netpath;
        this.publishDate = publishDate;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getNetpath() {
        return netpath;
    }

    public void setNetpath(String netpath) {
        this.netpath = netpath;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}

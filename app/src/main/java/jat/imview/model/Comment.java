package jat.imview.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bulat on 17.12.15.
 */
public class Comment {
    private int id;
    private int imageId;
    private int userId;
    private Date publishDate;
    private String message;
    private int rating;

    public Comment() {
    }

    public Comment(int id, int imageId, int userId, Date publishDate, String message, int rating) {
        this.id = id;
        this.imageId = imageId;
        this.userId = userId;
        this.publishDate = publishDate;
        this.message = message;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setPublishDateFromString(String publishDateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        try {
            this.publishDate = dateFormat.parse(publishDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

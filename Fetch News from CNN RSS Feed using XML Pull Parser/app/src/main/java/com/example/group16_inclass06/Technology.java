package com.example.group16_inclass06;

import java.io.DataInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Satish on 9/26/2016.
 */
public class Technology {

    String title;
    String description;
    String link;
    Date pubDate;
    String thumbUrl;
    String imageUrl;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Comparator<Technology> revComp = new Comparator<Technology>() {

        public int compare(Technology s1, Technology s2) {
            Date rev1 = s1.pubDate;
            Date rev2 = s2.pubDate;

            return rev1.compareTo(rev2);
        }
    };

    @Override
    public String toString() {
        return "Technology{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

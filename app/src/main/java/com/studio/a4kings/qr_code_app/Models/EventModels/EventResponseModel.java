package com.studio.a4kings.qr_code_app.Models.EventModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DUX on 21.03.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResponseModel implements Serializable {
    private Integer id;
    private String title;
    private String userCreatorId;
    private String author;
    private String startDate;
    private String endDate;
    private Double priceFrom;
    private Double priceTo;
    private Float rating;
    private String tag;
    private String mainPhoto;
    private String description;
    private Integer countUsers;
    private Boolean isParticipant;
    private Boolean isSubscriber;


    public Boolean getIsSubscriber() {
        return isSubscriber;
    }

    public void setIsSubscriber(Boolean subscriber) {
        isSubscriber = subscriber;
    }

    public Boolean getIsParticipant() {
        return isParticipant;
    }

    public void setIsParticipant(Boolean isParticipant) {
        this.isParticipant = isParticipant;
    }

    public Integer getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(Integer countUsers) {
        this.countUsers = countUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Double priceTo) {
        this.priceTo = priceTo;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
}

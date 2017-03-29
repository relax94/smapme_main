package com.studio.a4kings.qr_code_app.Models.Activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by DUX on 13.06.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityModel {
    private String activityId;
    private String initiatorId;
    private Integer activityType;
    private Date createdDate;
    private ActivityData data;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ActivityData getData() {
        return data;
    }

    public void setData(ActivityData data) {
        this.data = data;
    }
}

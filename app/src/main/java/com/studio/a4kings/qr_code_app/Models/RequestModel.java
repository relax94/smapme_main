package com.studio.a4kings.qr_code_app.Models;

import java.io.Serializable;

/**
 * Created by DUX on 29.04.2016.
 */
public class RequestModel extends SubscriberModel implements Serializable{
    public Integer requestId;
    public String createdDate;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

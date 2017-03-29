package com.studio.a4kings.qr_code_app.Models.FSQModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FSQMeta {
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("requestId")
    private  String requestId;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

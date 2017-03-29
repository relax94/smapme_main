package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.FSQModels.FSQMeta;
import com.studio.a4kings.qr_code_app.Models.FSQModels.FSQResult;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FSQResponse {

    @JsonProperty("meta")
    private FSQMeta meta;
    @JsonProperty("response")
    private FSQResult response;

    public FSQMeta getMeta() {
        return meta;
    }

    public void setMeta(FSQMeta meta) {
        this.meta = meta;
    }

    public FSQResult getResponse() {
        return response;
    }

    public void setResponse(FSQResult response) {
        this.response = response;
    }
}

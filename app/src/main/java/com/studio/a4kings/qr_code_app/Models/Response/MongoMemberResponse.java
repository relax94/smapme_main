package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;

/**
 * Created by Dmitry Pavlenko on 03.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MongoMemberResponse {
    @JsonProperty("state")
    private boolean state;
    @JsonProperty("r")
    private MongoMember r;

    public boolean isState() {
        return state;
    }

    public MongoMember getMongoMember() {
        return r;
    }
}

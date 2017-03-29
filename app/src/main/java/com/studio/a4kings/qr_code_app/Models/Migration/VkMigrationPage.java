package com.studio.a4kings.qr_code_app.Models.Migration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventRequestModel;

/**
 * Created by Dmitry Pavlenko on 14.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VkMigrationPage {
    @JsonProperty("id")
    private int id;
    @JsonProperty("screen_name")
    private String screenName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("photo_50")
    private String photoSmall;
    @JsonProperty("photo_100")
    private String photoMedium;
    @JsonProperty("photo_200")
    private String photoBig;
    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VkMigrationPage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getPhotoMedium() {
        return photoMedium;
    }

    public void setPhotoMedium(String photoMedium) {
        this.photoMedium = photoMedium;
    }

    public String getPhotoBig() {
        return photoBig;
    }

    public void setPhotoBig(String photoBig) {
        this.photoBig = photoBig;
    }

    public static EventRequestModel convertToEventRequest(VkMigrationPage vkMigrationPage){
        EventRequestModel eventRequestModel =new EventRequestModel();
        eventRequestModel.setTitle(vkMigrationPage.getName());
        eventRequestModel.setCountUsers(10);
        eventRequestModel.setDescription(vkMigrationPage.getDescription());
        eventRequestModel.setStartDate("14.06.16");
        eventRequestModel.setEndDate("14.06.16");
        eventRequestModel.setPriceFrom(0D);
        eventRequestModel.setPriceTo(0D);
        eventRequestModel.setTag("Музыка");
        eventRequestModel.setPrivateLevel(0);
        return  eventRequestModel;
    }
}

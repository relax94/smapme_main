package com.studio.a4kings.qr_code_app.Models.BookingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingModel {
    @JsonProperty("_id")
    private String databaseId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("city_hotel")
    private String cityHotel;
    @JsonProperty("cc1")
    private String cc1;
    @JsonProperty("ufi")
    private String ufi;
    @JsonProperty("class")
    private Integer hotelClass;
    @JsonProperty("currencycode")
    private String currencyCode;
    @JsonProperty("minrate")
    private String minRate;
    @JsonProperty("maxrate")
    private String maxRate;
    @JsonProperty("preferred")
    private Integer preffered;
    @JsonProperty("public_ranking")
    private Integer publicRanking;
    @JsonProperty("nr_rooms")
    private Integer nrRooms;
    @JsonProperty("longitude")
    private Double lon;
    @JsonProperty("latitude")
    private Double lat;
    @JsonProperty("hotel_url")
    private String hotelUrl;
    @JsonProperty("photo_url")
    private String photoUrl;
    @JsonProperty("desc_en")
    private String descriptionEn;
    @JsonProperty("desc_ru")
    private String descriptionRu;
    @JsonProperty("city_unique")
    private String cityUniqueId;
    @JsonProperty("city_preferred")
    private String cityPreffered;
    @JsonProperty("continent_id")
    private String continentId;

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCityHotel() {
        return cityHotel;
    }

    public void setCityHotel(String cityHotel) {
        this.cityHotel = cityHotel;
    }

    public String getCc1() {
        return cc1;
    }

    public void setCc1(String cc1) {
        this.cc1 = cc1;
    }

    public String getUfi() {
        return ufi;
    }

    public void setUfi(String ufi) {
        this.ufi = ufi;
    }

    public Integer getHotelClass() {
        return hotelClass;
    }

    public void setHotelClass(Integer hotelClass) {
        this.hotelClass = hotelClass;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public Integer getPreffered() {
        return preffered;
    }

    public void setPreffered(Integer preffered) {
        this.preffered = preffered;
    }

    public Integer getPublicRanking() {
        return publicRanking;
    }

    public void setPublicRanking(Integer publicRanking) {
        this.publicRanking = publicRanking;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getHotelUrl() {
        return hotelUrl;
    }

    public void setHotelUrl(String hotelUrl) {
        this.hotelUrl = hotelUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getCityUniqueId() {
        return cityUniqueId;
    }

    public void setCityUniqueId(String cityUniqueId) {
        this.cityUniqueId = cityUniqueId;
    }

    public String getCityPreffered() {
        return cityPreffered;
    }

    public void setCityPreffered(String cityPreffered) {
        this.cityPreffered = cityPreffered;
    }

    public String getContinentId() {
        return continentId;
    }

    public void setContinentId(String continentId) {
        this.continentId = continentId;
    }
}

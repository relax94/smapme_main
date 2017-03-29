package com.studio.a4kings.qr_code_app.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DUX on 15.02.2016.
 */
public class TravelEventModel implements Serializable {

    private Integer id;
    private String title;

    public TravelEventModel() {
    }

    public TravelEventModel(String title, Integer budget) {
        this.title = title;
        this.budget = budget;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
    private ArrayList<String> countries;

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    private ArrayList<String> cities;
    private ArrayList<String> photos;
    private ArrayList<Member> users;
    private String userId;
    private String description;
    private String dateStart;
    private String dateEnd;
    private Integer budget;
    private Integer usersCount;

    public Integer getCountUsers() {
        return CountUsers;
    }

    public void setCountUsers(Integer countUsers) {
        CountUsers = countUsers;
    }

    private Integer CountUsers;
    private Member user;

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
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

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<Member> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Member> users) {
        this.users = users;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }
}

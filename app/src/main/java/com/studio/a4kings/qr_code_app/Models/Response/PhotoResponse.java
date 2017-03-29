package com.studio.a4kings.qr_code_app.Models.Response;

public class PhotoResponse extends MainResponse{

    String fileName;
    Integer type;// 1 - photo, 2 - wallpaper

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

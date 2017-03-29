package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.maps.model.LatLng;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GSteps;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 25.05.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripModel {

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("owner_id")
    private String owner_id;
    @JsonProperty("event_id")
    private String event_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("map_snapshot_url")
    private String snapShotUrl;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("line_width")
    private int lineWidth;
    @JsonProperty("line_color")
    private int lineColor;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /*

    @JsonProperty("gDirection")
    private List<GSteps> gDirection;
*/

    @JsonProperty("trip_points")
    private List<Coords> tripPoints;

    public List<Coords> getTripPoints() {
        return tripPoints;
    }

    public void setTripPoints(List<Coords> tripPoints) {
        this.tripPoints = tripPoints;
    }

    /*public List<GSteps> getgDirection() {
            return gDirection;
        }

        public void setgDirection(List<GSteps> gDirection) {
            this.gDirection = gDirection;
        }
    */
    public String getEventId() {
        return event_id;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }



    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnapShotUrl() {
        return snapShotUrl;
    }

    public void setSnapShotUrl(String snapShotUrl) {
        this.snapShotUrl = snapShotUrl;
    }
}

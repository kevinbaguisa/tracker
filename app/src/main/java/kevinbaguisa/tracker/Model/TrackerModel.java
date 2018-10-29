package kevinbaguisa.tracker.Model;

import com.google.gson.annotations.SerializedName;

public class TrackerModel {

    @SerializedName("bus_id")
    private String bus_id;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public TrackerModel() {

    }

    public void setBusID(String bus_id) {
        this.bus_id = bus_id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBusID() {
        return bus_id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}

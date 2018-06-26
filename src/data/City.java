package data;

public class City {
    private String id;
    private String name;
    private String country;
    private double longitude;
    private double latitude;
    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }
    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return "City [id=" + id + ", cityName=" + name + ", country=" + country + ", longitude=" + longitude
                + ", latitude=" + latitude + "]";
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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

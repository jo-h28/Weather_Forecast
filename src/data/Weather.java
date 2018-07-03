package data;

public class Weather {
    private String description;
    private String main;
    private double temperature;
    private double pressure;
    private int humidity;
    private double minTemp;
    private double maxTemp;
    private double windSpeed;
    private double windDegree;
    private String icon;
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Weather() {}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public int getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public double getWindDegree() {
        return windDegree;
    }
    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }
    @Override
    public String toString() {
        return "Weather [description=" + description + ", main=" + main + ", temperature=" + temperature + ", pressure="
                + pressure + ", humidity=" + humidity + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp + ", windSpeed="
                + windSpeed + ", windDegree=" + windDegree + "]";
    }
    
}

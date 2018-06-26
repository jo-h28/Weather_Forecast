package api;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.*;

public class CityForecast {
    private static final String APPID = "9be94e233b1e8ee592132e23db487117";
    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=%s";
    private City cityInfo;
    private Weather[] forecast;
    private double[] seaLevelPress;
    private double[] groundLevelPress;
    private String[] dayTime;
    private int population;
    public CityForecast(String cityName, String country) {
        forecast = new Weather[40];
        seaLevelPress = new double[40];
        groundLevelPress = new double[40];
        dayTime = new String[40];
        String urlString = String.format(FORECAST_URL, cityName + "," + country + "&APPID=" + APPID);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            String stringFromStream = IOUtils.toString(new BufferedInputStream(urlConnection.getInputStream()), "UTF-8");
            JSONObject json = new JSONObject(stringFromStream);
            JSONArray list = json.getJSONArray("list");
            cityInfo = new City(json.getJSONObject("city").getString("name"), json.getJSONObject("city").getString("country"));
            cityInfo.setId(json.getJSONObject("city").get("id").toString());
            cityInfo.setLatitude(json.getJSONObject("city").getJSONObject("coord").getDouble("lat"));
            cityInfo.setLongitude(json.getJSONObject("city").getJSONObject("coord").getDouble("lon"));
            population = json.getJSONObject("city").getInt("population");
            for(int i = 0; i < list.length(); ++i) {
                forecast[i] = new Weather();
                forecast[i].setMain(list.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
                forecast[i].setDescription(list.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                forecast[i].setTemperature(list.getJSONObject(i).getJSONObject("main").getDouble("temp"));
                forecast[i].setMinTemp(list.getJSONObject(i).getJSONObject("main").getDouble("temp_min"));
                forecast[i].setMaxTemp(list.getJSONObject(i).getJSONObject("main").getDouble("temp_max"));
                forecast[i].setPressure(list.getJSONObject(i).getJSONObject("main").getDouble("pressure"));
                forecast[i].setHumidity(list.getJSONObject(i).getJSONObject("main").getInt("humidity"));
                forecast[i].setWindSpeed(list.getJSONObject(i).getJSONObject("wind").getDouble("speed"));
                forecast[i].setWindDegree(list.getJSONObject(i).getJSONObject("wind").getDouble("deg"));
                seaLevelPress[i] = list.getJSONObject(i).getJSONObject("main").getDouble("sea_level");
                groundLevelPress[i] = list.getJSONObject(i).getJSONObject("main").getDouble("grnd_level");
                dayTime[i] = list.getJSONObject(i).get("dt_txt").toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }
    public static void main(String[] args) {
        CityForecast testCity = new CityForecast("jakarta", "indonesia");
        System.out.println(testCity);
    }
    @Override
    public String toString() {
        return "CityForecast [cityInfo=" + cityInfo + ", forecast=" + Arrays.toString(forecast) + ", seaLevelPress="
                + Arrays.toString(seaLevelPress) + ", groundLevelPress=" + Arrays.toString(groundLevelPress)
                + ", dayTime=" + Arrays.toString(dayTime) + ", population=" + population + "]";
    }
}

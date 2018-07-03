package api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import data.*;

import java.net.URL;

public class CityWeather {
   private static final String APPID = "9be94e233b1e8ee592132e23db487117";
   private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s";
   private City cityInfo;
   private Weather currentWeather;
   private int visibility;
   
   public CityWeather(String cityName, String country) {
       String urlString = String.format(WEATHER_URL, cityName + "," + country + "&APPID=" + APPID);
       HttpURLConnection urlConnection = null;
       try {
           URL url = new URL(urlString.toString());
           urlConnection = (HttpURLConnection) url.openConnection();
           String stringFromStream = IOUtils.toString(new BufferedInputStream(urlConnection.getInputStream()), "UTF-8");
           JSONObject json = new JSONObject(stringFromStream);
           JSONObject coord = json.getJSONObject("coord");
           JSONObject mainInfo = json.getJSONObject("main");
           JSONObject condition = json.getJSONArray("weather").getJSONObject(0);
           JSONObject wind = json.getJSONObject("wind");
           cityInfo = new City(json.getString("name").toString(), json.getJSONObject("sys").getString("country"));
           currentWeather = new Weather();
           cityInfo.setId(json.get("id").toString());
           cityInfo.setLatitude(coord.getDouble("lat"));
           cityInfo.setLongitude(coord.getDouble("lon"));
           currentWeather.setMain(condition.getString("main"));
           currentWeather.setDescription(condition.getString("description"));
           currentWeather.setTemperature(mainInfo.getDouble("temp"));
           currentWeather.setPressure(mainInfo.getInt("pressure"));
           currentWeather.setHumidity(mainInfo.getInt("humidity"));
           currentWeather.setMinTemp(mainInfo.getDouble("temp_min"));
           currentWeather.setMaxTemp(mainInfo.getDouble("temp_max"));
           currentWeather.setIcon("http://openweathermap.org/img/w/" + condition.getString("icon") + ".png");
           visibility = json.getInt("visibility");
           currentWeather.setWindSpeed(wind.getDouble("speed"));
           if(wind.has("deg")) {
               currentWeather.setWindDegree(wind.getDouble("deg"));
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
   @Override
   public String toString() {
       return "CityWeather [id=" + cityInfo.getId() + ", cityName=" + cityInfo.getName() + ", country=" + cityInfo.getCountry() + ", description=" + currentWeather.getDescription()
            + ", main=" + currentWeather.getMain() + ", longitude=" + cityInfo.getLongitude() + ", latitude=" + cityInfo.getLatitude() + ", temperature=" + currentWeather.getTemperature()
            + ", pressure=" + currentWeather.getPressure() + ", humidity=" + currentWeather.getHumidity() + ", minTemp=" + currentWeather.getMinTemp() + ", maxTemp=" + currentWeather.getMaxTemp()
            + ", visibility=" + visibility + ", windSpeed=" + currentWeather.getWindSpeed() + ", windDegree=" + currentWeather.getWindDegree() + "]";
}
   public static void main(String[] args) {
       CityWeather testCity = new CityWeather("jakarta", "indonesia");
       System.out.println(testCity);
   }
   public City getCityInfo() {
       return cityInfo;
   }
   public Weather getCurrentWeather() {
       return currentWeather;
   }
   public int getVisibility() {
       return visibility;
   }
}

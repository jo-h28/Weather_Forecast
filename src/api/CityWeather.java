package api;

import data.City;
import data.Weather;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class used to find city's current weather from given city name and country.
 */
public class CityWeather {
  private static final String APPID = "9be94e233b1e8ee592132e23db487117";
  private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s";
  private City cityInfo;
  private Weather currentWeather;
  private int visibility;

  /**
   * Constructor.
   * @param cityName name of the city which informations are to be requested.
   * @param country name of the country which consist of the city.
   */
  public CityWeather(String cityName, String country) {
    String urlString = String.format(WEATHER_URL, cityName + "," + country + "&APPID=" + APPID);
    HttpURLConnection urlConnection = null;
    try {
      URL url = new URL(urlString.toString());
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.connect();
      if (urlConnection.getResponseCode() == 200) {
        String stringFromStream = IOUtils.toString(
            new BufferedInputStream(urlConnection.getInputStream()), "UTF-8");
        JSONObject json = new JSONObject(stringFromStream);
        JSONObject coord = json.getJSONObject("coord");
        cityInfo = new City(json.getString("name").toString(),
            json.getJSONObject("sys").getString("country"));
        cityInfo.setLatitude(coord.getDouble("lat"));
        cityInfo.setLongitude(coord.getDouble("lon"));
        JSONObject mainInfo = json.getJSONObject("main");
        currentWeather = new Weather();
        currentWeather.setTemperature(mainInfo.getDouble("temp"));
        currentWeather.setPressure(mainInfo.getInt("pressure"));
        currentWeather.setHumidity(mainInfo.getInt("humidity"));
        currentWeather.setMinTemp(mainInfo.getDouble("temp_min"));
        currentWeather.setMaxTemp(mainInfo.getDouble("temp_max"));
        JSONObject condition = json.getJSONArray("weather").getJSONObject(0);
        currentWeather.setMain(condition.getString("main"));
        currentWeather.setDescription(condition.getString("description"));
        JSONObject wind = json.getJSONObject("wind");
        currentWeather.setWindSpeed(wind.getDouble("speed"));
        if (wind.has("deg")) {
          currentWeather.setWindDegree(wind.getDouble("deg"));
        } else {
          currentWeather.setWindDegree(0);
        }
        cityInfo.setId(json.get("id").toString());
        currentWeather.setIcon("http://openweathermap.org/img/w/" + condition.getString("icon") + ".png");
        if (json.has("visibility")) {
          visibility = json.getInt("visibility");
        } else {
          visibility = 10000;
        }
      } else {
        cityInfo = null;
      }
    } catch (SocketTimeoutException e) {
      cityInfo = null;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }

  @Override
  public String toString() {
    return "CityWeather [id=" + cityInfo.getId() 
        + ", cityName=" + cityInfo.getName() 
        + ", country=" + cityInfo.getCountry() 
        + ", description=" + currentWeather.getDescription() 
        + ", main=" + currentWeather.getMain() 
        + ", longitude=" + cityInfo.getLongitude() 
        + ", latitude=" + cityInfo.getLatitude() 
        + ", temperature=" + currentWeather.getTemperature() 
        + ", pressure=" + currentWeather.getPressure() 
        + ", humidity=" + currentWeather.getHumidity() 
        + ", minTemp=" + currentWeather.getMinTemp() 
        + ", maxTemp=" + currentWeather.getMaxTemp() 
        + ", visibility=" + visibility 
        + ", windSpeed=" + currentWeather.getWindSpeed()
        + ", windDegree=" + currentWeather.getWindDegree() + "]";
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

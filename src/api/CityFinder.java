package api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class used to find cities' forecasts from given city name and database.
 */
public class CityFinder {
  private String content;
  private ArrayList<CityForecast> forecasts;
  private ArrayList<CityWeather> weathers;

  /**
   * Constructor.
   * @param cityName name of the city to be searched.
   * @param fileContent database of cities' informations.
   */
  public CityFinder(String cityName, String fileContent) {
    weathers = new ArrayList<CityWeather>();
    forecasts = new ArrayList<CityForecast>();
    try {
      content = fileContent;
      JSONArray jsonArray = new JSONArray(content);
      for (int i = 0; i < jsonArray.length(); ++i) {
        JSONObject json = jsonArray.getJSONObject(i);
        if (json.get("name").toString().toLowerCase().contains(cityName.toLowerCase())) {
          String name = json.get("name").toString().replaceAll(" ", "+");
          String country = json.get("country").toString().replaceAll(" ", "+");
          CityWeather currentWeather = new CityWeather(name, country);
          if (currentWeather.getCityInfo() != null) {
            String id = currentWeather.getCityInfo().getId();
            if (!weathers.stream().anyMatch(w -> w.getCityInfo().getId().equals(id))) {
              weathers.add(currentWeather);
            }

            CityForecast currentForecast = new CityForecast(name, country);
            if (!forecasts.stream().anyMatch(f -> f.getCityInfo().getId().equals(id))) {
              forecasts.add(currentForecast);
            }
          }
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  public String getContent() {
    return content;
  }

  public ArrayList<CityForecast> getForecasts() {
    return forecasts;
  }

  public ArrayList<CityWeather> getWeathers() {
    return weathers;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "CityFinder [weathers=" + weathers + ", forecasts=" + forecasts + "]";
  }
}

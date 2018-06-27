package api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityFinder {
    private ArrayList<CityWeather> weathers;
    private ArrayList<CityForecast> forecasts;
    public CityFinder(String cityName) {
        weathers = new ArrayList<CityWeather>();
        forecasts = new ArrayList<CityForecast>();
        try {
            String content = FileUtils.readFileToString(new File("city.list.json"), "utf-8");
            JSONArray jsonArray = new JSONArray(content);
            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.get("name").toString().toLowerCase().contains(cityName.toLowerCase())) {
                    String name = json.get("name").toString().replaceAll(" ", "+");
                    String country = json.get("country").toString().replaceAll(" ", "+");
                    
                    CityWeather currentWeather = new CityWeather(name, country);
                    String id = currentWeather.getCityInfo().getId();
                    if(!weathers.stream().anyMatch(w -> w.getCityInfo().getId().equals(id))) {
                        weathers.add(currentWeather);
                    }
                    
                    CityForecast currentForecast = new CityForecast(name, country);
                    if(!forecasts.stream().anyMatch(f -> f.getCityInfo().getId().equals(id))) {
                        forecasts.add(currentForecast);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void main(String args[]) {
        CityFinder finder = new CityFinder("Delhi");
    }
    public ArrayList<CityWeather> getWeathers() {
        return weathers;
    }
    public ArrayList<CityForecast> getForecasts() {
        return forecasts;
    }
}

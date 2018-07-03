package api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityFinder {
    private ArrayList<CityWeather> weathers;
    private ArrayList<CityForecast> forecasts;
    private String content;
    public CityFinder(String cityName) {
        weathers = new ArrayList<CityWeather>();
        forecasts = new ArrayList<CityForecast>();
        try {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void main(String args[]) {
        @SuppressWarnings("unused")
        CityFinder finder = null;
        finder = new CityFinder("Delhi");
        System.out.println(finder);
    }
    public ArrayList<CityWeather> getWeathers() {
        return weathers;
    }
    public ArrayList<CityForecast> getForecasts() {
        return forecasts;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}

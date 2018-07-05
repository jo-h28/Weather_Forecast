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
    private String content;
    public CityFinder(String cityName, String fileContent) {
        weathers = new ArrayList<CityWeather>();
        forecasts = new ArrayList<CityForecast>();
        try {
            content = fileContent;
            JSONArray jsonArray = new JSONArray(content);
            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.get("name").toString().toLowerCase().contains(cityName.toLowerCase())) {
                    String name = json.get("name").toString().replaceAll(" ", "+");
                    String country = json.get("country").toString().replaceAll(" ", "+");
                    CityWeather currentWeather = new CityWeather(name, country);
                    if(currentWeather.getCityInfo() != null) {
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "CityFinder [weathers=" + weathers + ", forecasts=" + forecasts + "]";
    }

    public static void main(String args[]) {
        CityFinder finder = null;
        try {
            finder = new CityFinder("asdjkanfkandfkf", FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

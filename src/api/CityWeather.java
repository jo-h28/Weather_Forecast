package api;

import java.net.HttpURLConnection;
import java.net.URL;

public class CityWeather {
    HttpURLConnection urlConnection = null;
    private Object urlString;
    URL url = new URL(urlString.toString());
    urlConnection = (HttpURLConnection) url.openConnection();
}

package junit;

import org.junit.jupiter.api.Test;

import api.CityForecast;

class JUCityForecast {
  private CityForecast cityForecast = new CityForecast("Jakarta", "Indonesia");

  @Test
  void testCityForecast() {
    assert (cityForecast.getCityInfo() != null);
  }

  @Test
  void testGetCityInfo() {
    assert (cityForecast.getCityInfo() != null);
  }

  @Test
  void testGetForecast() {
    assert (cityForecast.getForecast().length != 0);
  }

  @Test
  void testGetSeaLevelPress() {
    assert (cityForecast.getSeaLevelPress().length != 0);
  }

  @Test
  void testGetGroundLevelPress() {
    assert (cityForecast.getGroundLevelPress().length != 0);
  }

  @Test
  void testGetDayTime() {
    assert (cityForecast.getDayTime().length != 0);
  }

}

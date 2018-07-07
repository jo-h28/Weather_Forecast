package junit;

import org.junit.jupiter.api.Test;

import api.CityWeather;

class JUCityWeather {
  private CityWeather cityWeather = new CityWeather("Jakarta", "Indonesia");

  @Test
  void testCityWeather() {
    assert (cityWeather.getCityInfo() != null);
  }

  @Test
  void testGetCityInfo() {
    assert (cityWeather.getCityInfo() != null);
  }

  @Test
  void testGetCurrentWeather() {
    assert (cityWeather.getCurrentWeather().getPressure() != 0);
  }

  @Test
  void testGetVisibility() {
    assert (cityWeather.getVisibility() >= 0);
  }

}

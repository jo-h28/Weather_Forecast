package junit;

import org.junit.jupiter.api.Test;

import data.Weather;

class JUWeather {
  private Weather weather = new Weather();

  @Test
  void testGetIcon() {
    weather.setIcon("no1con");
    assert (weather.getIcon().equals("no1con"));
  }

  @Test
  void testSetIcon() {
    weather.setIcon("no_icon");
    assert (weather.getIcon().equals("no_icon"));
  }

  @Test
  void testWeather() {
    weather.setIcon("noIcon");
    weather.setHumidity(99);
    assert (weather.getIcon().equals("noIcon") && weather.getHumidity() == 99);
  }

  @Test
  void testGetDescription() {
    weather.setDescription("clear clear sky");
    assert (weather.getDescription().equals("clear clear sky"));
  }

  @Test
  void testSetDescription() {
    weather.setDescription("clear sky");
    assert (weather.getDescription().equals("clear sky"));
  }

  @Test
  void testGetMain() {
    weather.setMain("rain");
    assert (weather.getMain().equals("rain"));
  }

  @Test
  void testSetMain() {
    weather.setMain("clear");
    assert (weather.getMain().equals("clear"));
  }

  @Test
  void testGetTemperature() {
    weather.setTemperature(300.13);
    assert (weather.getTemperature() == 300.13);
  }

  @Test
  void testSetTemperature() {
    weather.setTemperature(300.03);
    assert (weather.getTemperature() == 300.03);
  }

  @Test
  void testGetPressure() {
    weather.setPressure(700.23);
    assert (weather.getPressure() == 700.23);
  }

  @Test
  void testSetPressure() {
    weather.setPressure(700.21);
    assert (weather.getPressure() == 700.21);
  }

  @Test
  void testGetHumidity() {
    weather.setHumidity(98);
    assert (weather.getHumidity() == 98);
  }

  @Test
  void testSetHumidity() {
    weather.setHumidity(99);
    assert (weather.getHumidity() == 99);
  }

  @Test
  void testGetMinTemp() {
    weather.setMinTemp(300.25);
    assert (weather.getMinTemp() == 300.25);
  }

  @Test
  void testSetMinTemp() {
    weather.setMinTemp(300.24);
    assert (weather.getMinTemp() == 300.24);
  }

  @Test
  void testGetMaxTemp() {
    weather.setMaxTemp(300.22);
    assert (weather.getMaxTemp() == 300.22);
  }

  @Test
  void testSetMaxTemp() {
    weather.setMaxTemp(300.21);
    assert (weather.getMaxTemp() == 300.21);
  }

  @Test
  void testGetWindSpeed() {
    weather.setWindSpeed(10.01);
    assert (weather.getWindSpeed() == 10.01);
  }

  @Test
  void testSetWindSpeed() {
    weather.setWindSpeed(10.02);
    assert (weather.getWindSpeed() == 10.02);
  }

  @Test
  void testGetWindDegree() {
    weather.setWindDegree(0.1);
    assert (weather.getWindDegree() == 0.1);
  }

  @Test
  void testSetWindDegree() {
    weather.setWindDegree(0.2);
    assert (weather.getWindDegree() == 0.2);
  }

}

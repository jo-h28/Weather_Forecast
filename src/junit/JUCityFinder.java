package junit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import api.CityFinder;

class JUCityFinder {

  @Test
  void testCityFinder() {
    CityFinder cityFinder = null;
    try {
      cityFinder = new CityFinder("asdasfdasfsada",
          FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert (cityFinder.getWeathers().size() == 0);
  }

  @Test
  void testGetContent() {
    CityFinder cityFinder = null;
    try {
      cityFinder = new CityFinder("asdasfdasfsada",
          FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    cityFinder.setContent("nothing");
    assert (cityFinder.getContent().equals("nothing"));
  }

  @Test
  void testGetForecasts() {
    CityFinder cityFinder = null;
    try {
      cityFinder = new CityFinder("asdasfdasfsada",
          FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert (cityFinder.getForecasts().size() == 0);
  }

  @Test
  void testGetWeathers() {
    CityFinder cityFinder = null;
    try {
      cityFinder = new CityFinder("asdasfdasfsada",
          FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert (cityFinder.getWeathers().size() == 0);
  }

  @Test
  void testSetContent() {
    CityFinder cityFinder = null;
    try {
      cityFinder = new CityFinder("asdasfdasfsada",
          FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    cityFinder.setContent("nothing");
    assert (cityFinder.getContent().equals("nothing"));
  }

}

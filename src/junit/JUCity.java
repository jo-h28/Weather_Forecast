package junit;

import org.junit.jupiter.api.Test;

import data.City;

class JUCity {
  private City city = new City("Jakarta", "Indonesia");

  @Test
  void testCity() {
    assert (city.getName().equals("Jakarta") && city.getCountry().equals("Indonesia"));
  }

  @Test
  void testGetId() {
    city.setId("001");
    assert (city.getId().equals("001"));
  }

  @Test
  void testSetId() {
    city.setId("001");
    assert (city.getId().equals("001"));
  }

  @Test
  void testGetName() {
    city.setName("Bandung");
    assert (city.getName().equals("Bandung"));
  }

  @Test
  void testSetName() {
    city.setName("Bandung");
    assert (city.getName().equals("Bandung"));
  }

  @Test
  void testGetCountry() {
    city.setCountry("ID");
    assert (city.getCountry().equals("ID"));
  }

  @Test
  void testSetCountry() {
    city.setCountry("ID");
    assert (city.getCountry().equals("ID"));
  }

  @Test
  void testGetLongitude() {
    city.setLongitude(100.01);
    assert (city.getLongitude() == 100.01);
  }

  @Test
  void testSetLongitude() {
    city.setLongitude(100.01);
    assert (city.getLongitude() == 100.01);
  }

  @Test
  void testGetLatitude() {
    city.setLatitude(100.01);
    assert (city.getLatitude() == 100.01);
  }

  @Test
  void testSetLatitude() {
    city.setLatitude(100.01);
    assert (city.getLatitude() == 100.01);
  }

}

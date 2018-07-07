package gui;

import api.CityFinder;
import api.CityForecast;
import api.CityWeather;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Class used to display cities' forecasts from given city name.
 */
@SuppressWarnings("serial")
public class SearchDisplay extends JPanel implements ActionListener {
  private BufferedImage searchBackground;
  private JComboBox<String> optList;
  private JTextField cityName;
  private JButton searchButton;
  private JList<String> result;
  private JEditorPane descriptions;
  private JLabel noRes;
  private JScrollPane resultScroller;
  private JScrollPane descScroller;
  private String cityList;
  private GridBagConstraints gbc;

  /**
   * Constructor.
   */
  public SearchDisplay() {
    cityList = "";
    setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    try {
      searchBackground = ImageIO.read(new File("asset/search.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    optList = new JComboBox<>(new String[] { "Current Weather", "Forecast" });
    JLabel query = new JLabel("Enter City Name: ");
    query.setForeground(Color.white);
    cityName = new JTextField(20);
    searchButton = new JButton("search");
    searchButton.addActionListener(this);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 0, 20, 10);
    add(optList, gbc);
    gbc.gridx = 1;
    gbc.insets = new Insets(10, 10, 20, 10);
    add(query, gbc);
    gbc.gridx = 2;
    add(cityName, gbc);
    gbc.gridx = 3;
    add(searchButton, gbc);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (isComponentExist(noRes)) {
      remove(noRes);
    }
    if (isComponentExist(result)) {
      remove(result);
    }
    if (isComponentExist(resultScroller)) {
      remove(resultScroller);
    }
    if (isComponentExist(descriptions)) {
      remove(descriptions);
    }
    if (isComponentExist(descScroller)) {
      remove(descScroller);
    }
    revalidate();
    repaint();
    if (e.getSource().equals(searchButton) && cityName.getText() != null) {
      result = new JList<String>();
      result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      result.setLayoutOrientation(JList.VERTICAL);
      descriptions = new JEditorPane();
      descriptions.setContentType("text/html");
      descriptions.setEditable(false);
      CityFinder cities = new CityFinder(cityName.getText(), cityList);
      if (cities.getWeathers().size() != 0) {
        if (optList.getSelectedItem().toString().equals("Current Weather")) {
          result.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
              return cities.getWeathers().size();
            }

            @Override
            public String getElementAt(int i) {
              String city = cities.getWeathers().get(i).getCityInfo().getName();
              String country = cities.getWeathers().get(i).getCityInfo().getCountry();
              return city + ", " + country;
            }
          });
          result.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
              if (isComponentExist(descriptions)) {
                remove(descriptions);
              }
              if (isComponentExist(descScroller)) {
                remove(descScroller);
              }
              int i = result.getSelectedIndex();
              CityWeather currW = cities.getWeathers().get(i);
              descriptions.setText("City name: " + currW.getCityInfo().getName() 
                  + "<br>Country: " + currW.getCityInfo().getCountry() 
                  + "<br>Location: Lat: " + currW.getCityInfo().getLatitude()
                  + ", Lon: " + currW.getCityInfo().getLongitude() 
                  + "<br>Current Weather:" 
                  + "<br><img src=\"" + currW.getCurrentWeather().getIcon() + "\">" 
                  + "<br>Status: " + currW.getCurrentWeather().getMain()
                  + "<br>&emsp;&emsp;&emsp;&emsp;" 
                  + currW.getCurrentWeather().getDescription() + "<br>Temperature: "
                  + currW.getCurrentWeather().getTemperature() + " K" + "<br>Pressure: "
                  + currW.getCurrentWeather().getPressure() + " hPa" + "<br>Humidity: "
                  + currW.getCurrentWeather().getHumidity() + "%" + "<br>Wind Speed: "
                  + currW.getCurrentWeather().getWindSpeed() + " m/s" + "<br>Wind Degree: "
                  + currW.getCurrentWeather().getWindDegree() + " degrees");

              gbc.gridwidth = 2;
              gbc.fill = GridBagConstraints.HORIZONTAL;
              gbc.gridx = 2;
              gbc.gridy = 1;
              add(descriptions, gbc);
              revalidate();
              repaint();
            }
          });
        } else {
          result.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
              return cities.getForecasts().size();
            }

            @Override
            public String getElementAt(int i) {
              String city = cities.getForecasts().get(i).getCityInfo().getName();
              String country = cities.getForecasts().get(i).getCityInfo().getCountry();
              return city + ", " + country;
            }
          });
          result.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
              if (isComponentExist(descriptions)) {
                remove(descriptions);
              }
              if (isComponentExist(descScroller)) {
                remove(descScroller);
              }
              int i = result.getSelectedIndex();
              CityForecast currF = cities.getForecasts().get(i);
              String temp = "City name: " + currF.getCityInfo().getName() 
                  + "<br>Country: " + currF.getCityInfo().getCountry() 
                  + "<br>Location: Lat: " + currF.getCityInfo().getLatitude()
                  + ", Lon: " + currF.getCityInfo().getLongitude() 
                  + "<br>Weather Forecast for 3 days, every 6 hours:";
              for (int j = 0; j < 24; j += 2) {
                temp += "<br>Date: " + currF.getDayTime()[j] 
                    + "<br><img src=\"" + currF.getForecast()[j].getIcon() + "\">" 
                    + "<br>Status: " + currF.getForecast()[j].getMain() 
                    + "<br>&emsp;&emsp;&emsp;&emsp;"
                    + currF.getForecast()[j].getDescription() + "<br>Temperature: "
                    + currF.getForecast()[j].getTemperature() + " K" + "<br>Pressure: "
                    + currF.getForecast()[j].getPressure() + " hPa" + "<br>Humidity: "
                    + currF.getForecast()[j].getHumidity() + "%" + "<br>Wind Speed: "
                    + currF.getForecast()[j].getWindSpeed() + " m/s" + "<br>Wind Degree: "
                    + currF.getForecast()[j].getWindDegree() + " degrees<br>";
              }
              descriptions.setText(temp);
              descScroller = new JScrollPane(descriptions);
              descScroller.setPreferredSize(new Dimension(250, 300));
              descriptions.setCaretPosition(0);
              gbc.gridwidth = 2;
              gbc.fill = GridBagConstraints.HORIZONTAL;
              gbc.gridx = 2;
              gbc.gridy = 1;
              add(descScroller, gbc);
              revalidate();
              repaint();
            }
          });
        }
        resultScroller = new JScrollPane(result);
        resultScroller.setPreferredSize(new Dimension(300, 100));
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 0, 0, 50);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(resultScroller, gbc);
      } else {
        noRes = new JLabel("No Result");
        noRes.setForeground(Color.white);
        noRes.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(50, 0, 50, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(noRes, gbc);
      }
      revalidate();
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(searchBackground, 0, 0, getWidth(), getHeight(), this);
  }

  /**
   * Method to check if the container contains a component.
   * @param c Component to be checked.
   * @return true if c is in the container.
   */
  public boolean isComponentExist(Component c) {
    Component[] components = this.getComponents();
    for (Component component : components) {
      if (c == component) {
        return true;
      }
    }
    return false;
  }

  public JComboBox<String> getOptList() {
    return optList;
  }

  public JTextField getCityName() {
    return cityName;
  }

  public JButton getSearchButton() {
    return searchButton;
  }

  public JList<String> getResult() {
    return result;
  }

  public JEditorPane getDescriptions() {
    return descriptions;
  }

  public String getCityList() {
    return cityList;
  }

  public void setCityList(String cityList) {
    this.cityList = cityList;
  }
}

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import api.CityFinder;
import api.CityForecast;
import api.CityWeather;
@SuppressWarnings("serial")
public class SearchDisplay extends JPanel implements ActionListener{
    private BufferedImage searchBG;
    private JComboBox<String> optList;
    private JTextField cityName;
    private JButton searchButton;
    private JList<String> result;
    private JEditorPane descriptions;
    private JLabel noRes;
    private JScrollPane resultScroller;
    private JScrollPane descScroller;
    
    public SearchDisplay() {
        setLayout(new FlowLayout());
        try {
            searchBG = ImageIO.read(new File("asset/search.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optList = new JComboBox<>(new String[] {"Forecast", "Current Weather"});
        JLabel query = new JLabel("Enter City Name: ");
        query.setForeground(Color.white);
        cityName = new JTextField(20);
        searchButton = new JButton("search");
        searchButton.addActionListener(this);
        add(optList);
        add(query);
        add(cityName);
        add(searchButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(noRes != null) {
            remove(noRes);
        }
        if(result != null) {
            remove(resultScroller);
            remove(descScroller);
        }
        revalidate();
        repaint();
        if(e.getSource().equals(searchButton) && cityName.getText() != null) {
           
            CityFinder cities = new CityFinder(cityName.getText(), Master.cityList);
            result = new JList<String>();
            result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            result.setLayoutOrientation(JList.VERTICAL);
            descriptions = new JEditorPane();
            descriptions.setContentType("text/html");
            descriptions.setEditable(false);
            if(cities.getWeathers().size() != 0) {
                if(optList.getSelectedItem().toString().equals("Current Weather")) {
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
                            int i = result.getSelectedIndex();
                            CityWeather currCW = cities.getWeathers().get(i);
                            descriptions.setText("City name: " + currCW.getCityInfo().getName() +
                                    "<br>Country: " + currCW.getCityInfo().getCountry() +
                                    "<br>Location: Lat: " + currCW.getCityInfo().getLatitude() +
                                    ", Lon: " + currCW.getCityInfo().getLongitude() +
                                    "<br>Current Weather:" +
                                    "<br><img src=\"" + currCW.getCurrentWeather().getIcon() + "\">" +
                                    "<br>Status: " + currCW.getCurrentWeather().getMain() +
                                    "<br>&emsp;&emsp;&emsp;&emsp;" + currCW.getCurrentWeather().getDescription() +
                                    "<br>Temperature: " + currCW.getCurrentWeather().getTemperature() + " K" +
                                    "<br>Pressure: " + currCW.getCurrentWeather().getPressure() + " hPa" +
                                    "<br>Humidity: " + currCW.getCurrentWeather().getHumidity() + "%" +
                                    "<br>Wind Speed: " + currCW.getCurrentWeather().getWindSpeed() + " m/s" +
                                    "<br>Wind Degree: " + currCW.getCurrentWeather().getWindDegree() + " degrees"
                                    );
                        }
                    });
                }
                else {
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
                            int i = result.getSelectedIndex();
                            CityForecast currCF = cities.getForecasts().get(i);
                            String temp = "City name: " + currCF.getCityInfo().getName() +
                                    "<br>Country: " + currCF.getCityInfo().getCountry() +
                                    "<br>Location: Lat: " + currCF.getCityInfo().getLatitude() +
                                    ", Lon: " + currCF.getCityInfo().getLongitude() +
                                    "<br>Weather Forecast for 3 days, every 6 hours:";
                            for(int j = 0; j < 24; j += 2) {
                                temp += "<br>Date: " + currCF.getDayTime()[j] +
                                        "<br><img src=\"" + currCF.getForecast()[j].getIcon() + "\">" +
                                        "<br>Status: " + currCF.getForecast()[j].getMain() +
                                        "<br>&emsp;&emsp;&emsp;&emsp;" + currCF.getForecast()[j].getDescription() +
                                        "<br>Temperature: " + currCF.getForecast()[j].getTemperature() + " K" +
                                        "<br>Pressure: " + currCF.getForecast()[j].getPressure() + " hPa" +
                                        "<br>Humidity: " + currCF.getForecast()[j].getHumidity() + "%" +
                                        "<br>Wind Speed: " + currCF.getForecast()[j].getWindSpeed() + " m/s" +
                                        "<br>Wind Degree: " + currCF.getForecast()[j].getWindDegree() + " degrees<br>";
                            }
                            descriptions.setText(temp);
                        }
                    });
                }
                resultScroller = new JScrollPane(result);
                descScroller = new JScrollPane(descriptions);
                resultScroller.setPreferredSize(new Dimension(250, 80));
                descScroller.setPreferredSize(new Dimension(250, 80));
                add(resultScroller, "result");
                add(descScroller, "descriptions");
            }
            else {
                noRes = new JLabel("No Result");
                noRes.setForeground(Color.white);
                add(noRes, "noresult");
            }
            revalidate();
            repaint();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(searchBG, 0, 0, getWidth(), getHeight(), this);
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
}

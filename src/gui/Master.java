package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Master extends JFrame implements ActionListener {
  private static final int SCREENWIDTH = 960;
  private static final int SCREENHEIGHT = 540;
  private CardLayout layout;
  private JPanel masterPanel;
  private JPanel cover;
  private SearchDisplay search;
  private String cityList;
  private JProgressBar progressBar;

  /**
   * Constructor.
   * @param width width of the application.
   * @param height height of the application.
   */
  public Master(int width, int height) {
    addPanel();
    start(width, height);
    layout.show(masterPanel, "search");
  }

  /**
   * Method to initialize Java Swing window for the application.
   * @param width width size of the application window.
   * @param height height size of the application window.
   */
  public void start(int width, int height) {
    setSize(width, height);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    setTitle("Weather_Forecast v.01");
    ImageIcon img = new ImageIcon("asset/appIcon.png");
    setIconImage(img.getImage());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    requestFocus();
    Loader fileLoader = new Loader();
    fileLoader.start();
    for (int i = 0; i <= 100; i += randInt(0, 5)) {
      final int progress = i;
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          progressBar.setValue(progress);
        }
      });
      try {
        Thread.sleep(randInt(200, 500));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      fileLoader.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    search.setCityList(cityList);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  /**
   * Method to add GUI panels for the application.
   */
  public void addPanel() {
    layout = new CardLayout();
    masterPanel = new JPanel();
    progressBar = new JProgressBar(0, 100);
    progressBar.setValue(0);
    progressBar.setStringPainted(true);
    Image backGround = Toolkit.getDefaultToolkit().createImage("asset/main.jpg");
    cover = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
      }
    };
    search = new SearchDisplay();
    search.getSearchButton().addActionListener(this);
    cover.setLayout(new FlowLayout());
    cover.add(progressBar);
    masterPanel.setLayout(layout);
    masterPanel.add(search, "search");
    masterPanel.add(cover, "cover");
    add(masterPanel);
    layout.show(masterPanel, "cover");

  }

  private class Loader extends Thread {
    public void run() {
      try {
        cityList = new String(Files.readAllBytes(Paths.get("asset/city.list.json")));
        Thread.sleep(100);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    @SuppressWarnings("unused")
    Master app = new Master(SCREENWIDTH, SCREENHEIGHT);
  }

  /**
   * Method to generate random integer between given range.
   * @param min minimum return value
   * @param max maximum return value
   * @return random integer between min and max.
   */
  public int randInt(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  public String getCityList() {
    return cityList;
  }
}

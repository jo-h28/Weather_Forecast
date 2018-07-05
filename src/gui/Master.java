package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
@SuppressWarnings("serial")
public class Master extends JFrame implements ActionListener{
    private static final int SCREENWIDTH = 1280, SCREENHEIGHT = 720;
    private CardLayout layout;
    private JPanel masterPanel;
    private JPanel cover;
    private SearchDisplay search;
    static String cityList;
    private JProgressBar progressBar;
    public Master(int width, int height) {
        addPanel();
        start(width, height);
        layout.show(masterPanel, "search");
    }
    
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
        for(int i = 0; i <= 100; i += randInt(0, 10)){
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
      }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void addPanel() {
        layout = new CardLayout();
        masterPanel = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Image backGround = Toolkit.getDefaultToolkit().createImage("asset/main.jpg");
        cover = new JPanel() { @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }};
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
    
    private class Loader extends Thread{
        public void run(){
            try {
                cityList = FileUtils.readFileToString(new File("asset/city.list.json"), "utf-8");
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

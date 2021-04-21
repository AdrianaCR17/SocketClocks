/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ADRIANA
 */
public class RelojcitoCliente extends JPanel implements Runnable {

    private volatile boolean shutdown;
    Calendar calendar;
    float timeFactor = 1;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    JLabel tiempo;

    public RelojcitoCliente() {
        this.calendar = Calendar.getInstance();
        setConfig();
    }

    public RelojcitoCliente(String random) {
        Integer hour = getRandomTime(24);
        Integer minute = getRandomTime(60);
        Integer second = getRandomTime(60);
        
        setTime(hour, minute, second);
        setConfig();
    }
    
    public Integer getRandomTime(int n){
        int random = (int)(Math.random()*(n+1));
        return random;
    }
    
    public void stop() {
        shutdown = true;
    }

    public void reanudar() {
        shutdown = false;
    }

     @Override
    public void run() {
        while (true) {
            while (!shutdown) {
                try {
                    this.calendar.add(Calendar.SECOND, 1);
                    setText();
                    Thread.sleep((long) (this.timeFactor * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setText(Integer hora, Integer minuto, Integer segundo) {
        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.HOUR_OF_DAY, hora);
        this.calendar.set(Calendar.MINUTE, minuto);
        this.calendar.set(Calendar.SECOND, segundo);
    }

    private void setText() {
        tiempo.setText(timeFormat.format(this.calendar.getTime()));
    }

    private void setConfig() {
        //configuración del relojcito
        this.tiempo = new JLabel();
        tiempo.setFont(new Font("Arial", Font.BOLD, 35));
        tiempo.setForeground(Color.BLACK);
        tiempo.setBackground(Color.WHITE);
        tiempo.setOpaque(true);
        setText();
        add(tiempo);
    }

    private void setTime(Integer hora, Integer minuto, Integer segundo) {
        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.HOUR_OF_DAY, hora);
        this.calendar.set(Calendar.MINUTE, minuto);
        this.calendar.set(Calendar.SECOND, segundo);
    }
}


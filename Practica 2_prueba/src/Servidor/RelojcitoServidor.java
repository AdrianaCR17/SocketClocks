package Servidor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RelojcitoServidor extends JPanel implements Runnable {

    private volatile boolean shutdown;
    Calendar calendar;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    float timeFactor = 1;

    JLabel tiempo;
    JButton editar;
    JButton enviar;
    JButton aumentar;
    JButton disminuir;

    public RelojcitoServidor() {
        this.calendar = Calendar.getInstance();
        setConfig();
    }

    public RelojcitoServidor(String random) {
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

        //configuración de botón editar
        this.editar = new JButton();
        editar.setBorder(BorderFactory.createEmptyBorder());
        editar.setContentAreaFilled(false);
        editar.setBounds(0, 0, 30, 30);
        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
                editar.setEnabled(false);
                boolean valid = false;
                while (!valid) {
                    String timeString = JOptionPane.showInputDialog(null, "Ingrese la nueva horrra.",
                            timeFormat.format(calendar.getTime()));
                    if (timeString == null || (timeString != null && ("".equals(timeString)))) {
                        reanudar();
                        editar.setEnabled(true);
                    } else {
                        String[] aux = timeString.split(":");
                        if (aux.length != 3) {
                            JOptionPane.showMessageDialog(null, "El formato no es válido");
                        } else {
                            valid = true;
              
                            setTime(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                            //System.out.println(aux[0] + aux[1] + aux[2]);
                            stop();
                            editar.setEnabled(true);
                        }
                    }
                    valid = true;
                }
            }
        });
        try {
            BufferedImage image;
            image = ImageIO.read(new File("./resources/editar.png"));
            editar.setIcon(
                    new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(editar);
        //acción de enviar
        this.enviar = new JButton();
        enviar.setBorder(BorderFactory.createEmptyBorder());
        enviar.setContentAreaFilled(false);
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviar.setEnabled(true);
                timeFactor = timeFactor * 3;
                try {
                    Thread.sleep((long) (timeFactor * 1000));
                    enviar.setEnabled(true);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        try {
            BufferedImage imagen;
            imagen = ImageIO.read(new File("./resources/enviar.png"));
            enviar.setIcon(
                    new ImageIcon(new ImageIcon(imagen).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(enviar);
/*
        //configuración de disminuir velocidad
        this.disminuir = new JButton();
        disminuir.setBorder(BorderFactory.createEmptyBorder());
        disminuir.setContentAreaFilled(false);
        disminuir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disminuir.setEnabled(false);
                timeFactor = timeFactor * 3;
                try {
                    Thread.sleep((long) (timeFactor * 1000));
                    disminuir.setEnabled(true);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        try {
            BufferedImage imagen;
            imagen = ImageIO.read(new File("./resources/disminuye.png"));
            disminuir.setIcon(
                    new ImageIcon(new ImageIcon(imagen).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(disminuir);

        //configura aumentar velocidad
        this.aumentar = new JButton();
        aumentar.setBorder(BorderFactory.createEmptyBorder());
        aumentar.setContentAreaFilled(false);
        aumentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aumentar.setEnabled(false);
                timeFactor = timeFactor / 3;
                try {
                    Thread.sleep((long) (timeFactor * 1000));
                    aumentar.setEnabled(true);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        try {
            BufferedImage imagen;
            imagen = ImageIO.read(new File("./resources/aumenta.png"));
            aumentar.setIcon(
                    new ImageIcon(new ImageIcon(imagen).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(aumentar);*/

    }

    private void setTime(Integer hora, Integer minuto, Integer segundo) {
        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.HOUR_OF_DAY, hora);
        this.calendar.set(Calendar.MINUTE, minuto);
        this.calendar.set(Calendar.SECOND, segundo);
    }
}

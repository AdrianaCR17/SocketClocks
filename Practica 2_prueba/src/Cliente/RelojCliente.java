package Cliente;

import Servidor.RelojcitoServidor;
import static com.sun.tools.javac.tree.TreeInfo.args;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADRIANA
 */

public class RelojCliente extends JFrame{
    Socket cliente;
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    LocalDateTime locaDate = LocalDateTime.now();
    int hours  = locaDate.getHour();
    int minutes = locaDate.getMinute();
    int seconds = locaDate.getSecond();
    RelojcitoCliente relojcitoC;

    public RelojCliente(){
     this.setLayout(new FlowLayout());
        this.getContentPane().setBackground(Color.darkGray);
        this.setSize(310, 300);
        setLocationRelativeTo(null);
        setTitle("Cliente");
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }
    
    public void init()
    {
        this.relojcitoC = new RelojcitoCliente();
        Thread h1 = new Thread(relojcitoC);
        this.add(relojcitoC);
        h1.start();
        establecerConexion();
    }
    private void establecerConexion(){
        //puerto del servidor
        final int PUERTO_SERVIDOR = 5000;
        //buffer donde se almacenara los mensajes
        byte[] buffer = new byte[1024];
 
        try {
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName("localhost");
 
            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();
 
            String mensaje = "¡Hola mundo desde el cliente!";
 
            //Convierto el mensaje a bytes
            buffer = mensaje.getBytes();
 
            //Creo un datagrama
            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
 
            //Lo envio con send
            System.out.println("Envio el datagrama");
            socketUDP.send(pregunta);
 
            //Preparo la respuesta
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
 
            //Recibo la respuesta
            socketUDP.receive(peticion);
            System.out.println("Recibo la peticion");
 
            //Cojo los datos y lo muestro
            mensaje = new String(peticion.getData());
            System.out.println(mensaje);
 
            //cierro el socket
            socketUDP.close();
 
        } catch (SocketException ex) {
            Logger.getLogger(RelojCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(RelojCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelojCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    }




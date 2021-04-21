package Servidor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RelojServidor extends JFrame {
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    LocalDateTime locaDate = LocalDateTime.now();
    int hours  = locaDate.getHour();
    int minutes = locaDate.getMinute();
    int seconds = locaDate.getSecond();
    RelojcitoServidor relojcito1;
    RelojcitoServidor relojcito2;
    RelojcitoServidor relojcito3;
    RelojcitoServidor relojcito4;

    /*Thread h1;
    Thread h2;
    Thread h3;
    Thread h4;
*/
    public RelojServidor(){
     this.setLayout(new FlowLayout());
        this.getContentPane().setBackground(Color.darkGray);
        this.setSize(310, 300);
        setLocationRelativeTo(null);
        setTitle("Servidor");
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }
    
    public void init() {
        this.relojcito1 = new RelojcitoServidor();
        Thread h1 = new Thread(relojcito1);
        this.add(relojcito1);
        
        this.relojcito2 = new RelojcitoServidor("random");
        Thread h2 = new Thread(relojcito2);
        this.add(relojcito2);
        
        this.relojcito3 = new RelojcitoServidor("random");
        Thread h3 = new Thread(relojcito3);
        this.add(relojcito3);
        
        /*this.relojcito4 = new RelojcitoServidor("random");
        Thread h4 = new Thread(relojcito4);
        this.add(relojcito4);*/
        
        h1.start();
        h2.start();
        h3.start();
        //h4.start();
        establecerConexion();
    }
    
    private void establecerConexion(){
        final int PUERTO = 5000;
        byte[] buffer = new byte[1024];
 
        try {
            System.out.println("Iniciado el servidor UDP");
            //Creacion del socket
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
 
            //Siempre atendera peticiones
            while (true) {
                 
                //Preparo la respuesta
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                 
                //Recibo el datagrama
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");
                 
                //Convierto lo recibido y mostrar el mensaje
                String mensaje = new String(peticion.getData());
                System.out.println(mensaje);
 
                //Obtengo el puerto y la direccion de origen
                //Sino se quiere responder, no es necesario
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
 
                mensaje = "¡Hola mundo desde el servidor!";
                buffer = mensaje.getBytes();
 
                //creo el datagrama
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
 
                //Envio la información
                System.out.println("Envio la informacion del cliente");
                socketUDP.send(respuesta);
                 
            }
 
        } catch (SocketException ex) {
            Logger.getLogger(RelojServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelojServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
  }
    /*try {//siempre debe hacerse dentro de un bloque try-catch
        DatagramSocket socket = new DatagramSocket(Constantes.PUERTO_DEL_SERVIDOR,InetAddress.getByName("localhost");
            ServerSocket servidor = new ServerSocket(1233); //Se crea el socket servidor con nÃºmero de puerto definido
            System.out.println("Esperando cliente...");
            for (;;) {//Espera dentro de un ciclo infinito la solicitud de conexion de un cliente
                Socket cl = servidor.accept(); //acepta conexiÃ³n del cliente
                System.out.println("Conexion establecida desde " + cl.getInetAddress() + ":" + cl.getPort());
                String mensaje = "Hola mundo"; //Se define el mensaje para el cliente
                //canal de escritura
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cl.getOutputStream())),true); //Se liga un printwriter a un flujo de salida de caracter
                pw.println(mensaje);
                //canal de lectura
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                String msj = br.readLine();//Se lee el mensaje recibido
                System.out.println("Recibimos un mensaje desde el cliente: "+msj);
                pw.flush(); //Se limpia el flujo de salida
                pw.close();//Se cierra el flujo de datos
                cl.close(); //Se cierra la conexiÃ³n del socket
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
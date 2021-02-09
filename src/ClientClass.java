
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class ClientClass {

    DatagramSocket socket;
    Scanner sc;

    public void createAndListenSocket() {
        sc = new Scanner(System.in);
        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("192.168.31.26");

            CalculationRequest request = new CalculationRequest();
            System.out.print("Enter first number: ");
            request.setOperand1(sc.nextInt());
            System.out.print("Enter second number: ");
            request.setOperand2(sc.nextInt());
            System.out.print("Enter operator: ");
            sc.nextLine();
            request.setOperator(sc.next().charAt(0));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream oostream = new ObjectOutputStream(stream);
            oostream.writeObject(request);
            byte[] sendData = stream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 9876);
            socket.send(sendPacket);
            System.out.println("Request Sent");

            byte[] data = new byte[1024];
            DatagramPacket incomingPacket = new DatagramPacket(data, data.length);
            socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println(response);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String arg[]) {
        ClientClass client = new ClientClass();
        client.createAndListenSocket();
    }
}

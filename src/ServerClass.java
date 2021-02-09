
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class ServerClass {

    DatagramSocket datagramsocket = null;
    CalculationRequest request;

    public void createAndListenSocket() {
        try {
            datagramsocket = new DatagramSocket(9876);
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramsocket.receive(packet);
                byte[] data = packet.getData();
                ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
                ObjectInputStream oistream = new ObjectInputStream(stream);
                request = (CalculationRequest) oistream.readObject();
                System.out.println("Request Recieved");
                int result = calculate(request);
                InetAddress IPAddress = packet.getAddress();
                int port = packet.getPort();
                String reply = "The output of your calculation request is " + result;
                byte[] replyData = reply.getBytes();
                DatagramPacket replyPacket
                        = new DatagramPacket(replyData, replyData.length, IPAddress, port);
                datagramsocket.send(replyPacket);
                Thread.sleep(2000);
               // System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculate(CalculationRequest request) {
        int result = 0;
        int operand1, operand2;
        char operator;
        operand1 = request.getOperand1();
        operand2 = request.getOperand2();
        operator = request.getOperator();
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            case '%':
                result = operand1 % operand2;
                break;

        }
        return result;
    }

    public static void main(String args[]) {
        ServerClass server = new ServerClass();
        server.createAndListenSocket();
    }
}

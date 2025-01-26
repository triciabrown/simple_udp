
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleUdpClient {
	static int serverPort = 8000;

	public static void main(String[] args) throws IOException {
		byte[] buffer = null;
		
		try {
			InetAddress serverIp = InetAddress.getLocalHost();
			Scanner scanner = new Scanner(System.in);
            System.out.println("Input message to send to the server: ");
			
			while(true) {
				//get byte array from user input to send in package
				String userInput = scanner.nextLine();
				buffer = userInput.getBytes();

				//create client socket and message packet to send
				DatagramSocket clientSocket = new DatagramSocket();
				DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length, serverIp, serverPort);
				
				clientSocket.send(udpPacket);
				
                //quit program on exit command
				if (userInput.equals("exit")) {
					clientSocket.close();
					break;
				}

                //receive message back from server
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                //print server response
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + response);
				
				
			}
		} 
		catch (SocketException e) {
			System.out.println("There was a problem creating a socket on port " + serverPort + " : " +e.getMessage());
		} 
		catch (UnknownHostException ex) {
			System.out.println("There was a problem finding the local host : " +ex.getMessage());
			
		}
	}

}

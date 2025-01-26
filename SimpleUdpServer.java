import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SimpleUdpServer {
	static int port = 8000; //port to listen for messages on
	

    public static void main(String[] args) {
    	try {
			DatagramSocket socket = new DatagramSocket(port);
			byte[] buffer = new byte[4096];
			DatagramPacket receivedPacket = null;
            System.out.println("Listening for messages on port " + port);
			
			while (true) {
				receivedPacket = new DatagramPacket(buffer, buffer.length);
				
				socket.receive(receivedPacket);
				String msgReceived = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
				
				
				//print out message received to the console
				System.out.println("Received from client: " + msgReceived);
				
				//quit loop if user said to exit
				if (msgReceived.equals("exit")) {
					System.out.println("Shutting down server... ");
					socket.close();
					break;
				}
				
				//reset buffer after receiving message
				buffer = new byte[4096];
				
				String response = "Message received";
				byte[] responseBuffer = response.getBytes();
				DatagramPacket packet = new DatagramPacket(responseBuffer, responseBuffer.length, receivedPacket.getAddress(), receivedPacket.getPort());
				
				//send response back to client
				socket.send(packet);
				
			}
		} catch (SocketException e) {
			System.out.println("There was a problem creating the receive socket: " +e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("There was a problem reading from the socket: " +e.getMessage());
			e.printStackTrace();
		}
    	
    }
}

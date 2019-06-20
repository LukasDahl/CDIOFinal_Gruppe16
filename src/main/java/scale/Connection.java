/*
Forfatter: Lukas
Ansvar: Denne klasse skaber en connection til vægten, og starter read og write klasserne.
 */

package scale;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection{

	Read read;
	Write write;

	public void createConnection() throws IOException{
		Socket socket;
		socket = new Socket("169.254.2.2", 8000);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		read = new Read(br);
		write = new Write(dos);
		write.start();
		read.start();


	}

	public String getRead(){
		return read.getInput();
	}

	public void setWrite(String output){
		write.setOutput(output);
	}
}

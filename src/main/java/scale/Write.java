/*
Forfatter: August
Ansvar: Denne klasse bruges til at skrive commands til vægten.
 */

package scale;

import java.io.DataOutputStream;
import java.io.IOException;

public class Write extends Thread{

	volatile String output;
	DataOutputStream dos;
	boolean newOutput = false;
	public Write(DataOutputStream dos) {

		this.dos = dos;

	}

	@Override
	public void run() {
		while (true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				if (newOutput){
					dos.writeBytes(output+ "\n");
					newOutput = false;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setOutput(String output) {
		this.output = output;
		newOutput = true;
	}
}

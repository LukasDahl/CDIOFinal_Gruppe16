/*
Forfatter: August
Ansvar: Denne klasse bruges til at læse svar fra vægten.
 */

package scale;

import java.io.BufferedReader;
import java.io.IOException;

public class Read extends Thread{
	private volatile String input;
	private BufferedReader br;
	boolean newInput= false;
	public Read(BufferedReader br) {
		this.br = br;

	}

	@Override
	public void run(){

		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				if (br.ready()) {
					input = br.readLine();
					newInput = true;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	public String getInput() {
		if (newInput) {
			newInput = false;
			return input;
		}
		else
			return "tom";
	}

}

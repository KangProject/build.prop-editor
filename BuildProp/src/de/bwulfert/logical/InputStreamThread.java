package de.bwulfert.logical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.bwulfert.debug.d;

public class InputStreamThread extends Thread {

	private InputStream inputStream;
	volatile ArrayList<String> list;

	public ArrayList<String> getResultSet() {
		return list;
	}

	public InputStreamThread(InputStream inputStream, String name) {
		this.inputStream = inputStream;
		setName(name);
		list = new ArrayList<String>();
	}

	public void run() {

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();

		try {
			
			int readChar;
	
			while (!isInterrupted() && (readChar = br.read()) != -1){			
				if(readChar == '\n' && buffer.length() != 2 && buffer.length() > 0 && !buffer.toString().equals("\n")){			
						list.add(buffer.toString());
						buffer.setLength(0);
				} else {
					buffer.append((char) readChar);
				}
			}

			br.close();

		} catch (IOException e) {
			d.l(e.getMessage());
		}

	}
}

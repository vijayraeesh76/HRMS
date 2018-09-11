package com.hrms.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class sample {
public static void main(String[] args) throws IOException, InterruptedException {
	StringBuilder word = null;

	InputStream in = System.in;
	BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	word = new StringBuilder(reader.readLine());
	int wordLength = word.length();
	
	for (int i = 0; i < 100; i++) {
		System.out.print(word);
		Thread.sleep(100);
		for(int j=0; j<wordLength;j++) {
			System.out.print("\b");
		}
		word.insert(0," ");
		wordLength++;
	}
}
}

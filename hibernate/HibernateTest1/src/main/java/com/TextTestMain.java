package com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TextTestMain {
	
	public static void main(String[] args) {
		try {
			String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\1132535\\Desktop\\tempbkp\\window_dump.xml")));
			System.out.println(content);
			
            // use apk path
            // get storage first

            // get text=" number
//			Files.write(Paths.get("counter.txt"), content.getBytes(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

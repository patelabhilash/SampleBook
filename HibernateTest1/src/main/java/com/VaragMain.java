package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VaragMain {
	
	public static void main(String[] args) {
		System.out.println(3);
		new VaragMain().getParamAdbCommand("taskkill /F /IM notepad++.exe");
//		new VaragMain().getParamAdbCommand("tasklist");
	}

	
	private String getParamAdbCommand(String ...commandOne) {
		String ansofParamAdb = null;
		String[] arrcommand = new String[commandOne.length+2];// commandOne;
		arrcommand[0] = "cmd.exe";
		arrcommand[1] = "/C";
		System.arraycopy(commandOne, 0, arrcommand, 2, commandOne.length);
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(arrcommand);
		try {
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success in performing " + commandOne);
				ansofParamAdb = output.toString();
				System.out.println(ansofParamAdb);
			} else {
				// abnormal...
				System.out.println("FAILURE ingetParamAdbCommand");
				System.out.println(ansofParamAdb);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ansofParamAdb;
	}
	
}

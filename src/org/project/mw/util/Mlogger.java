package org.project.mw.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Mlogger {
	public static void log(String message) {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter("output.txt", true));
			out.write(new Date().toGMTString() +" "+message+"\n");
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}

package org.jdog.Raptor.js.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class InputReader {
	public static String read(InputStream in) throws Exception {
		String out = null;
		if (in != null) {
			InputStreamReader inStreamReader = new InputStreamReader(in);
			StringWriter stWriter = new StringWriter();

			if (inStreamReader != null) {
				while (true) {
					int c = inStreamReader.read();
					if (c == -1) {
						break;
					}
					stWriter.write(c);
				}
				inStreamReader.close();
				in.close();

				out = stWriter.toString();
				stWriter.close();
			}
		}
		return out;
	}
}

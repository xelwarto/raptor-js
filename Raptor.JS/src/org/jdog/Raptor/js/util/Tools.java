package org.jdog.Raptor.js.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	public static HashMap<String, String> getParams(String[] appParams) {
		HashMap<String, String> paramStore = new HashMap<String, String>();
		if (appParams.length > 0) {
			for (int i = 0; i < appParams.length; i++) {
				Pattern paramPattern = Pattern.compile("^\\--?.*");
				Matcher paramMatcher = paramPattern.matcher(appParams[i]);
				boolean paramMatch = paramMatcher.find();

				if (paramMatch) {
					String value = "1";
					String key = appParams[i];
					key = key.replaceAll("^-+", "");
					i++;

					if (i < appParams.length) {
						paramPattern = Pattern.compile("^\\--?.*");
						paramMatcher = paramPattern.matcher(appParams[i]);
						paramMatch = paramMatcher.find();

						if (paramMatch) {
							i--;
						} else {
							value = appParams[i];
						}
					}
					paramStore.put(key, value);
				}
			}
		}
		return paramStore;
	}
}

package org.jdog.Raptor.js.handler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class ConfigHandler {
	public static ConfigHandler cfgHandler = null;
	public Properties cfgProps = null;

	public ConfigHandler() {
		cfgProps = new Properties();
	}

	public static ConfigHandler getHanlder() {
		if (cfgHandler == null) {
			cfgHandler = new ConfigHandler();
		}
		return cfgHandler;
	}

	public void load(String file) throws Exception {
		if (file != null && !file.equals("")) {
			File cfgFile = new File(file);

			if (cfgFile.exists() && cfgFile.canRead()) {
				if (cfgProps != null) {
					cfgProps.load(new FileInputStream(cfgFile));
				} else {
					throw new Exception("Configuration properties is null");
				}
			} else {
				throw new Exception(
						"Configuration file cannot be located or read");
			}
		} else {
			throw new Exception("Configuration file cannot be null or blank");
		}
	}

	public String get(String key) {
		if (key != null && !key.equals("")) {
			return cfgProps.getProperty(key);
		}
		return null;
	}

	public ArrayList<String> gets(String key) {
		ArrayList<String> values = new ArrayList<String>();
		Set<String> propsNames = cfgProps.stringPropertyNames();
		
		for (String name : propsNames) {
			if (name != null && name.matches(key + "\\[.*\\]")) {
				String value = get(name);
				if (value != null) {
					values.add(value);
				}
			}
		}
		return values;
	}

	public void set(String key, String value) {
		if (key != null && !key.equals("")) {
			if (value != null) {
				cfgProps.setProperty(key, value);
			}
		}
	}
	
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> hash = new HashMap<String, String>();
		
		for (Object key: cfgProps.keySet()) {
			if (key instanceof String) {
				String name = (String) key;
				String value = get(name);
				hash.put(name, value);
			}
		}
		return hash;
	}

	public void destory() {
		cfgHandler = null;
		cfgProps.clear();
		cfgProps = null;
	}
}

package org.jdog.Raptor.js.handler;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.jdog.Raptor.js.util.InputReader;

public class ApplicationHandler {
	public static ApplicationHandler appHandler = null;
	HashMap<String, String> paramsMap = null;

	public ApplicationHandler(String cfgFile) throws Exception {
		ConfigHandler.getHanlder();
		if (cfgFile != null) {
			loadConfig(cfgFile);
		}

		new ContextFactoryHandler();
		ScopeHandler.getHandler();
	}

	public static ApplicationHandler getHandler(String cfgFile)
			throws Exception {
		if (appHandler == null) {
			appHandler = new ApplicationHandler(cfgFile);
		}
		return appHandler;
	}

	public void setParamsMap(HashMap<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public void loadConfig(String file) throws Exception {
		ConfigHandler.getHanlder().load(file);
	}

	public void runJS(String file) throws Exception {
		if (file != null && !file.equals("")) {
			File jsFile = new File(file);

			if (jsFile != null && jsFile.exists() && jsFile.canRead()) {
				ConfigHandler.getHanlder().set("js.file.path", file);

				String jsIn = InputReader.read(new FileInputStream(jsFile));

				JavaScriptHandler jsHandler = new JavaScriptHandler();
				jsHandler.initScope();

				jsHandler.addPrototype("out", System.out);
				jsHandler.addPrototype("parameters",
						JavaScriptHandler.convert(paramsMap));
				jsHandler.addPrototype("configuration", JavaScriptHandler
						.convert(ConfigHandler.getHanlder().toHashMap()));

				jsHandler.runString(jsIn, jsFile.getName());
			} else {
				throw new Exception(
						"JavaScript file can not be located or read");
			}
		} else {
			throw new Exception("JavaScript file can not be null or blank");
		}
	}

	public void destroy() throws Exception {
		ScopeHandler.getHandler().destroy();
		ConfigHandler.getHanlder().destory();
		appHandler = null;
	}
}

package org.jdog.Raptor.js;

import java.util.HashMap;

import org.jdog.Raptor.js.handler.ApplicationHandler;
import org.jdog.Raptor.js.handler.ConfigHandler;
import org.jdog.Raptor.js.util.Tools;
import org.jdog.Raptor.js.util.Variables;

public class App {

	public static void main(String[] appParams) {
		if (appParams != null && appParams.length > 0) {
			ApplicationHandler appHandler = null;
			String cfgFile = null;
			try {
				ConfigHandler.getHanlder().set("raptor.bin.dir",
						System.getProperty("raptor.bin.dir"));

				HashMap<String, String> paramsMap = Tools.getParams(appParams);

				if (paramsMap != null) {
					if (paramsMap.get("cfg") != null
							&& !paramsMap.get("cfg").equals("")) {
						cfgFile = paramsMap.get("cfg");
					}

					appHandler = ApplicationHandler.getHandler(cfgFile);
					
					if (appHandler != null) {
						appHandler.setParamsMap(paramsMap);
						
						String jsFile = null;
						if (paramsMap.get("js") != null
								&& !paramsMap.get("js").equals("")) {
							jsFile = paramsMap.get("js");
						} else if (ConfigHandler.getHanlder().get(
								"raptor.js.file") != null) {
							jsFile = ConfigHandler.getHanlder().get(
									"raptor.js.file");

							if (!jsFile.startsWith("/")) {
								if (ConfigHandler.getHanlder().get(
										"raptor.bin.dir") != null) {
									jsFile = ConfigHandler.getHanlder().get(
											"raptor.bin.dir");
									jsFile = jsFile.concat("/../");
									jsFile = jsFile
											.concat(ConfigHandler.getHanlder()
													.get("raptor.js.file"));
								}
							}
						} else {
							appHandler.destroy();
							showUsage();
						}

						try {
							appHandler.runJS(jsFile);
						} catch (Exception e) {
							System.out.println("Error: " + e.toString());
							System.exit(1);
						}
					} else {
						// ERROR HERE
					}
				} else {
					showUsage();
				}

				if (appHandler != null) {
					appHandler.destroy();
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
				// System.exit(1);
			}
		} else {
			showUsage();
		}

		System.exit(0);
	}

	private static void showUsage() {
		System.out.println("Raptor.JS " + Variables.appVersion + " Help\n\n"
				+ "\t--help        This help screen\n"
				+ "\t--cfg <file>  Config File\n"
				+ "\t--js <file>   Run the javascript file\n\n");

		System.exit(1);
	}
}

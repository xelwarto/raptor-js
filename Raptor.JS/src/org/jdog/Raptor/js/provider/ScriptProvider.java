package org.jdog.Raptor.js.provider;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.jdog.Raptor.js.handler.ConfigHandler;
import org.jdog.Raptor.js.util.InputReader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;

public class ScriptProvider implements ModuleScriptProvider {

	public ScriptProvider() {
	}

	@Override
	public ModuleScript getModuleScript(Context cx, String id, URI uri,
			URI base, Scriptable path) throws Exception {

		String jsFilePath = ConfigHandler.getHanlder().get("js.file.path");
		String curDirectory = null;
		File jsFile = null;

		if (jsFilePath != null && !jsFilePath.equals("")) {
			curDirectory = new File(jsFilePath).getParent();
		}

		if (id != null && !id.equals("")) {
			if (curDirectory != null && !curDirectory.equals("")) {
				jsFile = new File(curDirectory, id);
			} else {
				jsFile = new File(id);
			}
		}
		
		if (jsFile != null && jsFile.exists()) {
			if (jsFile.canRead()) {
				String js = InputReader.read(new FileInputStream(jsFile));

				if (js != null) {
					Script newScript = cx.compileString(js, id, 1, null);
					if (newScript != null) {
						return new ModuleScript(newScript, new URI(""), new URI(""));
					}
				}
			}
		}
		return null;
	}
}

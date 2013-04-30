/* 
JDog.org - Sijerian.JS
JavaScript Application Server

JavaScriptHandler.java - JavaScript evaluation handler class

Copyright 2012 Ted Elwartowski <xelwarto.pub@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.jdog.Raptor.js.handler;

import java.util.HashMap;

import org.jdog.Raptor.js.handler.ScopeHandler;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptHandler {

	private Context cx = null;
	private Scriptable scope = null;

	public JavaScriptHandler() {
		cx = Context.enter();
	}

	public void initScope() throws Exception {
		scope = ScopeHandler.getNewScope(cx);
	}

	public void addPrototype(String name, Object obj) {
		if (name != null && !name.equals("")) {
			if (obj != null) {
				if (scope != null) {
					Object jsObj = Context.javaToJS(obj, scope);
					ScriptableObject.putProperty(scope, name, jsObj);
				}
			}
		}
	}

	public void runString(String javaScript, String name) throws Exception {
		if (javaScript != null) {
			if (name == null) {
				name = "javascript";
			}
			cx.evaluateString(scope, javaScript, name, 1, null);
		}
	}

	public void exit() {
		if (cx != null) {
			cx = null;
			scope = null;
		}
		Context.exit();
	}

	public static NativeObject convert(HashMap<String, String> hash) {
		NativeObject nObj = new NativeObject();
		if (hash != null && !hash.isEmpty()) {
			for (String name : hash.keySet()) {
				if (name != null) {
					String value = hash.get(name);
					if (value != null) {
						nObj.put(name, nObj, value);
					}
				}
			}
		}
		return nObj;
	}
}
/* 
JDog.org - Raptor.JS
JavaScript Application Server

ScopeHandler.java - JavaScript scope handler class

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

import java.util.ArrayList;

import org.jdog.Raptor.js.mods.Module;
import org.jdog.Raptor.js.provider.ScriptProvider;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.commonjs.module.RequireBuilder;

public class ScopeHandler {

	private static ScopeHandler scopeHandler = null;
	private ScriptableObject scopeObject = null;
	private static RequireBuilder rBuilder = null;

	public ScopeHandler() throws Exception {
		Context cx = Context.enter();
		scopeObject = cx.initStandardObjects(null, true);

		ScriptProvider scriptProvider = new ScriptProvider();

		rBuilder = new RequireBuilder();
		rBuilder.setModuleScriptProvider(scriptProvider);
		rBuilder.setPostExec(null);
		rBuilder.setPreExec(null);
		rBuilder.setSandboxed(false);

		ArrayList<String> jsMods = ConfigHandler.getHanlder().gets("raptor.mods");
		if (jsMods.size() > 0) {
			for (String mod : jsMods) {
				addModule(mod);
			}
		}
		
		scopeObject.sealObject();
		Context.exit();
	}
	
	public void addModule(String className) throws Exception {
		Class<?> newClass = Class.forName(className);
		
		if (newClass != null) {
			Module newModule = (Module) newClass.newInstance();
			ScriptableObject.defineClass(scopeObject, newModule.getClass());
		}
	}

	public static ScopeHandler getHandler()
			throws Exception {
		if (scopeHandler == null) {
			scopeHandler = new ScopeHandler();
		}
		return scopeHandler;
	}

	public static ScriptableObject getScope() throws Exception {
		return getHandler().scopeObject;
	}

	public static Scriptable getNewScope(Context cx) throws Exception {
		if (cx != null) {
			Scriptable newScope = cx.newObject(getHandler().scopeObject);
			newScope.setPrototype(getHandler().scopeObject);
			newScope.setParentScope(null);

			rBuilder.createRequire(cx, newScope).install(newScope);
			return newScope;
		}
		return null;
	}

	public void destroy() {
		scopeHandler = null;
		scopeObject = null;
	}
}
/* 
JDog.org - Sijerian.JS
JavaScript Application Server

ContextFactoryHandler.java - JavaScript context factory handler class

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

import org.jdog.Raptor.js.util.Variables;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

public class ContextFactoryHandler extends ContextFactory {

	@SuppressWarnings("deprecation")
	private class MyContext extends Context {

		long startTime = 0;
		int timeout_seconds = Variables.contextDefaultTimeOut;
	}

	static {
		ContextFactory.initGlobal(new ContextFactoryHandler());
	}

	@Override
	protected Context makeContext() {
		MyContext cx = (MyContext) MyContext.enter();
		cx.setInstructionObserverThreshold(10000);
		
		if (ConfigHandler.getHanlder().get("raptor.timeout") != null) {
			String timeout = ConfigHandler.getHanlder().get("raptor.timeout");
			if (!timeout.equals("")) {
				try {
					Integer to = new Integer(timeout);
					cx.timeout_seconds = to.intValue();
				} catch (Exception e) {
					
				}
			}
		}
		
		return cx;
	}

	@Override
	public boolean hasFeature(Context cx, int featureIndex) {
		switch (featureIndex) {
		case Context.FEATURE_NON_ECMA_GET_YEAR:
			return true;

		case Context.FEATURE_MEMBER_EXPR_AS_FUNCTION_NAME:
			return true;

		case Context.FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER:
			return true;

		case Context.FEATURE_PARENT_PROTO_PROPERTIES:
			return false;
		}
		return super.hasFeature(cx, featureIndex);
	}

	@Override
	protected void observeInstructionCount(Context cx, int instructionCount) {
		MyContext mcx = (MyContext) cx;
		long currentTime = System.currentTimeMillis();
		if (currentTime - mcx.startTime > mcx.timeout_seconds * 1000) {
			throw new Error();
		}
	}

	@Override
	protected Object doTopCall(Callable callable, Context cx, Scriptable scope,
			Scriptable thisObj, Object[] args) {
		MyContext mcx = (MyContext) cx;
		mcx.startTime = System.currentTimeMillis();

		return super.doTopCall(callable, cx, scope, thisObj, args);
	}
}
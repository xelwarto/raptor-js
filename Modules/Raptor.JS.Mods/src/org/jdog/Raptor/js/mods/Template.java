package org.jdog.Raptor.js.mods;

import java.text.MessageFormat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class Template extends Module {

	private static final long serialVersionUID = 1L;
	private String message = null;

	public Template() {
	}

	public static Scriptable jsConstructor(Context cx, Object[] args,
			Function ctorObj, boolean inNewExpr) {
		if (args != null && args.length <= 0 && args[0] != null) {
			throw Context
					.reportRuntimeError("Template Exception: invalid number of arguments");
		}
		if (args[0] instanceof String) {
			Template tmpl = new Template();
			tmpl.message = (String) args[0];
			return tmpl;
		} else {
			throw Context
					.reportRuntimeError("Template Exception: invalid argument type");
		}
	}

	@Override
	public String getClassName() {
		return "Template";
	}

	public static String jsFunction_getMessage(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		Template thisTemplate = checkInstance(thisObj);

		if (args != null && args.length <= 0 && args[0] != null) {
			throw Context
					.reportRuntimeError("Template Exception: invalid number of arguments");
		}
		if (args[0] instanceof NativeArray) {
			NativeArray obj = (NativeArray) args[0];
			Long objLength = new Long(obj.getLength());
			Object[] msgObj = new Object[objLength.intValue()];
			for (int i = 0; i < obj.getLength(); i++) {
				msgObj[i] = obj.get(i, obj);
			}
			return MessageFormat.format(thisTemplate.message, msgObj);
		} else {
			throw Context
					.reportRuntimeError("Template Exception: invalid argument type");
		}
	}

	private static Template checkInstance(Scriptable obj) {
		if (obj == null || !(obj instanceof Template)) {
			throw Context.reportRuntimeError("called on incompatible object");
		}
		return (Template) obj;
	}
}
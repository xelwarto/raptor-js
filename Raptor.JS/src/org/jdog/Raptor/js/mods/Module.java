package org.jdog.Raptor.js.mods;

import org.mozilla.javascript.ScriptableObject;

public class Module extends ScriptableObject {

	private static final long serialVersionUID = 1L;

	@Override
	public String getClassName() {
		return "Module";
	}
}

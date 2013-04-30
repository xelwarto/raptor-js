package org.jdog.Raptor.js.mods.util;

import javax.naming.directory.DirContext;

public class LdapUtil {
	public final static int ADD_ATTRIBUTE = DirContext.ADD_ATTRIBUTE;
	public final static int REPLACE_ATTRIBUTE = DirContext.REPLACE_ATTRIBUTE;
	public final static int REMOVE_ATTRIBUTE = DirContext.REMOVE_ATTRIBUTE;

	public static String escapeDN(String dn) {
		if (dn != null) {
			dn = dn.replaceAll("\\\\", "\\\\\\\\");
			dn = dn.replaceAll("\\/", "\\\\/");
			dn = dn.replaceAll("\\;", "\\\\;");
			dn = dn.replaceAll("\\+", "\\\\+");
			dn = dn.replaceAll("\\>", "\\\\>");
			dn = dn.replaceAll("\\<", "\\\\<");
			dn = dn.replaceAll("\\\"", "\\\\\"");
			return dn;
		}
		return null;
	}
}
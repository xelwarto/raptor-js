/*
 * Raptor.JS Module - Logger
 */
package org.jdog.Raptor.js.mods;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * 
 * @author Ted Elwartowski
 */
public class Logger extends Module {

	private static final long serialVersionUID = 1L;
	private String fileName = null;
	private BufferedWriter writer = null;
	private boolean doAppend = true;
	private boolean isQuiet = false;
	private boolean isDebug = false;
	private String dateForm = "MM/dd/yyyy HH:mm:ss";
	private Vector<String> logList = new Vector<String>();

	public Logger() {
	}

	public static Scriptable jsConstructor(Context cx, Object[] args,
			Function ctorObj, boolean inNewExpr) throws Exception {
		if (args != null) {
			if (args.length > 1) {
				throw Context
						.reportRuntimeError("Logger Exception: invalid number of arguments");
			}
		}

		Logger log = new Logger();

		if (args != null && args.length > 0) {
			if (args[0] != null) {
				if (args[0] instanceof String) {
					log.fileName = (String) args[0];
				} else if (args[0] instanceof NativeJavaObject) {
					NativeJavaObject obj = (NativeJavaObject) args[0];
					Object obj2 = Context.toString(obj.unwrap());

					if (obj2 instanceof String) {
						log.fileName = (String) obj2;
					} else {
						throw Context
								.reportRuntimeError("Logger Exception: invalid argument type");
					}
				} else {
					throw Context
							.reportRuntimeError("Logger Exception: invalid argument type");
				}

				if (log.fileName == null) {
					throw Context
							.reportRuntimeError("Logger Exception: invalid argument");
				}

				File file = new File(log.fileName);
				if (!file.exists()) {
					file.createNewFile();
				}

				if (file.canWrite()) {
					log.writer = new BufferedWriter(new java.io.FileWriter(
							file, log.doAppend));
				}
			}
		}
		return log;
	}

	public static void jsFunction_close(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);
		if (thisLogger.writer != null) {
			thisLogger.writer.close();
		}
	}

	public static Object jsFunction_getLogs(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);
		Object[] resObj = new Object[0];
		if (thisLogger.logList != null && thisLogger.logList.size() > 0) {
			resObj = new Object[thisLogger.logList.size()];
			for (int i = 0; i < thisLogger.logList.size(); i++) {
				String name = (String) thisLogger.logList.get(i);
				resObj[i] = name;
			}
		}
		Scriptable scope = ScriptableObject.getTopLevelScope(thisLogger);
		return cx.newObject(scope, "Array", resObj);
	}

	public static void jsFunction_write(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);

		if (args == null || args.length < 1) {
			throw Context
					.reportRuntimeError("Logger Exception: invalid number of arguments");
		}
		if (args[0] != null && args[0] instanceof String) {
			String entry = getlogEntry((String) args[0], thisLogger.dateForm);
			thisLogger.logList.add(entry);
			if (thisLogger.writer != null) {
				thisLogger.writer.write(entry);
				thisLogger.writer.newLine();
			}

			if (!thisLogger.isQuiet) {
				System.out.println(entry);
			}
		}
	}

	public static void jsFunction_debug(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);

		if (thisLogger.isDebug) {
			if (args == null || args.length < 1) {
				throw Context
						.reportRuntimeError("Logger Exception: invalid number of arguments");
			}
			if (args[0] != null && args[0] instanceof String) {
				String entry = getlogEntry("(DEBUG) " + (String) args[0],
						thisLogger.dateForm);
				thisLogger.logList.add(entry);
				if (thisLogger.writer != null) {
					thisLogger.writer.write(entry);
					thisLogger.writer.newLine();
				}

				if (!thisLogger.isQuiet) {
					System.out.println(entry);
				}
			}
		}
	}

	public static void jsFunction_setQuiet(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);
		if (args == null || args.length < 1) {
			throw Context
					.reportRuntimeError("Logger Exception: invalid number of arguments");
		}
		if (args[0] != null && args[0] instanceof Boolean) {
			Boolean quiet = (Boolean) args[0];
			thisLogger.isQuiet = quiet;
		}
	}

	public static void jsFunction_setDebug(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);
		if (args == null || args.length < 1) {
			throw Context
					.reportRuntimeError("Logger Exception: invalid number of arguments");
		}
		if (args[0] != null && args[0] instanceof Boolean) {
			Boolean debug = (Boolean) args[0];
			thisLogger.isDebug = debug;
		}
	}

	public static void jsFunction_setDateFormat(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		Logger thisLogger = checkInstance(thisObj);
		if (args == null || args.length < 1) {
			throw Context
					.reportRuntimeError("Logger Exception: invalid number of arguments");
		}
		if (args[0] != null && args[0] instanceof String) {
			thisLogger.dateForm = (String) args[0];
		}
	}

	@Override
	public String getClassName() {
		return "Logger";
	}

	private static Logger checkInstance(Scriptable obj) {
		if (obj == null || !(obj instanceof Logger)) {
			throw Context.reportRuntimeError("called on incompatible object");
		}
		return (Logger) obj;
	}

	private static String getlogEntry(String entry, String dateForm) {
		String newEntry = "";
		DateFormat dateFormat = new SimpleDateFormat(dateForm);
		Date date = new Date();
		newEntry = newEntry.concat("[" + dateFormat.format(date) + "] ");
		newEntry = newEntry.concat(entry);
		return newEntry;
	}
}
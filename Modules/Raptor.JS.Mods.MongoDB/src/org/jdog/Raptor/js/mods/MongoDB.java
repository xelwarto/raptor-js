/*
 * Raptor.JS Module - MongoDB
 */
package org.jdog.Raptor.js.mods;

import org.jdog.Raptor.js.mods.mongo.mDB;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import com.mongodb.MongoClient;

/**
 * 
 * @author Ted Elwartowski
 */
public class MongoDB extends Module {

	private static final long serialVersionUID = 1L;
	private MongoClient mongoClient = null;

	public MongoDB() {
	}

	public static Scriptable jsConstructor(Context cx, Object[] args,
			Function ctorObj, boolean inNewExpr) {
		if (args != null && args.length <= 0) {
			throw Context
					.reportRuntimeError("MongoDB Exception: invalid number of arguments");
		}

		MongoDB mongoDB = new MongoDB();

		if (args[0] != null) {
			if (args[0] instanceof String) {
				String host = (String) args[0];
				Integer port = null;

				if (args.length > 1) {
					if (args[1] != null) {
						if (args[1] instanceof String) {
							port = new Integer((String) args[1]);
						}
					}
				}

				try {
					mongoDB.createClient(host, port);
				} catch (Exception e) {
					throw Context.reportRuntimeError("MongoDB Exception: "
							+ e.toString());
				}
			}
		}
		return mongoDB;
	}

	public static mDB jsFunction_getDB(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		MongoDB thisMongoDB = checkInstance(thisObj);

		if (args != null) {
			if (args.length == 0 && args.length > 1) {
				throw Context
						.reportRuntimeError("MongoDB Exception: invalid number of arguments");
			}
		}

		if (args[0] != null && args[0] instanceof String) {
			String dbName = (String) args[0];

			if (thisMongoDB.mongoClient != null) {
				mDB mdb = new mDB(thisMongoDB.mongoClient.getDB(dbName));
				return mdb;
			} else {
				throw Context
						.reportRuntimeError("MongoDB Exception: null pointer exception (mongoClient)");
			}
		} else {
			throw Context
					.reportRuntimeError("MongoDB Exception: invalid argument type");
		}
	}

	public static void jsFunction_close(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		MongoDB thisMongoDB = checkInstance(thisObj);

		if (thisMongoDB.mongoClient != null) {
			try {
				thisMongoDB.mongoClient.close();
			} catch (Exception e) {
				throw Context.reportRuntimeError("MongoDB Exception: "
						+ e.toString());
			}
		} else {
			throw Context
					.reportRuntimeError("MongoDB Exception: null pointer exception (mongoClient)");
		}
	}

	@Override
	public String getClassName() {
		return "MongoDB";
	}

	private static MongoDB checkInstance(Scriptable obj) {
		if (obj == null || !(obj instanceof MongoDB)) {
			throw Context.reportRuntimeError("called on incompatible object");
		}
		return (MongoDB) obj;
	}

	private void createClient(String host, Integer port) throws Exception {
		if (host != null) {
			if (port != null) {
				mongoClient = new MongoClient(host, port);
			} else {
				mongoClient = new MongoClient(host);
			}
		}
	}
}

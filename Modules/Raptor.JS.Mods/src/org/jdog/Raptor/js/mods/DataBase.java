/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdog.Raptor.js.mods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * 
 * @author Ted Elwartowski
 */
public class DataBase extends Module {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private Vector<String> labelList = new Vector<String>();
	private static String userId = null;
	private static String className = null;
	private static String jdbcUrl = null;
	private static String dataSource = null;

	public DataBase() {
	}

	public static Scriptable jsConstructor(Context cx, Object[] args,
			Function ctorObj, boolean inNewExpr) throws Exception {

		DataBase db = new DataBase();
		String password = _getArgs(args);

		if (dataSource != null) {
			javax.naming.Context initContext = new InitialContext();
			javax.naming.Context envContext = (javax.naming.Context) initContext
					.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup(dataSource);
			db.conn = ds.getConnection();
		} else {
			if (jdbcUrl != null && className != null) {
				Properties dbProperty = new Properties();
				if (userId != null) {
					dbProperty.put("user", userId);
				}
				if (password != null) {
					dbProperty.put("password", password);
				}
				Class.forName(className).newInstance();
				db.conn = DriverManager.getConnection(jdbcUrl, dbProperty);
			} else {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid or missing arguments");
			}
		}
		return db;
	}

	private static String _getArgs(Object[] args) {
		String password = null;
		if (args != null) {
			if (args.length == 1) {
				if (args[0] != null && args[0] instanceof NativeObject) {

					NativeObject obj = (NativeObject) args[0];

					if (obj.get("user", obj) != null
							&& obj.get("user", obj) instanceof String) {
						userId = (String) obj.get("user", obj);
					}

					if (obj.get("password", obj) != null
							&& obj.get("password", obj) instanceof String) {
						password = (String) obj.get("password", obj);
					}

					if (obj.get("driver", obj) != null
							&& obj.get("driver", obj) instanceof String) {
						className = (String) obj.get("driver", obj);
					}

					if (obj.get("url", obj) != null
							&& obj.get("url", obj) instanceof String) {
						jdbcUrl = (String) obj.get("url", obj);
					}

					if (obj.get("datasource", obj) != null
							&& obj.get("datasource", obj) instanceof String) {
						dataSource = (String) obj.get("datasource", obj);
					}
				} else if (args[0] != null && args[0] instanceof String) {
					dataSource = (String) args[0];
				} else {
					throw Context
							.reportRuntimeError("DataBase Exception: invalid argument type");
				}
			} else if (args.length == 4) {
				if (args[0] != null && args[0] instanceof String) {
					className = (String) args[0];
				}

				if (args[1] != null && args[1] instanceof String) {
					jdbcUrl = (String) args[1];
				}

				if (args[2] != null && args[2] instanceof String) {
					userId = (String) args[2];
				}

				if (args[3] != null && args[3] instanceof String) {
					password = (String) args[3];
				}
			} else {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid number of arguments");
			}
		} else {
			throw Context
					.reportRuntimeError("DataBase Exception: invalid number of arguments");
		}
		return password;
	}

	@Override
	public String getClassName() {
		return "DataBase";
	}

	public static void jsFunction_close(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		DataBase thisDataBase = checkInstance(thisObj);
		if (thisDataBase.conn != null) {
			thisDataBase.conn.close();
		}
	}

	public static Object jsFunction_runSelect(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		DataBase thisDataBase = checkInstance(thisObj);
		String sql = null;
		Object[] resObj = new Object[0];

		if (thisDataBase.conn != null) {
			if (args != null && args.length < 2) {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid number of arguments");
			}

			if (args[0] != null && args[0] instanceof String) {
				sql = (String) args[0];
			} else {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid argument type");
			}

			thisDataBase.labelList.clear();
			PreparedStatement pstm = thisDataBase.conn.prepareStatement(sql);
			if (args[1] != null) {
				if (args[1] instanceof NativeArray) {
					NativeArray array = (NativeArray) args[1];
					if (array.getLength() > 0) {
						for (int i = 0; i < array.getLength(); i++) {
							Object obj = array.get(i, array);

							if (obj == null) {
								pstm.setNull(i + 1, java.sql.Types.NULL);
							} else if (obj instanceof NativeJavaObject) {
								NativeJavaObject nObj = (NativeJavaObject) obj;
								pstm.setObject(i + 1, nObj.unwrap());
							} else {
								pstm.setObject(i + 1, obj);
							}
						}
					}
				} else {
					throw Context
							.reportRuntimeError("DataBase Exception: invalid argument type");
				}
			}

			ResultSet res = pstm.executeQuery();
			Vector<Object> results = new Vector<Object>();
			int cnt = 0;
			while (res.next()) {
				NativeObject obj = new NativeObject();
				ResultSetMetaData r = res.getMetaData();
				for (int i = 0; i < r.getColumnCount(); i++) {
					String name = r.getColumnLabel(i + 1);
					if (!thisDataBase.labelList.contains(name)) {
						thisDataBase.labelList.add(name);
					}
					obj.put(name, obj, res.getObject(name));
				}
				results.add(obj);
				cnt++;
			}
			resObj = new Object[results.size()];
			Iterator<Object> it = results.iterator();
			cnt = 0;
			while (it.hasNext()) {
				NativeObject obj = (NativeObject) it.next();
				resObj[cnt] = obj;
				cnt++;
			}
			res.close();
			pstm.close();
		}
		Scriptable scope = ScriptableObject.getTopLevelScope(thisDataBase);
		return cx.newObject(scope, "Array", resObj);
	}

	public static Object jsFunction_getLabels(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		DataBase thisDataBase = checkInstance(thisObj);
		Object[] resObj = new Object[0];
		if (thisDataBase.labelList != null && thisDataBase.labelList.size() > 0) {
			resObj = new Object[thisDataBase.labelList.size()];
			for (int i = 0; i < thisDataBase.labelList.size(); i++) {
				String name = (String) thisDataBase.labelList.get(i);
				resObj[i] = name;
			}
		}
		Scriptable scope = ScriptableObject.getTopLevelScope(thisDataBase);
		return cx.newObject(scope, "Array", resObj);
	}

	public static void jsFunction_runUpdate(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		executeStm(cx, thisObj, args, funObj);
	}

	public static void jsFunction_runInsert(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		executeStm(cx, thisObj, args, funObj);
	}

	private static void executeStm(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) throws Exception {
		DataBase thisDataBase = checkInstance(thisObj);
		String sql = null;

		if (thisDataBase.conn != null) {
			if (args != null && args.length < 2) {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid number of arguments");
			}

			if (args[0] != null && args[0] instanceof String) {
				sql = (String) args[0];
			} else {
				throw Context
						.reportRuntimeError("DataBase Exception: invalid argument type");
			}

			PreparedStatement pstm = thisDataBase.conn.prepareStatement(sql);
			if (args[1] != null) {
				if (args[1] instanceof NativeArray) {
					NativeArray array = (NativeArray) args[1];
					if (array.getLength() > 0) {
						for (int i = 0; i < array.getLength(); i++) {
							Object obj = array.get(i, array);

							if (obj == null) {
								pstm.setNull(i + 1, java.sql.Types.NULL);
							} else if (obj instanceof NativeJavaObject) {
								NativeJavaObject nObj = (NativeJavaObject) obj;
								pstm.setObject(i + 1, nObj.unwrap());
							} else {
								pstm.setObject(i + 1, obj);
							}
						}
					}
				} else {
					throw Context
							.reportRuntimeError("DataBase Exception: invalid argument type");
				}
			}

			pstm.execute();
			pstm.close();
		}
	}

	private static DataBase checkInstance(Scriptable obj) {
		if (obj == null || !(obj instanceof DataBase)) {
			throw Context.reportRuntimeError("called on incompatible object");
		}
		return (DataBase) obj;
	}
}

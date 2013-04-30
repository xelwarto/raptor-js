package org.jdog.Raptor.js.mods.mongo;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class mCollection {
	private DBCollection dbCollection = null;

	public mCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}

	public String findOne() {
		return dbCollection.findOne().toString();
	}

	public Long getCount() throws Exception {
		if (dbCollection != null) {
			return new Long(dbCollection.getCount());
		} else {
			throw new Exception(
					"MongoDB(mCollection) Exception: null pointer exception (dbCollection)");
		}
	}

	public void insert(Object data) throws Exception {
		List<DBObject> dbObjs = new ArrayList<DBObject>();

		if (data != null) {
			if (data instanceof NativeObject) {
				NativeObject obj = (NativeObject) data;
				Object[] objIds = obj.getIds();
				
				DBObject dbObj_ent = new BasicDBObject();
				for (int i = 0; i < objIds.length; i++) {
					Object objId = objIds[i];
					if (objId instanceof String) {
						String id = (String) objId;
						
						Object value = obj.get(id, obj);
						DBObject dbObj = new BasicDBObject();
						
						
					}
				}
			} else if (data instanceof NativeArray) {

			} else {
				throw new Exception(
						"MongoDB(mCollection) Exception: invalid argument type");
			}
		} else {
			throw new Exception(
					"MongoDB(mCollection) Exception: invalid argument type");
		}

		if (dbObjs != null && dbObjs.size() > 0) {
			if (dbCollection != null) {
				dbCollection.insert(dbObjs);
			} else {
				throw new Exception(
						"MongoDB(mCollection) Exception: null pointer exception (dbCollection)");
			}
		}
	}
}

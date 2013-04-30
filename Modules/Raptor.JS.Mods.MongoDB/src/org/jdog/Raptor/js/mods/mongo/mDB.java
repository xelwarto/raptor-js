package org.jdog.Raptor.js.mods.mongo;

import java.util.Set;

import com.mongodb.DB;

public class mDB {

	private DB mongoDB = null;

	public mDB(DB mongoDB) {
		this.mongoDB = mongoDB;
	}

	public mCollection getCollection(String name) throws Exception {
		if (name != null) {
			if (mongoDB != null) {
				mCollection mCol = new mCollection(mongoDB.getCollection(name));
				return mCol;
			} else {
				throw new Exception(
						"MongoDB(mDB) Exception: null pointer exception (mongoDB)");
			}
		} else {
			throw new Exception("MongoDB(mDB) Exception: invalid argument type");
		}
	}

	public Object[] getCollections() throws Exception {
		Object[] retObj = new Object[0];
		if (mongoDB != null) {
			Set<String> collections = mongoDB.getCollectionNames();

			if (collections.size() > 0) {
				retObj = new Object[collections.size()];
				int colCount = 0;
				for (String col : collections) {
					retObj[colCount] = col;
					colCount++;
				}
			}
		} else {
			throw new Exception(
					"MongoDB(mDB) Exception: null pointer exception (mongoDB)");
		}

		return retObj;
	}
}

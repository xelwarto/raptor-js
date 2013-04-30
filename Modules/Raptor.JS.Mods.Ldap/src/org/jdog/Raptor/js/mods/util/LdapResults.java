/*
 */
package org.jdog.Raptor.js.mods.util;

import java.util.List;
import java.util.ArrayList;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchResult;

/**
 * 
 * @author Ted Elwartowski
 */
public class LdapResults {

	private NamingEnumeration<SearchResult> results = null;
	private List<LdapEntity> resList = new ArrayList<LdapEntity>();
	private int resCnt = 0;

	public LdapResults(NamingEnumeration<SearchResult> results)
			throws Exception {
		this.results = results;

		if (this.results != null) {
			while (this.results.hasMore()) {
				SearchResult sRes = (SearchResult) this.results.next();
				if (sRes != null) {
					LdapEntity ent = new LdapEntity(sRes);
					this.resList.add(ent);
				}
			}
		} else {
			throw org.mozilla.javascript.Context
					.reportRuntimeError("LdapResults Exception: null pointer exception (results)");
		}
	}

	public int size() {
		return resList.size();
	}

	public boolean hasMore() {
		if (resList != null && resList.size() > 0) {
			if (resCnt < resList.size()) {
				return true;
			}
		}
		return false;
	}

	public LdapEntity next() {
		if (resList != null) {
			LdapEntity ent = resList.get(resCnt);
			if (ent != null) {
				resCnt++;
				return ent;
			} else {
				throw org.mozilla.javascript.Context
						.reportRuntimeError("LdapResults Exception: null pointer exception (LdapEntity)");
			}
		} else {
			throw org.mozilla.javascript.Context
					.reportRuntimeError("LdapResults Exception: null pointer exception (resList)");
		}
	}

	public void reset() {
		resCnt = 0;
	}

	public List<LdapEntity> getAll() {
		if (resList != null) {
			return resList;
		} else {
			throw org.mozilla.javascript.Context
					.reportRuntimeError("LdapResults Exception: null pointer exception (resList)");
		}
	}
}
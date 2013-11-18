/*
 * Raptor.JS Module - Ldap
 */
package org.jdog.Raptor.js.mods;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;

import org.jdog.Raptor.js.mods.util.LdapEntity;
import org.jdog.Raptor.js.mods.util.LdapResults;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;

import java.util.Hashtable;

/**
 *
 * @author Ted Elwartowski
 */
public class Ldap extends Module {

	private static final long serialVersionUID = 1L;
	
	private String ldapHost = null;
    private String ldapPort = "389";
    private String ldapServer = null;
    private Hashtable<String, String> env = new Hashtable<String, String>();
    private DirContext ctx = null;
    private final String ldapProto = "ldap://";
    private final String ldapBindFactory = "com.sun.jndi.ldap.LdapCtxFactory";

    public Ldap() {
    }

    public static Scriptable jsConstructor(org.mozilla.javascript.Context cx, Object[] args,
            Function ctorObj, boolean inNewExpr) {
        if (args != null && args.length < 1) {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
        }

        if (args[0] != null && args[0] instanceof String) {
            Ldap ldap = new Ldap();
            ldap.ldapHost = (String) args[0];

            if (args.length > 1 && args[1] != null) {
                if (args[1] instanceof String) {
                    ldap.ldapPort = (String) args[1];
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                }
            }

            if (ldap.ldapProto == null) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: null pointer exception (ldapProto)");
            }
            if (ldap.ldapHost == null) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: null pointer exception (ldapHost)");
            }
            if (ldap.ldapPort == null) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: null pointer exception (ldapPort)");
            }
            ldap.ldapServer = ldap.ldapProto + ldap.ldapHost + ":" + ldap.ldapPort;
            return ldap;
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
        }
    }

    public static void jsFunction_bind(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);

        if (thisLdap.ldapServer == null) {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: null pointer exception (ldapServer)");
        }

	//FIX HERE
        thisLdap.env.put(javax.naming.Context.REFERRAL, "follow");
        thisLdap.env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, thisLdap.ldapBindFactory);
        thisLdap.env.put(javax.naming.Context.PROVIDER_URL, thisLdap.ldapServer);

        if (args != null) {
            if (args.length == 2) {
                if (args[0] != null && args[1] != null) {
                    if (args[0] instanceof String) {
                        thisLdap.env.put(javax.naming.Context.SECURITY_PRINCIPAL, (String) args[0]);
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                    }

                    if (args[1] instanceof String) {
                        thisLdap.env.put(javax.naming.Context.SECURITY_CREDENTIALS, (String) args[1]);
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                    }
                }
            }
        }

        thisLdap.ctx = new InitialDirContext(thisLdap.env);
    }

    public static LdapResults jsFunction_search(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);

        if (thisLdap.ctx != null) {
            if (args != null && args.length < 1) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
            }

            String base = null;
            String filter = null;
            String scope = "subtree";
            String[] attrs = null;

            if (args[0] != null && args[0] instanceof NativeObject) {
                NativeObject obj = (NativeObject) args[0];
                Object objBase = obj.get("base", obj);
                Object objFilter = obj.get("filter", obj);
                Object objScope = obj.get("scope", obj);
                Object objAttrs = obj.get("attrs", obj);

                if (objBase != null && objBase instanceof String) {
                    base = (String) objBase;
                    if (base.equals("")) {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: missing argument (base)");
                    }
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: missing argument (base)");
                }

                if (objFilter != null && objFilter instanceof String) {
                    filter = (String) objFilter;
                    if (filter.equals("")) {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: missing argument (filter)");
                    }
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: missing argument (filter)");
                }

                if (objScope != null && objScope instanceof String) {
                    scope = (String) objScope;
                }

                if (objAttrs != null && objAttrs instanceof NativeArray) {
                    NativeArray arry = (NativeArray) objAttrs;
                    Long aSize = new Long(arry.getLength());

                    attrs = new String[aSize.intValue()];
                    for (int i = 0; i < aSize.intValue(); i++) {
                        if (arry.get(i, arry) != null && arry.get(i, arry) instanceof String) {
                            attrs[i] = (String) arry.get(i, arry);
                        }
                    }
                }

                SearchControls searchCtls = new SearchControls();

                if (scope != null) {
                    if (scope.equalsIgnoreCase("subtree")) {
                        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                    }
                    if (scope.equalsIgnoreCase("object")) {
                        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
                    }
                    if (scope.equalsIgnoreCase("onelevel")) {
                        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
                    }
                }

                if (attrs != null && attrs.length > 0) {
                    searchCtls.setReturningAttributes(attrs);
                }

                LdapResults results = new LdapResults((NamingEnumeration<SearchResult>) thisLdap.ctx.search(base, filter, searchCtls));
                return results;
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
            }

        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap connection is null");
        }
    }

    public static void jsFunction_close(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);
        if (thisLdap.ctx != null) {
            thisLdap.ctx.close();
            thisLdap.ctx = null;
        }
    }

    public static void jsFunction_add(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);
        if (thisLdap.ctx != null) {
            if (args != null && args.length < 1) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
            }

            if (args[0] != null) {
                LdapEntity ent = null;
                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object obj2 = obj.unwrap();
                    if (obj2 instanceof LdapEntity) {
                        ent = (LdapEntity) obj2;
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                    }
                } else if (args[0] instanceof LdapEntity) {
                    ent = (LdapEntity) args[0];
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                }

                if (ent != null) {
                    String dn = ent.getDN();
                    if (dn != null && !dn.equals("")) {
                        if (ent.getAttrHandler() != null) {
                            thisLdap.ctx.createSubcontext(dn, ent.getAttrHandler().getAttributes());
                        } else {
                            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap Entity attribute handler is null");
                        }
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap Entity DN is null or missing");
                    }
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap Entity is null");
                }
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap connection is null");
        }
    }

    public static void jsFunction_remove(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);
        if (thisLdap.ctx != null) {
            if (args != null && args.length < 1) {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
            }

            if (args[0] != null) {
                LdapEntity ent = null;
                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object obj2 = obj.unwrap();
                    if (obj2 instanceof LdapEntity) {
                        ent = (LdapEntity) obj2;
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                    }
                } else if (args[0] instanceof LdapEntity) {
                    ent = (LdapEntity) args[0];
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
                }

                if (ent != null) {
                    String dn = ent.getDN();
                    if (dn != null && !dn.equals("")) {
                        thisLdap.ctx.destroySubcontext(dn);
                    } else {
                        throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap Entity DN is null or missing");
                    }
                } else {
                    throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap Entity is null");
                }
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap connection is null");
        }
    }
    
    public static void jsFunction_rename(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);
        
        if (args != null && args.length != 2) {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
        }
        
        if (args[0] != null && args[1] != null) {
        	if (args[0] instanceof String && args[1] instanceof String) {
	            if (thisLdap.ctx != null) {
	            	thisLdap.ctx.rename((String) args[0], (String) args[1]);
	            } else {
	            	throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap connection is null");
	            }
        	} else {
        		throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
        	}
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
        }
    }

    public static DirContext jsFunction_getContext(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        Ldap thisLdap = checkInstance(thisObj);
        if (thisLdap.ctx != null) {
        	return thisLdap.ctx;
        } else {
        	throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: Ldap connection is null");
        }
    }
    
    public static Object[] jsFunction_getRdn(org.mozilla.javascript.Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
    	if (args != null && args.length < 1) {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid number of arguments");
        }
        
        if (args[0] != null && args[0] instanceof String) {
        	Object[] obj = null;
        	LdapName dn = new LdapName((String) args[0]);
        	
        	if (dn != null && dn.size() > 0) {
        		obj = new Object[dn.size()];
                for (int i = 0; i < dn.size(); i++) {
                    obj[i] = dn.get(i);
                }
                return obj;
        	} else {
        		throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid DN type");
        	}
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("Ldap Exception: invalid argument type");
        }
    }

    @Override
    public String getClassName() {
        return "Ldap";
    }

    private static Ldap checkInstance(Scriptable obj) {
        if (obj == null || !(obj instanceof Ldap)) {
            throw org.mozilla.javascript.Context.reportRuntimeError("called on incompatible object");
        }
        return (Ldap) obj;
    }
}
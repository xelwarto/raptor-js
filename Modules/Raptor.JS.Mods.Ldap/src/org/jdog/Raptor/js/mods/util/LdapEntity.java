/*
 */
package org.jdog.Raptor.js.mods.util;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;

/**
 *
 * @author Ted Elwartowski
 */
public class LdapEntity {

    private HashMap<String, List<Object>> attrList = new HashMap<String, List<Object>>();
    private LdapAttributeHandler attrHandler = new LdapAttributeHandler();
    private List<String> attributes = new ArrayList<String>();
    private String dn = null;

    public LdapEntity() {
    }

    public LdapEntity(SearchResult result) throws Exception {
        if (result != null) {
            dn = (String) result.getNameInNamespace();
            if (dn != null) {
                Attributes userAttributes = result.getAttributes();
                NamingEnumeration<String> userAttributeIds = userAttributes.getIDs();
                while (userAttributeIds.hasMore()) {
                    String id = (String) userAttributeIds.next();
                    _addAttribute(id, _getAttributeValues(result, id));
                }
                attrHandler.clear();
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (dn)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (result)");
        }
    }

    public Object getValue(String name) {
        Object obj = null;
        if (name != null && !name.equals("")) {
            if (attrList != null) {
                if (_getAttribute((String) name) != null) {
                    if (_getAttribute((String) name).size() > 0) {
                        obj = _getAttribute((String) name).get(0);
                    }
                }
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (attrs)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
        return obj;
    }

    public Object[] getValues(String name) {
        Object[] obj = null;
        if (name != null && !name.equals("")) {
            if (attrList != null) {
                if (_getAttribute((String) name) != null) {
                    if (_getAttribute((String) name).size() > 0) {
                        obj = new Object[_getAttribute((String) name).size()];
                        for (int i = 0; i < _getAttribute((String) name).size(); i++) {
                            obj[i] = _getAttribute((String) name).get(i);
                        }
                    }
                }
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (attrs)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
        return obj;
    }

    public void addAttribute(String name, Object value) {
        if (name != null && !name.equals("")) {
            if (value != null) {
                List<Object> objValues = new ArrayList<Object>();
                if (value instanceof NativeArray) {
                    NativeArray array = (NativeArray) value;
                    if (array.getLength() > 0) {
                        for (int i = 0; i < array.getLength(); i++) {
                            Object obj = array.get(i, array);
                            if (obj instanceof NativeJavaObject) {
                                NativeJavaObject obj2 = (NativeJavaObject) obj;
                                objValues.add(obj2.unwrap());
                            } else {
                                objValues.add(obj);
                            }
                        }
                    }
                } else if (value instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) value;
                    objValues.add(obj.unwrap());
                } else {
                    objValues.add(value);
                }
                _addAttribute(name, objValues);
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (value)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
    }

    public void removeAttribute(String name, Object value) {
        if (name != null && !name.equals("")) {
            if (value != null) {
                List<Object> objValues = new ArrayList<Object>();
                if (value instanceof NativeArray) {
                    NativeArray array = (NativeArray) value;
                    if (array.getLength() > 0) {
                        for (int i = 0; i < array.getLength(); i++) {
                            Object obj = array.get(i, array);
                            if (obj instanceof NativeJavaObject) {
                                NativeJavaObject obj2 = (NativeJavaObject) obj;
                                objValues.add(obj2.unwrap());
                            } else {
                                objValues.add(obj);
                            }
                        }
                    }
                } else if (value instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) value;
                    objValues.add(obj.unwrap());
                } else {
                    objValues.add(value);
                }
                _removeAttribute(name, objValues);
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (value)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }

    }

    public void removeAttribute(String name) {
        if (name != null && !name.equals("")) {
            _removeAttribute(name, null);
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
    }

    public void replaceAttribute(String name, Object value) {
        if (name != null && !name.equals("")) {
        	name = name.toLowerCase();
            if (value != null) {
                List<Object> objValues = new ArrayList<Object>();
                if (value instanceof NativeArray) {
                    NativeArray array = (NativeArray) value;
                    if (array.getLength() > 0) {
                        for (int i = 0; i < array.getLength(); i++) {
                            Object obj = array.get(i, array);
                            if (obj instanceof NativeJavaObject) {
                                NativeJavaObject obj2 = (NativeJavaObject) obj;
                                objValues.add(obj2.unwrap());
                            } else {
                                objValues.add(obj);
                            }
                        }
                    }
                } else if (value instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) value;
                    objValues.add(obj.unwrap());
                } else {
                    objValues.add(value);
                }

                _replaceAttribute(name, objValues);
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (value)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }

    }

    public Object[] getAttributes() {
        Object[] objs = null;
        if (attributes != null && attributes.size() > 0) {
            objs = new Object[attributes.size()];
            for (int i = 0; i <
                    attributes.size(); i++) {
                objs[i] = attributes.get(i);
            }

        }
        return objs;
    }

    public LdapAttributeHandler getAttrHandler() {
        return attrHandler;
    }

    public String getDN() {
        return dn;
    }

    public void setDN(String dn) {
        this.dn = dn;
    }

    public void doUpdate(DirContext ctx) {
        try {
            _modifyAttribute(ctx, this.getDN(), attrHandler.getModList());
            attrHandler.clear();
        } catch (Exception e) {
            throw org.mozilla.javascript.Context.reportRuntimeError(e.toString());
        }

    }

    private List<Object> _getAttributeValues(SearchResult result, String attrName) throws Exception {
        List<Object> list = new ArrayList<Object>();
        Attribute attr = (Attribute) result.getAttributes().get(attrName);
        if (attr != null) {
            NamingEnumeration<?> allAttrs = (NamingEnumeration<?>) attr.getAll();
            while (allAttrs.hasMore()) {
                Object obj = (Object) allAttrs.next();
                list.add(obj);
            }
        }
        return list;
    }

    private List<Object> _getAttribute(String name) {
        if (attrList != null) {
            if (name != null && !name.equals("")) {
                name = name.toLowerCase();
                if (attrList.get(name) != null) {
                    return attrList.get(name);
                }
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (attrList)");
        }
        return null;
    }

    private void _removeAttribute(String name, List<Object> objValues) {
        if (name != null && !name.equals("")) {
        	name = name.toLowerCase();
        	
        	if (_getAttribute(name) != null) {
	            if (objValues != null && objValues.size() > 0) {
                    attrHandler.removeAttribute(name, objValues);
                    List<Object> values = new ArrayList<Object>();
                    Iterator<Object> it = _getAttribute(name).iterator();
                    while (it.hasNext()) {
                        Object attrValue = (Object) it.next();
                        if (attrValue != null) {
                            if (!objValues.contains(attrValue)) {
                                values.add(attrValue);
                            }
                        }
                    }
                    if (values != null && values.size() > 0) {
                        attrList.put(name, values);
                    } else {
                        attrList.remove(name);
                        attributes.remove(name);
                    }
	            } else {
                    attrHandler.removeAttribute(name);
                    attrList.remove(name);
                    attributes.remove(name);
                }
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
    }
    
    private void _replaceAttribute(String name, List<Object> objValues) {
        if (name != null && !name.equals("")) {
        	name = name.toLowerCase();
        	
        	if (_getAttribute(name) != null) {
	            if (objValues != null && objValues.size() > 0) {
	                	attrHandler.replaceAttribute(name, objValues);
	                    attrList.put(name, objValues);
	            } else {
	            	throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (objValues)");
	            }
        	}
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
    }

    private void _addAttribute(String name, List<Object> objValues) {
        if (name != null && !name.equals("")) {
        	name = name.toLowerCase();
            if (objValues != null && objValues.size() > 0) {
                attrHandler.addAttribute(name, objValues);
                if (_getAttribute(name) != null) {
                    List<Object> values = (List<Object>) _getAttribute(name);
                    values.addAll(objValues);
                    attrList.put(name, values);
                } else {
                    attrList.put(name, objValues);
                }
                attributes.add(name);
            } else {
                throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (objValues)");
            }
        } else {
            throw org.mozilla.javascript.Context.reportRuntimeError("LdapEntity Exception: null pointer exception (name)");
        }
    }

    private void _modifyAttribute(DirContext ctx, String entryDn, ModificationItem[] modAttrs) throws Exception {
        if (ctx != null) {
            if (entryDn != null && !entryDn.equals("")) {
                if (modAttrs != null) {
                    entryDn = LdapUtil.escapeDN(entryDn);
                    ctx.modifyAttributes(entryDn, modAttrs);
                } else {
                    throw new Exception("LdapEntity Exception: null pointer exception (modAttrs)");
                }
            } else {
                throw new Exception("LdapEntity Exception: null pointer exception (entryDn)");
            }

        } else {
            throw new Exception("LdapEntity Exception: null pointer exception (ctx)");
        }
    }
}

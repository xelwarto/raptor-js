/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdog.Raptor.js.mods.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.directory.*;

/**
 *
 * @author Ted Elwartowski
 */
public class LdapAttributeHandler {

    private BasicAttributes attributes = new BasicAttributes(true);
    private List<ModificationItem> modList = new ArrayList<ModificationItem>();

    public LdapAttributeHandler() {
    }

    public void addAttribute(String attrName, Object attrValue) {
        if (attrName != null) {
            _setAttribute(attrName, attrValue, LdapUtil.ADD_ATTRIBUTE);
        }
    }

    public void addAttribute(String attrName, List<Object> attrValues) {
        if (attrName != null) {
            _setAttribute(attrName, attrValues, LdapUtil.ADD_ATTRIBUTE);
        }
    }

    public void replaceAttribute(String attrName, Object attrValue) {
        if (attrName != null) {
            _setAttribute(attrName, attrValue, LdapUtil.REPLACE_ATTRIBUTE);
        }
    }

    public void replaceAttribute(String attrName, List<Object> attrValues) {
        if (attrName != null) {
            _setAttribute(attrName, attrValues, LdapUtil.REPLACE_ATTRIBUTE);
        }
    }

    public void removeAttribute(String attrName, Object attrValue) {
        if (attrName != null) {
            _setAttribute(attrName, attrValue, LdapUtil.REMOVE_ATTRIBUTE);
        }
    }

    public void removeAttribute(String attrName) {
        if (attrName != null) {
            _setAttribute(attrName, LdapUtil.REMOVE_ATTRIBUTE);
        }
    }

    public void removeAttribute(String attrName, List<Object> attrValues) {
        if (attrName != null) {
            _setAttribute(attrName, attrValues, LdapUtil.REMOVE_ATTRIBUTE);
        }
    }

    public ModificationItem[] getModList() {
        ModificationItem[] mods = new ModificationItem[modList.size()];
        for (int i = 0; i < modList.size(); i++) {
            mods[i] = modList.get(i);
        }
        return mods;
    }

    public BasicAttributes getAttributes() {
        return attributes;
    }

    public void clear() {
        attributes = new BasicAttributes(true);
        modList.clear();
    }

    private void _setAttribute(String attrName, Object attrValue, int modType) {
        if (attrName != null) {
            Attribute attribute = new BasicAttribute(attrName);
            if (attrValue != null && !attrValue.equals("")) {
                attribute.add(attrValue);
            }
            ModificationItem modItem = new ModificationItem(modType, attribute);
            modList.add(modItem);
            attributes.put(attribute);
        }
    }

    private void _setAttribute(String attrName, int modType) {
        if (attrName != null) {
            Attribute attribute = new BasicAttribute(attrName);
            
            ModificationItem modItem = new ModificationItem(modType, attribute);
            modList.add(modItem);
            attributes.put(attribute);
        }
    }

    private void _setAttribute(String attrName, List<Object> attrValues, int modType) {
        if (attrName != null) {
            Attribute attribute = new BasicAttribute(attrName);
            if (attrValues != null && attrValues.size() > 0) {
                Iterator<Object> it = attrValues.iterator();
                while (it.hasNext()) {
                    Object attrValue = it.next();
                    if (attrValue != null && !attrValue.equals("")) {
                        attribute.add(attrValue);
                    }
                }
                ModificationItem modItem = new ModificationItem(modType, attribute);
                modList.add(modItem);
                attributes.put(attribute);
            }
        }
    }
}
package org.jdog.Raptor.js.mods.util;

import java.util.Arrays;
import java.util.Comparator;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.NamingException;

public class MXHandler {

    private static String mailSystem = null;

    public static String findMX(String host) throws NamingException {
        InitialDirContext iDirC = new InitialDirContext();
        Attributes attributes = iDirC.getAttributes("dns:/" + host, new String[]{"MX"});
        Attribute attributeMX = attributes.get("MX");

        if (attributeMX != null) {
            String[][] pvhn = new String[attributeMX.size()][2];
            for (int i = 0; i < attributeMX.size(); i++) {
                pvhn[i] = ("" + attributeMX.get(i)).split("\\s+");
            }

            // sort the MX RRs by RR value (lower is preferred)
            Arrays.sort(pvhn, new Comparator<String[]>() {

                public int compare(String[] o1, String[] o2) {
                    return (Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]));
                }
            });

            if (pvhn.length > 0) {
                mailSystem = pvhn[0][1].endsWith(".") ? pvhn[0][1].substring(0, pvhn[0][1].length() - 1) : pvhn[0][1];
            }
        }

        return mailSystem;
    }
}


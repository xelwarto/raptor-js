/*
 * Raptor.JS Module - File
 */
package org.jdog.Raptor.js.mods;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author Ted Elwartowski
 */
public class File extends Module {

	private static final long serialVersionUID = 1L;
	private java.io.File fileObj = null;

    public File() {
    }

    public static Scriptable jsConstructor(Context cx, Object[] args,
            Function ctorObj, boolean inNewExpr) throws Exception {
        if (args == null || args.length < 1) {
            throw Context.reportRuntimeError("File Exception: invalid number of arguments");
        }

        if (args[0] != null) {
            File file = new File();

            String filePart1 = null;
            String filePart2 = null;
            if (args[0] instanceof String) {
                filePart1 = (String) args[0];
            } else if (args[0] instanceof NativeJavaObject) {
                NativeJavaObject obj = (NativeJavaObject) args[0];
                Object obj2 = Context.toString(obj.unwrap());
                if (obj2 instanceof String) {
                    filePart1 = (String) obj2;
                } else {
                    throw Context.reportRuntimeError("File Exception: invalid argument type");
                }
            } else {
                throw Context.reportRuntimeError("File Exception: invalid argument type");
            }

            if (args.length > 1 && args[1] != null) {
                if (args[1] instanceof String) {
                    filePart2 = (String) args[1];
                } else if (args[1] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[1];
                    Object obj2 = Context.toString(obj.unwrap());
                    if (obj2 instanceof String) {
                        filePart2 = (String) obj2;
                    } else {
                        throw Context.reportRuntimeError("File Exception: invalid argument type");
                    }
                } else {
                    throw Context.reportRuntimeError("File Exception: invalid argument type");
                }
            }

            if (filePart1 != null) {
                if (filePart2 != null) {
                    file.fileObj = new java.io.File(filePart1, filePart2);
                } else {
                    file.fileObj = new java.io.File(filePart1);
                }
            } else {
                throw Context.reportRuntimeError("File Exception: file part is null");
            }

            return file;
        } else {
            throw Context.reportRuntimeError("File Exception: invalid argument type");
        }
    }

    public static String jsFunction_path(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.getPath();
        }
        return "";
    }

    public static String jsFunction_name(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.getName();
        }
        return "";
    }

    public static String jsFunction_parent(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.getParent();
        }
        return "";
    }

    public static boolean jsFunction_exists(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.exists();
        }
        return false;
    }

    public static boolean jsFunction_canRead(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.canRead();
        }
        return false;
    }

    public static boolean jsFunction_canWrite(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.canWrite();
        }
        return false;
    }

    public static boolean jsFunction_canExecute(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.canExecute();
        }
        return false;
    }

    public static boolean jsFunction_isFile(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.isFile();
        }
        return false;
    }

    public static boolean jsFunction_isDirectory(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.isDirectory();
        }
        return false;
    }

    public static boolean jsFunction_createFile(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.createNewFile();
        }
        return false;
    }

    public static boolean jsFunction_createDir(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.mkdir();
        }
        return false;
    }

    public static boolean jsFunction_remove(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.delete();
        }
        return false;
    }

    public static long jsFunction_length(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            return thisFile.fileObj.length();
        }
        return 0;
    }

    public static Object jsFunction_list(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);

        Object[] resObj = new Object[0];
        if (thisFile.fileObj != null) {
            if (thisFile.fileObj.isDirectory()) {
                String[] files = thisFile.fileObj.list();
                resObj = files;
            }
        }
        Scriptable scope = ScriptableObject.getTopLevelScope(thisFile);
        return cx.newObject(scope, "Array", resObj);
    }

    public static void jsFunction_moveFile(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) throws Exception {
        File thisFile = checkInstance(thisObj);
        if (thisFile.fileObj != null) {
            if (args == null || args.length < 1) {
                throw Context.reportRuntimeError("File Exception: invalid number of arguments");
            }

            if (args[0] != null) {
                java.io.File dirTo = null;

                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object objValue = obj.unwrap();
                    if (objValue instanceof String) {
                        dirTo = new java.io.File((String) objValue);
                    } else {
                        throw Context.reportRuntimeError("File Exception: invalid argument type");
                    }
                } else if (args[0] instanceof String) {
                    dirTo = new java.io.File((String) args[0]);
                } else {
                    throw Context.reportRuntimeError("File Exception: invalid argument type");
                }

                if (dirTo != null) {
                    if (thisFile.fileObj.exists()) {
                        if (thisFile.fileObj.isFile() && thisFile.fileObj.canWrite()) {
                            if (dirTo.exists()) {
                                if (dirTo.isDirectory() && dirTo.canWrite()) {
                                    dirTo = new java.io.File(dirTo, thisFile.fileObj.getName());
                                    thisFile.fileObj.renameTo(dirTo);
                                    if (dirTo.exists()) {
                                        thisFile.fileObj = dirTo;
                                    }
                                } else {
                                    throw Context.reportRuntimeError("File Exception: destination is not a directory or access failed");
                                }
                            } else {
                                throw Context.reportRuntimeError("File Exception: destination directory does not exist");
                            }
                        } else {
                            throw Context.reportRuntimeError("File Exception: source is not a file or access failed");
                        }
                    } else {
                        throw Context.reportRuntimeError("File Exception: source file does not exist");
                    }
                }
            } else {
                throw Context.reportRuntimeError("File Exception: invalid argument type");
            }
        } else {
            throw Context.reportRuntimeError("File Exception: null pointer exception: (File object)");
        }
    }

    @Override
    public String getClassName() {
        return "File";
    }

    private static File checkInstance(Scriptable obj) {
        if (obj == null || !(obj instanceof File)) {
            throw Context.reportRuntimeError("called on incompatible object");
        }
        return (File) obj;
    }
}
package org.jdog.Raptor.js.mods;

import java.io.File;
import org.mozilla.javascript.*;

public class SendMail extends Module {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.jdog.Raptor.js.mods.util.SendMail sendMail = null;

    public SendMail() {
    }

    public static Scriptable jsConstructor(Context cx, Object[] args,
            Function ctorObj, boolean inNewExpr) {
        if (args != null && args.length <= 0 && args[0] != null) {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }

        if (args[0] instanceof String) {
            SendMail send = new SendMail();
            send.sendMail = new org.jdog.Raptor.js.mods.util.SendMail();
            send.sendMail.setSmtpServer((String) args[0]);

            if (args.length > 1 && args[1] != null) {
                if (args[1] instanceof NativeObject) {
                    NativeObject obj = (NativeObject) args[1];
                    if (obj.get("port", obj) != null && obj.get("port", obj) instanceof String) {
                        send.sendMail.setSmtpPort((String) obj.get("port", obj));
                    }
                    if (obj.get("user", obj) != null && obj.get("user", obj) instanceof String) {
                        send.sendMail.setSmtpUser((String) obj.get("user", obj));
                    }
                    if (obj.get("password", obj) != null) {
                        Object pass = obj.get("password", obj);
                        if (pass instanceof NativeObject) {
                            NativeObject obj2 = (NativeObject) pass;
                            if (obj2.get("fetch", obj) != null && obj2.get("fetch", obj) instanceof String) {
                                //Integer pw = new Integer((String) obj2.get("fetch", obj));
                                send.sendMail.setSmtpPassword("");
                            }
                        }
                        if (pass instanceof String) {
                            send.sendMail.setSmtpPassword((String) pass);
                        }
                    }
                }
            }
            return send;
        } else {
            throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
        }
    }

    @Override
    public String getClassName() {
        return "SendMail";
    }

    public static void jsFunction_setDebug(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        thisSendMail.sendMail.setDebug();
    }

    public static void jsFunction_send(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        thisSendMail.sendMail.sendMessage();
    }

    public static void jsFunction_addEmail(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && args[i] instanceof String) {
                    thisSendMail.sendMail.addMessageTo((String) args[i]);
                }
            }
        }
    }

    public static void jsFunction_addEmailCC(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && args[i] instanceof String) {
                    thisSendMail.sendMail.addMessageCC((String) args[i]);
                }
            }
        }
    }

    public static void jsFunction_addEmailBCC(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && args[i] instanceof String) {
                    thisSendMail.sendMail.addMessageBCC((String) args[i]);
                }
            }
        }
    }

    public static void jsFunction_addFrom(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length > 0 && args[0] != null) {
            thisSendMail.sendMail.setMessageFrom((String) args[0]);
        }
    }

    public static void jsFunction_addSubject(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length > 0 && args[0] != null) {
            thisSendMail.sendMail.setMessageSubject((String) args[0]);
        }
    }

    public static void jsFunction_addTxtBody(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length < 1) {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }

        if (thisSendMail.sendMail != null) {
            String msgBody = null;

            if (args[0] != null) {
                if (args[0] instanceof String) {
                    msgBody = (String) args[0];
                }
                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object obj2 = Context.toString(obj.unwrap());

                    if (obj2 instanceof String) {
                        msgBody = (String) obj2;
                    } else {
                        throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
                    }
                }
            } else {
                throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
            }
            if (msgBody != null) {
                thisSendMail.sendMail.addTxtBody(msgBody);
            }
        } else {
            throw Context.reportRuntimeError("SendMail Exception: null pointer exception (sendMail)");
        }
    }

    public static void jsFunction_addHtmlBody(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length < 1) {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }

        if (thisSendMail.sendMail != null) {
            String msgBody = null;

            if (args[0] != null) {
                if (args[0] instanceof String) {
                    msgBody = (String) args[0];
                }
                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object obj2 = Context.toString(obj.unwrap());

                    if (obj2 instanceof String) {
                        msgBody = (String) obj2;
                    } else {
                        throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
                    }
                }
            } else {
                throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
            }
            if (msgBody != null) {
                thisSendMail.sendMail.addHtmlBody(msgBody);
            }
        } else {
            throw Context.reportRuntimeError("SendMail Exception: null pointer exception (sendMail)");
        }
    }

    public static void jsFunction_addBody(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        if (args != null && args.length < 2) {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }

        if (thisSendMail.sendMail != null) {
            String msgBody = null;
            String msgType = null;

            if (args[0] != null) {
                if (args[0] instanceof String) {
                    msgBody = (String) args[0];
                }
                if (args[0] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[0];
                    Object obj2 = Context.toString(obj.unwrap());

                    if (obj2 instanceof String) {
                        msgBody = (String) obj2;
                    } else {
                        throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
                    }
                }
            } else {
                throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
            }

            if (args[1] != null) {
                if (args[1] instanceof String) {
                    msgType = (String) args[1];
                }
                if (args[1] instanceof NativeJavaObject) {
                    NativeJavaObject obj = (NativeJavaObject) args[1];
                    Object obj2 = Context.toString(obj.unwrap());

                    if (obj2 instanceof String) {
                        msgType = (String) obj2;
                    } else {
                        throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
                    }
                }
            } else {
                throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
            }

            if (msgBody != null && msgType != null) {
                thisSendMail.sendMail.addBodyPart(msgBody, msgType);
            }
        } else {
            throw Context.reportRuntimeError("SendMail Exception: null pointer exception (sendMail)");
        }
    }

    public static void jsFunction_addFile(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);

        String fileName = null;
        if (args != null && args.length < 1) {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }

        if (args[0] != null) {
            if (args[0] instanceof String) {
                fileName = (String) args[0];
            } else if (args[0] instanceof NativeJavaObject) {
                NativeJavaObject obj = (NativeJavaObject) args[0];
                Object obj2 = Context.toString(obj.unwrap());

                if (obj2 instanceof String) {
                    fileName = (String) obj2;
                } else {
                    throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
                }
            } else {
                throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
            }
        } else {
            throw Context.reportRuntimeError("SendMail Exception: invalid argument type");
        }

        if (fileName != null) {
            File file = new File(fileName);
            if (file != null && file.exists()) {
                thisSendMail.sendMail.addFile(file);
            } else {
                throw Context.reportRuntimeError("SendMail Exception: file not found: (" + file.getPath() + ")");
            }
        } else {
            throw Context.reportRuntimeError("SendMail Exception: invalid number of arguments");
        }
    }

    public static void jsFunction_sendMulti(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        thisSendMail.sendMail.sendMulti();
    }

    public static void jsFunction_sendSingle(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        thisSendMail.sendMail.sendSingle();
    }

    public static boolean jsFunction_hasError(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        return thisSendMail.sendMail.hasError();
    }

    public static String jsFunction_getError(Context cx, Scriptable thisObj,
            Object[] args, Function funObj) {
        SendMail thisSendMail = checkInstance(thisObj);
        return thisSendMail.sendMail.getError();
    }

    private static SendMail checkInstance(Scriptable obj) {
        if (obj == null || !(obj instanceof SendMail)) {
            throw Context.reportRuntimeError("called on incompatible object");
        }
        return (SendMail) obj;
    }
}

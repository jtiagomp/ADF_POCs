package com.app.common.utils;


import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {


    private static final String businessPackage = "business name contained in your package";

    public LogUtils() {
        super();
    }

    public static void writeLogError(String error, Exception ex) {
        Logger logger = Logger.getGlobal();
        String result = "";

        if (logger.isLoggable(Level.INFO)) {
            for (StackTraceElement stTrack : ex.getStackTrace()) {
                if (stTrack.getClassName().contains(businessPackage))
                    result =
                        result + "className " + stTrack.getClassName() + "; MethodName: " + stTrack.getMethodName() +
                        "; lineNumber: " + stTrack.getLineNumber() + "\n";


            }
        } else {
            for (StackTraceElement stTrack : ex.getStackTrace()) {
                if (stTrack.getClassName().contains(businessPackage))
                    result =
                        result + "className " + stTrack.getClassName() + "; MethodName: " + stTrack.getMethodName() +
                        "; lineNumber: " + stTrack.getLineNumber() + "\n";


            }
        }

        logger.severe("Error: " + error + ";\nException: " + ex + "\nStackTrace: \n" + result);
        ex.printStackTrace();
    }
}


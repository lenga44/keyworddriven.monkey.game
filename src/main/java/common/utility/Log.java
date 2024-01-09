package common.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Log {
    private static final Logger Log =  LogManager.getLogger(Log.class);

    public static void resetFileLog() throws IOException {
        FileOutputStream writer = new FileOutputStream(Constanst.LOG_FILE_PATH);
        writer.write(("").getBytes());
        writer.close();
    }
    //Info Level Logs
    public static void info (String message) {
        Log.info(message);
    }
    public static void info (Object object) {
        Log.info(object);
    }

    //Warn Level Logs
    public static void warn (String message) {
        Log.warn(message);
    }
    public static void warn (Object object) {
        Log.warn(object);
    }

    //Error Level Logs
    public static void error (String message) {
        Log.error(message);
    }
    public static void error (Object object) {
        Log.error(object);
    }

    //Fatal Level Logs
    public static void fatal (String message) {
        Log.fatal(message);
    }

    //Debug Level Logs
    public static void debug (String message) {
        Log.debug(message);
    }
    public static void debug (Object object) {
        Log.debug(object);
    }
}

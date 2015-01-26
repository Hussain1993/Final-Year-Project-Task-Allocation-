package com.Hussain.pink.triangle.SwingAppender;

import com.Hussain.pink.triangle.View.LogView;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * https://code.google.com/p/log4j-swing-appender/
 */
public class Log4jSwingAppender extends AppenderSkeleton {
    private LogView logUI = LogView.getInstance();

    public Log4jSwingAppender(){}

    @Override
    protected void append(LoggingEvent event) {
        if (!performChecks()) {
            return;
        }
        String logOutput = this.layout.format(event);
        logUI.doLog(logOutput);

        if (layout.ignoresThrowable()) {
            String[] lines = event.getThrowableStrRep();
            if (lines != null) {
                int len = lines.length;
                for (int i = 0; i < len; i++) {
                    logUI.doLog(lines[i]);
                    logUI.doLog(Layout.LINE_SEP);
                }
            }
        }
    }

    @Override
    public void close() {
        //there is nothing to close
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    /**
     * Performs checks to make sure the appender ui is still alive.
     *
     * @return If the UI is still alive
     */
    private boolean performChecks() {
        return !closed && layout != null;
    }
}

package com.tianxiabuyi.txutils.log;

public final class Settings {
    private boolean showThreadInfo = true;
    private int methodCount = 2;
    private int methodOffset = 0;
    private LogAdapter logAdapter;

    /**
     * Determines to how logs will be printed
     */
    private LogLevel logLevel = LogLevel.FULL;

    public Settings logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Settings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    public Settings methodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public Settings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public Settings logAdapter(LogAdapter logAdapter) {
        this.logAdapter = logAdapter;
        return this;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public LogAdapter getLogAdapter() {
        if (logAdapter == null) {
            logAdapter = new AndroidLogAdapter();
        }
        return logAdapter;
    }

    public void reset() {
        logLevel = LogLevel.FULL;
        showThreadInfo = true;
        methodCount = 2;
        methodOffset = 0;
    }
}

package com.myown.ownProject.core.constants;

// Enum holding fixed timeout values used across WaitUtil
public enum Timeout {

    SHORT(5),       // quick elements — buttons, links
    DEFAULT(10),    // standard wait for most elements
    LONG(20),       // slow elements — dropdowns, modals
    PAGE_LOAD(30);  // full page transitions, redirects

    // stores the second value for each constant
    private final int seconds;

    // constructor — runs when each constant is created eg SHORT(5) stores 5
    Timeout(int seconds) {
        this.seconds = seconds;
    }

    // getter — used by WaitUtil to read the second value
    public int getSeconds() {
        return seconds;
    }
}
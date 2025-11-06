package com.back.standard.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestUtil {
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static PrintStream CURRENT_OUT;
    private static final InputStream ORIGINAL_IN = System.in;
    private static InputStream CURRENT_IN;

    public static Scanner genScanner(String input) {
        return new Scanner(input);
    }

    public static void setInFromString(String input) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        CURRENT_IN = byteArrayInputStream;
        System.setIn(CURRENT_IN);
    }
    public static void clearSetInFromString() {
        System.setIn(ORIGINAL_IN);
        CURRENT_IN = null;
    }
    public static ByteArrayOutputStream setOutToByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CURRENT_OUT = new PrintStream(byteArrayOutputStream, true);
        System.setOut(CURRENT_OUT);

        return byteArrayOutputStream;
    }

    public static void clearSetOutToByteArray() {
        System.setOut(ORIGINAL_OUT);
        CURRENT_OUT.close();
        CURRENT_OUT = null;
    }
}
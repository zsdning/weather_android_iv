package com.iframe.jni;

/**
 * Created by Administrator on 2017/6/10.
 */

public class JniTest {
    static {
        System.loadLibrary("jni-test");
    }

    public static void main(String args[]){
        JniTest jniTest = new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello world");
    }

    public native String get();
    public native void set(String str);
}

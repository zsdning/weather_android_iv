//
// Created by Administrator on 2017/6/10.
//
#include "com_iframe_jni_JniTest.h"
#include <studio.h>

JNIEXPORT jstring JNICALL Java_com_iframe_jni_JniTest_get(JNIEnv *env, jobject thiz){
    printf("invoke get in c++\n");
    return env->NewStringUTF("Hello form JNI");
}


JNIEXPORT void JNICALL Java_com_iframe_jni_JniTest_set(JNIEnv *env, jobject thiz, jstring string){
    printf("invoke set in c++\n");
    char *str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%s\n",str);
    env->ReleaseStringUTFChars(string,str);
}
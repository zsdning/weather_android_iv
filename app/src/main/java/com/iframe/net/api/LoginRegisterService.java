package com.iframe.net.api;

import com.iframe.net.model.FrameRSAKey;
import com.iframe.net.model.ImageCode;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by zsdning on 16/5/10.
 * 登录注册相关接口
 */
public interface LoginRegisterService {

    /**
     * 获取密码机密key
     *
     * @return
     */
    @GET("user/passwordKey")
    @Headers("x-mars-terminal: android")
    Call<FrameRSAKey> getPwdKey();

    /**
     * 获取图形验证码
     *
     * @return
     */
    @GET("imageCode")
    @Headers("x-mars-terminal: android")
    Call<ImageCode> getImageCode();

    /**
     * 用户登录
     *
     * @param userName  用户名
     * @param password  密码
     * @param imageCode 图片验证码
     * @param pwkey     密码KEY
     * @param imageKey  图形验证码KEY
     * @return
     */
    @Deprecated
    @FormUrlEncoded
    @Headers("x-mars-terminal: android")
    @POST("user/login")
    Call<String> login(@Field("userName") String userName, @Field("password") String password, @Field("imageCode") String imageCode, @Field("pwkey") String pwkey, @Field("imageKey") String imageKey);


    /**
     * app用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param pwkey    密码KEY
     * @return
     */
    @FormUrlEncoded
    @Headers("x-mars-terminal: android")
    @POST("user/login/app")
    Call<String> appLogin(@Field("userName") String userName, @Field("password") String password, @Field("pwkey") String pwkey);


    /**
     * 用户登出
     *
     * @return
     */
    @POST("user/logout")
    @Headers("x-mars-terminal: android")
    Call<String> logout(@Header("x-mars-token") String token);

    /**
     * 用户注册
     *
     * @param phoneNo 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("user/registerCode")
    @Headers("x-mars-terminal: android")
    Call<String> registerCode(@Field("phoneNo") String phoneNo);

    /**
     * 快速注册
     *
     * @param phoneNo          手机号
     * @param recommendPhoneNo 身份证号
     * @param registerChannel  注册渠道
     * @return
     */
    @FormUrlEncoded
    @POST("user/quickRegister")
    @Headers("x-mars-terminal: android")
    Call<String> quickRegister(@Field("phoneNo") String phoneNo,@Field("verifyCode")String verifyCode, @Field("recommendPhoneNo") String recommendPhoneNo, @Field("registerChannel") String registerChannel);


    /**
     * 用户注册
     *
     * @param userName         用户名
     * @param phoneNo          手机号
     * @param verifyCode       短信验证码
     * @param password         密码
     * @param pwKey            密码KEY
     * @param recommendPhoneNo 推荐人手机号
     * @param registerChannel  注册渠道
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    @Headers("x-mars-terminal: android")
    Call<String> register(@Field("userName") String userName, @Field("phoneNo") String phoneNo, @Field("verifyCode") String verifyCode, @Field("password") String password, @Field("pwKey") String pwKey, @Field("recommendPhoneNo") String recommendPhoneNo, @Field("registerChannel") String registerChannel);


}
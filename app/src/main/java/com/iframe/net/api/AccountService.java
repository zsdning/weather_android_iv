package com.iframe.net.api;

import com.iframe.net.model.UserInfo;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by zsdnig on 2016/5/10.
 */
public interface AccountService {
    /**
     * 用户查询
     *
     * @param userName 用户名
     * @return
     */
    @Headers("x-mars-terminal: android")
    @GET("user/currentUser")
    Call<UserInfo> userQuery(@Header("x-mars-token") String token, @Query("userName") String userName);

    /**
     * 忘记密码
     *
     * @param phoneNo    手机号
     * @param realName   真实姓名
     * @param identityNo 身份证号
     * @param imageCode  图形验证码
     * @return
     */
    @FormUrlEncoded
    @Headers("x-mars-terminal: android")
    @POST("user/forgotpassword")
    Call<String> forgotPwd(@Field("phoneNo") String phoneNo, @Field("realName") String realName,
                           @Field("identityNo") String identityNo, @Field("imageCode") String imageCode);


    /**
     * 忘记密码
     *
     * @param phoneNo
     * @param realName
     * @param identityNo
     * @param imageCode
     * @param imageKey
     * @return
     */
    @FormUrlEncoded
    @Headers("x-mars-terminal: android")
    @POST("user/loginPassword/retrieve")
    Call<String> forgotPwd(@Field("phoneNo") String phoneNo, @Field("realName") String realName,
                           @Field("identityNo") String identityNo, @Field("imageCode") String imageCode, @Field("imageKey") String imageKey);

    /**
     * 修改密码
     *
     * @param password    旧密码
     * @param newPassword 新密码
     * @param pwKey       密码key
     * @return
     */
    @FormUrlEncoded
    @Headers("x-mars-terminal: android")
    @PUT("user/loginPassword")
    Call<Object> updatePwd(@Header("x-mars-token") String token, @Field("password") String password,
                           @Field("newPassword") String newPassword, @Field("pwKey") String pwKey);

}

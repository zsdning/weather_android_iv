package com.iframe.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iframe.net.api.AccountService;
import com.iframe.net.api.ApiService;
import com.iframe.net.api.InvestService;
import com.iframe.net.api.LoginRegisterService;
import com.iframe.net.api.WeatherService;
import com.iframe.net.model.NetError;
import com.squareup.okhttp.ResponseBody;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

//网络请求  Retrofit框架
public class Net {
    //test
    private static String URL_BASE_TEST = "http://retrofit.devwiki.net/";
    //real
    private static String URL_BASE = "http://api.jisuapi.com/weather/";

    private static Retrofit retrofit;

    //test
    private static ApiService apiService;
    private static WeatherService weatherService;
    private static LoginRegisterService loginRegisterService;
    private static AccountService accountService;
    private static InvestService investService;

    public static void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Net.URL_BASE)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    //test
    public static synchronized ApiService getApiService(){
        if(apiService == null){
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static synchronized WeatherService getWeatherService(){
        if(weatherService == null){
            weatherService = retrofit.create(WeatherService.class);
        }
        return weatherService;
    }

    public static synchronized LoginRegisterService getLoginRegisterService() {
        if (loginRegisterService == null) {
            loginRegisterService = retrofit.create(LoginRegisterService.class);
        }
        return loginRegisterService;
    }

    public static synchronized AccountService getAccountService() {
        if (accountService == null) {
            accountService = retrofit.create(AccountService.class);
        }
        return accountService;
    }


    public static synchronized InvestService getInvestService() {
        if (investService == null) {
            investService = retrofit.create(InvestService.class);
        }
        return investService;
    }


    /**
     * 获得error信息
     * @param errorBody
     * @return
     */
    public static String getError(ResponseBody errorBody) {
        String errorText = "请求出错";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NetError error = objectMapper.readValue(errorBody.string(), NetError.class);
            errorText = error.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorText;
    }

    /**
     * 对于某些接口，如果无返回结果，则表示为操作成功
     *
     * @param throwable
     * @return
     */
    public static boolean handleError(Throwable throwable) {
        throwable.printStackTrace();
        return throwable.getMessage().contains("No content to map due to end-of-input");
    }
}

package com.iframe.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.creditcloud.event.response.CMSResponse;
import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.net.Net;
import com.iframe.net.model.WeatherDaily;
import com.iframe.net.model.WeatherInfo;
import com.iframe.net.model.WeatherPriInfo;
import com.iframe.realm.module.NextDays;
import com.iframe.realm.module.Weather;
import com.iframe.ui.city.AddCityActivity;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.product.CircleIndicator;
import com.iframe.ui.product.weather.BannerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by zsdning on 16/5/29.  首页
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    public static final String REFRESHACTION = "HomeFragment.REFRESH_ACTION";
    //极速数据KEY
    public static String key = "7bb4707f32d03230";
    private static int INSERT = 0;
    private static int UPDATE = 1;
    private MainActivity activity;
    private TextView where, temp, date, lunarDate, week, lowTemp, highTemp, humi, windDirect, windPower, quality, state, tip;
    private TextView stateTwo, lowTempTwo, highTempTwo, windDirectTwo, windPowerTwo;
    private TextView stateThree, lowTempThree, highTempThree, windDirectThree, windPowerThree;
    private ImageView back, findCity, addLocation, weatherImg, weatherImgTwo, weatherImgThree;
    private RefreshReceiver refreshReceiver;
    //amap
    private LocationManagerProxy aMapManager;
    private String district;
    private String city;
    private String cityAt;
    private Weather weather;
    private Weather tempWeather;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter pagerAdapter;
    private ArrayList<String> bannerList = new ArrayList<>();
    private ArrayList<CMSResponse> cmsLinkList = new ArrayList<>();
    private RealmList<NextDays> nextDays = new RealmList<>();
    private MyHandler handler;
    private final int SCORLL_WHAT = 2;

    private String[] imageUrls = {"http://pic3.nipic.com/20090617/2082016_085827065_2.jpg",
            "http://pic27.nipic.com/20130305/9713815_104242739130_2.jpg",
            "http://pic23.nipic.com/20120729/7090656_094919954000_2.jpg",
            "http://pic23.nipic.com/20120918/10031483_133215033311_2.jpg"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findView(view);
        //启动时会先取缓存数据
        initView();
        //根据定位值重新刷新
        startAmap();
        return view;
    }

    private void findView(View view) {
        where = (TextView) view.findViewById(R.id.tv_where);
        //今天
        temp = (TextView) view.findViewById(R.id.tv_temp);
        date = (TextView) view.findViewById(R.id.tv_date);
        //lunarDate = (TextView) view.findViewById(R.id.tv_lunardate);
        week = (TextView) view.findViewById(R.id.tv_week);
        lowTemp = (TextView) view.findViewById(R.id.tv_lowtemp);
        highTemp = (TextView) view.findViewById(R.id.tv_hightemp);
        humi = (TextView) view.findViewById(R.id.tv_humi);
        windDirect = (TextView) view.findViewById(R.id.tv_wind_direct);
        windPower = (TextView) view.findViewById(R.id.tv_wind_power);
        quality = (TextView) view.findViewById(R.id.tv_quality);
        state = (TextView) view.findViewById(R.id.tv_state);
        tip = (TextView) view.findViewById(R.id.tv_tip);

        //明天
        stateTwo = (TextView) view.findViewById(R.id.tv_state_two);
        lowTempTwo = (TextView) view.findViewById(R.id.tv_lowtemp_two);
        highTempTwo = (TextView) view.findViewById(R.id.tv_hightemp_two);
        windDirectTwo = (TextView) view.findViewById(R.id.tv_wind_direct_two);
        windPowerTwo = (TextView) view.findViewById(R.id.tv_wind_power_two);

        //后天
        stateThree = (TextView) view.findViewById(R.id.tv_state_three);
        lowTempThree = (TextView) view.findViewById(R.id.tv_lowtemp_three);
        highTempThree = (TextView) view.findViewById(R.id.tv_hightemp_three);
        windDirectThree = (TextView) view.findViewById(R.id.tv_wind_direct_three);
        windPowerThree = (TextView) view.findViewById(R.id.tv_wind_power_three);

        //图片
        weatherImg = (ImageView) view.findViewById(R.id.weather_icon);
        weatherImgTwo = (ImageView) view.findViewById(R.id.weather_icon_two);
        weatherImgThree = (ImageView) view.findViewById(R.id.weather_icon_three);

        //添加banner
        viewPager = (ViewPager) view.findViewById(R.id.home_viewflow);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.home_viewflowindic);
        getBanners();
    }

    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null) {
                Double geoLat = location.getLatitude();
                Double geoLng = location.getLongitude();
                String cityCode = "";
                String desc = "";
                Bundle locBundle = location.getExtras();
                if (locBundle != null) {
                    cityCode = locBundle.getString("citycode");
                    desc = locBundle.getString("desc");
                }
                String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
                        + "\n精    度    :" + location.getAccuracy() + "米"
                        + "\n定位方式:" + location.getProvider() + "\n定位时间:"
                        + new Date(location.getTime()).toLocaleString() + "\n城市编码:"
                        + cityCode + "\n位置描述:" + desc + "\n省:"
                        + location.getProvince() + "\n市:" + location.getCity()
                        + "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
                        .getAdCode());
                Log.i("位置信息：", str);
                cityAt = location.getCity().split("市")[0];
                district = location.getDistrict();
                where.setText(district);
                //部分县区不支持，支持后可用district
                getInformation(cityAt);
            }
            //只定位一次
            stopAmap();
        }
    };

    /**
     * 高德地图定位
     */
    private void startAmap() {
        aMapManager = LocationManagerProxy.getInstance(activity);
        /*
         * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Weather
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
        aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, mAMapLocationListener);
    }

    private void stopAmap() {
        if (aMapManager != null) {
            aMapManager.removeUpdates(mAMapLocationListener);
            aMapManager.destory();
        }
        aMapManager = null;
    }

    ////网络请求测试
    private void getInformation(String area) {
        Net.getWeatherService().getWeather(key, area).enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Response<WeatherInfo> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Log.i("response", response.body().getMsg());
                    WeatherPriInfo weatherPriInfo = response.body().getResult();
                    initData(weatherPriInfo);
                    if (activity.getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("firstStart", true)) {
                        storeData(weatherPriInfo, INSERT);
                    } else {
                        storeData(weatherPriInfo, UPDATE);
                    }

                } else {
                    showShortToast(Net.getError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                showNetErrorToast();
                throwable.printStackTrace();
            }
        });
    }

    private void initData(WeatherPriInfo weatherPriInfo) {
        //今天
        temp.setText(weatherPriInfo.getTemp());
        //时间处理
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateVal = null;
        try {
            dateVal = df.parse(weatherPriInfo.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(dateVal);
        date.setText(dateStr);
        week.setText(weatherPriInfo.getWeek());
        //公历转农历
        String[] temp = weatherPriInfo.getDate().split("-");

        lowTemp.setText(weatherPriInfo.getTemplow());
        highTemp.setText(weatherPriInfo.getTemphigh());
        humi.setText(weatherPriInfo.getHumidity());
        windDirect.setText(weatherPriInfo.getWinddirect());
        windPower.setText(weatherPriInfo.getWindpower());
        quality.setText(weatherPriInfo.getAqi().getQuality());
        switch (weatherPriInfo.getAqi().getQuality()) {
            case "优":
                quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_excellent_shape));
                break;
            case "良":
                quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_good_shape));
                break;
            case "轻度":
                quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_pollute_shape));
                break;
            default:
                quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_heavy_pollute_shape));
                break;
        }
        state.setText(weatherPriInfo.getWeather());
        tip.setText(weatherPriInfo.getIndex().get(1).getDetail());

        //明天
        stateTwo.setText(weatherPriInfo.getDaily().get(1).getDay().getWeather());
        lowTempTwo.setText(weatherPriInfo.getDaily().get(1).getNight().getTemplow());
        highTempTwo.setText(weatherPriInfo.getDaily().get(1).getDay().getTemphigh());
        windDirectTwo.setText(weatherPriInfo.getDaily().get(1).getDay().getWinddirect());
        windPowerTwo.setText(weatherPriInfo.getDaily().get(1).getDay().getWindpower());

        //后天
        stateThree.setText(weatherPriInfo.getDaily().get(2).getDay().getWeather());
        lowTempThree.setText(weatherPriInfo.getDaily().get(2).getNight().getTemplow());
        highTempThree.setText(weatherPriInfo.getDaily().get(2).getDay().getTemphigh());
        windDirectThree.setText(weatherPriInfo.getDaily().get(2).getDay().getWinddirect());
        windPowerThree.setText(weatherPriInfo.getDaily().get(2).getDay().getWindpower());

        //当前
        initImage(weatherPriInfo.getWeather(), weatherImg, true);
        //明天
        initImage(weatherPriInfo.getDaily().get(1).getDay().getWeather(), weatherImgTwo, false);
        //后天
        initImage(weatherPriInfo.getDaily().get(2).getDay().getWeather(), weatherImgThree, false);
    }

    private void initImage(String weather, ImageView imageView, boolean today) {
        //时间处理
        Calendar cal = Calendar.getInstance();
        int time = cal.get(Calendar.HOUR_OF_DAY);

        switch (weather) {
            case "晴":
                if (today && (time < 6 || time >= 18)) {
                    imageView.setImageResource(R.drawable.ic_nightsunny_big);
                } else {
                    imageView.setImageResource(R.drawable.ic_sunny_big);
                }
                break;
            case "多云":
                if (today && (time < 6 && time >= 18)) {
                    imageView.setImageResource(R.drawable.ic_nightcloudy_big);
                } else {
                    imageView.setImageResource(R.drawable.ic_cloudy_big);
                }
                break;
            case "霾":
                imageView.setImageResource(R.drawable.ic_sandstorm_big);
                break;
            case "阴":
                imageView.setImageResource(R.drawable.ic_overcast_big);
                break;
            case "阵雨":
                imageView.setImageResource(R.drawable.ic_thundeshower_big);
                break;
            case "雷阵雨":
                imageView.setImageResource(R.drawable.ic_thundeshowehail_big);
                break;
            case "暴雨":
                imageView.setImageResource(R.drawable.ic_sleet_big);
                break;
            case "大雨":
                imageView.setImageResource(R.drawable.ic_heavyrain_big);
                break;
            case "中雨":
                imageView.setImageResource(R.drawable.ic_moderraterain_big);
                break;
            case "小雨":
                imageView.setImageResource(R.drawable.ic_lightrain_big);
                break;
            case "雪":
                if (time >= 6 && time < 18) {
                    imageView.setImageResource(R.drawable.ic_snow_big);
                } else {
                    imageView.setImageResource(R.drawable.ic_nightsnow_big);
                }
                break;
            case "雨夹雪":
                imageView.setImageResource(R.drawable.ic_rainsnow_big);
                break;
            case "雨转多云":
                imageView.setImageResource(R.drawable.ic_shower_big);
                break;
        }
    }

    /**
     * 缓存  插入或更新
     */
    private void storeData(WeatherPriInfo info, int mode) {
        if (info == null) {
            return;
        }
        weather = new Weather();
        weather.setArea(info.getCity());
        weather.setDate(info.getDate());
        weather.setHightemp(info.getTemphigh());
        weather.setLowtemp(info.getTemplow());
        weather.setTemp(info.getTemp());
        weather.setHumidity(info.getHumidity());
        weather.setQuality(info.getAqi().getQuality());
        weather.setState(info.getWeather());
        weather.setWeek(info.getWeek());
        weather.setLunardate("农历冬月初六");
        weather.setTip(info.getIndex().get(1).getDetail());
        weather.setWindDirect(info.getWinddirect());
        weather.setWindPower(info.getWindpower());

        weather.setStateTwo(info.getDaily().get(1).getDay().getWeather());
        weather.setHightempTwo(info.getDaily().get(1).getDay().getTemphigh());
        weather.setLowtempTwo(info.getDaily().get(1).getNight().getTemplow());
        weather.setWindDirectTwo(info.getDaily().get(1).getDay().getWinddirect());
        weather.setWindPowerTwo(info.getDaily().get(1).getDay().getWindpower());

        weather.setStateThree(info.getDaily().get(2).getDay().getWeather());
        weather.setHightempThree(info.getDaily().get(2).getDay().getTemphigh());
        weather.setLowtempThree(info.getDaily().get(2).getNight().getTemplow());
        weather.setWindDirectThree(info.getDaily().get(2).getDay().getWinddirect());
        weather.setWindPowerThree(info.getDaily().get(2).getDay().getWindpower());

        //下周天气情况
        WeatherDaily daily;
        nextDays.clear();   ///清零
        for(int i = 0;i < 7;i++){
            daily = info.getDaily().get(i);
            nextDays.add(new NextDays(daily.getDate(),
                    daily.getDay().getWeather(),
                    daily.getNight().getTemplow(),
                    daily.getDay().getTemphigh(),
                    daily.getDay().getWinddirect(),
                    daily.getDay().getWindpower()));
        }
        weather.setNextDays(nextDays);

        weather.setId("1");

        if (mode == INSERT) {
            try {
                activity.weatherDao.insertWeather(weather);
                //插入完成后以后启动执行更新
                activity.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                        .putBoolean("firstStart", false)
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (mode == UPDATE) {
            try {
                activity.weatherDao.updateWeather(weather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 无网络或者请求无数据返回  则从缓存取
     */
    private void initView() {
        try {
            tempWeather = MainActivity.weatherDao.getWeather("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tempWeather != null) {
            //今天
            where.setText(tempWeather.getArea());
            temp.setText(tempWeather.getTemp());
            //时间处理
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateVal = null;
            try {
                dateVal = df.parse(tempWeather.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(dateVal);
            date.setText(dateStr);
            week.setText(tempWeather.getWeek());
            //公历转农历
            String[] temp = tempWeather.getDate().split("-");

            lowTemp.setText(tempWeather.getLowtemp());
            highTemp.setText(tempWeather.getHightemp());
            humi.setText(tempWeather.getHumidity());
            windDirect.setText(tempWeather.getWindDirect());
            windPower.setText(tempWeather.getWindPower());
            quality.setText(tempWeather.getQuality());
            switch (tempWeather.getQuality()) {
                case "优":
                    quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_excellent_shape));
                    break;
                case "良":
                    quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_good_shape));
                    break;
                case "轻度":
                    quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_pollute_shape));
                    break;
                default:
                    quality.setBackground(activity.getResources().getDrawable(R.drawable.quality_heavy_pollute_shape));
                    break;
            }
            state.setText(tempWeather.getState());
            tip.setText(tempWeather.getTip());

            //明天
            stateTwo.setText(tempWeather.getStateTwo());
            lowTempTwo.setText(tempWeather.getLowtempTwo());
            highTempTwo.setText(tempWeather.getHightempTwo());
            windDirectTwo.setText(tempWeather.getWindDirectTwo());
            windPowerTwo.setText(tempWeather.getWindPowerTwo());

            //后天
            stateThree.setText(tempWeather.getStateThree());
            lowTempThree.setText(tempWeather.getLowtempThree());
            highTempThree.setText(tempWeather.getHightempThree());
            windDirectThree.setText(tempWeather.getWindDirectThree());
            windPowerThree.setText(tempWeather.getWindPowerThree());

            //当前
            initImage(tempWeather.getState(), weatherImg, true);
            //明天
            initImage(tempWeather.getStateTwo(), weatherImgTwo, false);
            //后天
            initImage(tempWeather.getStateThree(), weatherImgThree, false);
        }

    }

    private void getBanners() {
        bannerList.addAll(Arrays.asList(imageUrls));
        initBanner();
    }


    private void initBanner() {
        pagerAdapter = new BannerAdapter(activity, bannerList);
        pagerAdapter.setCMSLinkData(cmsLinkList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        pagerAdapter.notifyDataSetChanged();
        circleIndicator.setViewPager(viewPager);
        startScroll(bannerList.size());

    }

    private void startScroll(int size) {
        handler = new MyHandler(size);
        sendScrollMessage(6 * 1000);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCORLL_WHAT);
        handler.sendEmptyMessageDelayed(SCORLL_WHAT, delayTimeInMills);
    }

    public void scrollOnce(int size) {
        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % size, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyHandler extends Handler {
        private int size;

        public MyHandler(int size) {
            this.size = size;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCORLL_WHAT:
                    scrollOnce(size);
                    sendScrollMessage(6 * 1000);
                    break;
            }
        }
    }

    private void register() {
        IntentFilter refreshFilter = new IntentFilter();
        refreshFilter.addAction(REFRESHACTION);
        refreshReceiver = new RefreshReceiver();
        activity.registerReceiver(refreshReceiver, refreshFilter);
    }

    @Override
    public void onDetach() {
        if (refreshReceiver != null) {
            activity.unregisterReceiver(refreshReceiver);
        }
        //stopAmap();
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        super.onActivityCreated(savedInstanceState);
        register();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    class RefreshReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (REFRESHACTION.equals(action)) {
                where.setText(AddCityActivity.city);
                getInformation((AddCityActivity.city));
            }
        }
    }
}

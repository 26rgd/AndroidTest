package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.LoginActivity;
import com.powercn.grentechtaxi.activity.OrderListActivity;
import com.powercn.grentechtaxi.activity.SettingActivity;
import com.powercn.grentechtaxi.adapter.chlid.PopupWindowAdapter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.entity.LoginInfo;
import com.powercn.grentechtaxi.entity.OrderInfo;
import com.powercn.grentechtaxi.map.DrivingRouteOverlay;
import com.powercn.grentechtaxi.view.CircleImageView;

import lombok.Getter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.powercn.grentechtaxi.R.id.civ_mainmap_titlebar_account;
import static com.powercn.grentechtaxi.common.unit.CoordinateSystem.gcj2wgs84;

/**
 * Created by Administrator on 2017/5/10.
 */

@Getter
public class MainMapView extends MainChildView implements LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    private MapView mapView;
    private AMap aMap;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    public LocationSource.OnLocationChangedListener mListener = null;
    public UiSettings uiSettings;
    public int zoom = 14;
    public int gpscount = 0;
    public AMapLocation myLocation;
    private Marker takeOnMarker;
    private CircleImageView ivHead;
    private LinearLayout layout_mainmap_popupwindow_postion;
    private TextView tv_mainmap_cattype_taxi;
    private TextView tv_mainmap_cattype_netword;
    private TextView carTaxiLine;
    private TextView carNetWordLine;
    private TextView tvCity;
    private DrivingRouteOverlay drivingRouteOverlay;
    public InfowWindowMarkerAdapter infowWindowMarkerAdapter = new InfowWindowMarkerAdapter(activity);

    private MainMapView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    public MainMapView(MainActivity activity, int resId, MapView mapView, AMap aMap) {
        this(activity, resId);
        this.mapView = mapView;
        this.aMap = aMap;
        initMap();
    }

    @Override
    protected void initView() {
        ivHead = (CircleImageView) findViewById(civ_mainmap_titlebar_account);
        layout_mainmap_popupwindow_postion = (LinearLayout) findViewById(R.id.layout_mainmap_popupwindow_postion);
        tv_mainmap_cattype_netword = (TextView) findViewById(R.id.tv_mainmap_cattype_netword);
        tv_mainmap_cattype_taxi = (TextView) findViewById(R.id.tv_mainmap_cattype_taxi);
        tvCity = (TextView) findViewById(R.id.tv_city);
        carTaxiLine = (TextView) findViewById(R.id.tv_cartaxi_hint);
        carNetWordLine = (TextView) findViewById(R.id.tv_carnetword_hint);
    }

    @Override
    protected void bindListener() {
        ivHead.setOnClickListener(this);
        tv_mainmap_cattype_netword.setOnClickListener(this);
        tv_mainmap_cattype_taxi.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void initMap() {
        // 设置定位监听
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));//显示我的位置的自定义图标

        aMap.setMyLocationStyle(myLocationStyle);
        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        // uiSettings.setMyLocationButtonEnabled(true);
        aMap.setInfoWindowAdapter(infowWindowMarkerAdapter);
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                for (Marker marker : aMap.getMapScreenMarkers()) {
                    marker.hideInfoWindow();
                }
            }
        });
        aMap.clear();//清除所以MARK 包括定位蓝点
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        StringUnit.println(tag ,"***************-----" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
        tvCity.setText(regeocodeResult.getRegeocodeAddress().getCity());
        activity.cityName=regeocodeResult.getRegeocodeAddress().getCity();
        activity.cityName= activity.cityName.replace("市","");
        activity.cityCode=regeocodeResult.getRegeocodeAddress().getCityCode();
        String addr = "";
        if (regeocodeResult.getRegeocodeAddress().getAois().size() == 0) {
            addr = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        } else {
            AoiItem address = regeocodeResult.getRegeocodeAddress().getAois().get(0);
            addr = address.getAoiName();
        }
        activity.startAddr.init(addr, regeocodeResult.getRegeocodeQuery().getPoint().getLatitude(), regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
        activity.callCarView.getTvStart().setText(addr);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
        activity.callCarView.getTvDest().setText(address.getBuilding());
    }

    private void getGpsDesc(LatLonPoint latLonPoint) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(activity);
        geocoderSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        try {
            if (mListener != null && aMapLocation != null) {
                if (aMapLocation != null && aMapLocation.getLatitude() > 0//&& aMapLocation.getErrorCode() == 0
                        ) {
                    myLocation = aMapLocation;
                    StringUnit.println(tag,aMapLocation.getLatitude() + "|" + aMapLocation.getLongitude());
                    LatLonPoint latLonPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    if (gpscount == 0) {
                        activity.startAddr.init("", aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                    if (gpscount < 3) {
                        mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                        getGpsDesc(latLonPoint);
                    }
                    if (gpscount == 1) {
                        mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                        double[] gps = gcj2wgs84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                    gpscount = gpscount + 1;
                } else {
//                    String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
//                    StringUnit.println("AmapErr: " + errText);
                }
            }
        } catch (Exception e) {
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        try {
            if (mLocationClient == null) {
                mLocationClient = new AMapLocationClient(activity);
                mLocationOption = new AMapLocationClientOption();
                mLocationClient.setLocationListener(this);
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
               // mLocationOption.setInterval(30000);
                mLocationOption.setOnceLocation(true);
                mLocationOption.setNeedAddress(true);
                mLocationClient.setLocationOption(mLocationOption);
                mLocationClient.startLocation();
                AMapLocation aMapLocation = mLocationClient.getLastKnownLocation();
                this.onLocationChanged(aMapLocation);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void deactivate() {
        try {
            mListener = null;
            if (mLocationClient != null) {
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            }
            mLocationClient = null;
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case civ_mainmap_titlebar_account:
                HttpRequestTask.getUserInfo(activity.loginInfo.phone);
                if (LoginInfo.currentLoginSuccess == false) {
                    activity.jumpForResult(LoginActivity.class, MainActivity.otherCode);
                    return;
                }
                if (layout_mainmap_popupwindow_postion != null)
                    showPopupWindow(layout_mainmap_popupwindow_postion);
                else
                    showPopupWindow(v);
                break;
            case R.id.tv_mainmap_cattype_taxi:
                carTaxiLine.setVisibility(View.VISIBLE);
                carNetWordLine.setVisibility(View.INVISIBLE);
                tv_mainmap_cattype_taxi.setTextColor(ViewUnit.getColor(activity, R.color.textColorSelectCarFocus));
                tv_mainmap_cattype_netword.setTextColor(ViewUnit.getColor(activity, R.color.textColorSelectCarNoFocus));
                break;
            case R.id.tv_mainmap_cattype_netword:
                carTaxiLine.setVisibility(View.INVISIBLE);
                carNetWordLine.setVisibility(View.VISIBLE);
                tv_mainmap_cattype_netword.setTextColor(ViewUnit.getColor(activity, R.color.textColorSelectCarFocus));
                tv_mainmap_cattype_taxi.setTextColor(ViewUnit.getColor(activity, R.color.textColorSelectCarNoFocus));
                break;
        }
    }


    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.activity_mainmap_popupwindow, null);
        ListView listView = (ListView) contentView.findViewById(R.id.lv_mainmap_popupwindows);
        TextView tv_mainmap_popupwindows_name = (TextView) contentView.findViewById(R.id.tv_mainmap_popupwindows_name);
        CircleImageView popupWindowIcon = (CircleImageView) contentView.findViewById(R.id.popuwindow_icon);
        tv_mainmap_popupwindows_name.setText(activity.loginInfo.phone);
        final PopupWindowAdapter popupWindowAdapter = new PopupWindowAdapter(activity, null, R.layout.activity_mainmap_popupwindow_list_item);
        listView.setAdapter(popupWindowAdapter);
        listView.setDivider(null);
        int width = ViewUnit.getDisplayWidth(activity) * 7 / 15;
        StringUnit.println(tag ,"屏幕: " + ViewUnit.getDisplayWidth(activity) + "|" + ViewUnit.getDisplayHeight(activity));
        final PopupWindow popupWindow = new PopupWindow(contentView,
                width, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);


        popupWindowIcon.setImageBitmap(activity.readHeadImage());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupWindowAdapter.PopuWindowInfo popuWindowInfo = (PopupWindowAdapter.PopuWindowInfo) popupWindowAdapter.getItem(position);
                switch (popuWindowInfo) {
                    case Order:
                        //activity.tripOrderView.setVisibility(View.VISIBLE);
                        activity.jumpForResult(OrderListActivity.class,55);
                        break;

//                    case Service:
//                        activity.tripEvaluateView.setVisibility(View.VISIBLE);
//                        break;
                    case Setting:
                        activity.jumpForResult(SettingActivity.class, activity.otherCode);
                        //activity.jumpNotFinish(SettingActivity.class);
                        break;
                }
                popupWindow.dismiss();
            }
        });
        popupWindowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.userInfoView.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(ViewUnit.getDrawable(activity, R.drawable.search_icon));
        popupWindow.showAsDropDown(view);
    }
    public void showWati()
    {


        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        setVisibility(View.GONE);
        activity.tripWaitView.setVisibility(View.VISIBLE);
    }

    public void showFinish()
    {


        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        setVisibility(View.GONE);
        activity.tripWaitView.setVisibility(View.GONE);
        activity.tripFinshView.setVisibility(View.VISIBLE);
    }

    public void router() {
        if (activity.startAddr.dlat == 0 || activity.startAddr.dlng == 0) {
            activity.showToast("出发地获取有误,请重新选择");
            return;
        }
        if (activity.destAddr.dlat == 0 || activity.destAddr.dlng == 0) {
            activity.showToast("目的地获取有误,请重新选择");
            return;
        }
        this.removeLineRouter();
        final RouteSearch routeSearch = new RouteSearch(activity);
        final LatLonPoint start = new LatLonPoint(activity.startAddr.dlat, activity.startAddr.dlng);
        final LatLonPoint end = new LatLonPoint(activity.destAddr.dlat, activity.destAddr.dlng);
        RouteSearch.FromAndTo startd = new RouteSearch.FromAndTo(start, end);
        final RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(startd, RouteSearch.DRIVING_SINGLE_DEFAULT, null, null, "");
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                if (i == 1000)
                    StringUnit.println(tag ,GsonUnit.toJson(busRouteResult));
                else {
                    activity.showToast("busRouteResult规划失败");
                }
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                if (i == 1000) {

                    DrivePath drivePath = driveRouteResult.getPaths().get(0);

                    float n = 0;
                    for (DriveStep step : drivePath.getSteps()) {
                        n = n + step.getDistance();
                    }
                    activity.totalMileage = (int) (n / 1000);
                    activity.totalMoney = (int) driveRouteResult.getTaxiCost();
                    drivingRouteOverlay = new DrivingRouteOverlay(activity, aMap, drivePath, start, end, null);
                    drivingRouteOverlay.addToMap();

                    showCall();
                } else {
                    activity.showToast("driveRouteResult规划失败");
                }
            }

            public void showCall() {


                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                setVisibility(View.GONE);
                activity.callActionView.setVisibility(View.VISIBLE);
            }




            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                if (i == 1000)
                    StringUnit.println(tag ,GsonUnit.toJson(walkRouteResult));
                else {
                    activity.showToast("walkRouteResult规划失败");
                }
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
                if (i == 1000)
                    StringUnit.println(tag,GsonUnit.toJson(rideRouteResult));
                else {
                    activity.showToast("rideRouteResult规划失败");
                }
            }
        });

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        routeSearch.calculateDriveRouteAsyn(query);
                    } catch (Exception e) {
                    }
                }
            }).start();
        } catch (Exception e) {

        }
    }

    private class InfowWindowMarkerAdapter implements AMap.InfoWindowAdapter {
        private View infoWindow = null;
        private Context context;
        public Marker currentMark;

        public InfowWindowMarkerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            currentMark = marker;
            if (infoWindow == null)
                infoWindow = LayoutInflater.from(context).inflate(R.layout.activity_mapinfo, null);
            Object object = marker.getObject();
            if (object == null) return null;
            ImageView iv_map_infowindow = (ImageView) infoWindow.findViewById(R.id.iv_mapinfo);
            TextView tv_map_infowindow = (TextView) infoWindow.findViewById(R.id.tv_mapinfo);
            //iv_map_infowindow.setVisibility(View.GONE);
            final MarkInfo markInfo = (MarkInfo) object;
            tv_map_infowindow.setText(markInfo.title);
            return infoWindow;
        }
    }

    ;

    public Marker addMark(LatLng latLng, String title) {
        //   GpsInfo gpsInfo = fromGpsToAmap((Double) lat, (Double) lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title).snippet("DefaultMarker").draggable(false);
        // MarkerOptions markerOptions   =new MarkerOptions().position(latLng).title(sitename);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon_wodeweizhi)));
        Marker marker = aMap.addMarker(markerOptions);
        MarkInfo markInfo = new MarkInfo();
        markInfo.lat = (Double) latLng.latitude;
        markInfo.lng = (Double) latLng.longitude;
        markInfo.title = title;
        marker.setObject(markInfo);
        marker.showInfoWindow();
        moveTo(markInfo.lat, markInfo.lng);
        if (takeOnMarker != null)
            takeOnMarker.remove();
        takeOnMarker = marker;
        return marker;
    }

    private void moveTo(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    private class MarkInfo {
        public String title;
        public double lat;
        public double lng;
    }

    public void removeLineRouter() {
        if (drivingRouteOverlay != null)
            drivingRouteOverlay.removeFromMap();
    }

    public void setMyPostion()
    {
        if(myLocation!=null)
        {
            try {
                mListener.onLocationChanged(myLocation);// 显示系统小蓝点
                LatLng latLng=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,zoom)));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

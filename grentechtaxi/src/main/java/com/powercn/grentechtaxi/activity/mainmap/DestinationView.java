package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.chlid.DepartAdpter;
import com.powercn.grentechtaxi.view.SearchEditText;

import lombok.Getter;

/**
 * Created by Administrator on 2017/5/11.
 */
@Getter
public class DestinationView  extends MainChildView implements  PoiSearch.OnPoiSearchListener {
    private SearchEditText et_destination_search_edit;
    private ListView lv_destination_poisearch;
    private ImageView iv_title_destination;
    private DepartAdpter departAdpter;
    private TextView myHome;
    private TextView myCompany;
    private TextView myHomeDetail;
    private TextView myCompanyDetail;
    public DestinationView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        iv_title_destination = (ImageView) findViewById(R.id.iv_title_destination);
        et_destination_search_edit=(SearchEditText)findViewById(R.id.et_destination_search_edit);
        lv_destination_poisearch=(ListView)findViewById(R.id.lv_destination_poisearch);
        myHome=(TextView)findViewById(R.id.tv_myhome);
        myCompany=(TextView)findViewById(R.id.tv_mycompany);
        myHomeDetail=(TextView)findViewById(R.id.tv_myhome_detail);
        myCompanyDetail=(TextView)findViewById(R.id.tv_mycompany_detail);


    }

    @Override
    protected void bindListener() {
        iv_title_destination.setOnClickListener(this);
        myHome.setOnClickListener(this);
        myCompany.setOnClickListener(this);
        myHomeDetail.setOnClickListener(this);
        myCompanyDetail.setOnClickListener(this);
        et_destination_search_edit.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                SearchEditText v=(SearchEditText)view;
                String keyword= v.getText().toString();
                PoiSearch.Query poiQuery = new PoiSearch.Query(keyword, "", activity.cityCode);
                poiQuery.setPageSize(25);// 设置每页最多返回多少条poiitem
                poiQuery.setPageNum(0);//设置查询页码
                PoiSearch poiSearch = new PoiSearch(activity, poiQuery);
                poiSearch.setOnPoiSearchListener(DestinationView.this);
                poiSearch.searchPOIAsyn();
            }
        });
        lv_destination_poisearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(departAdpter!=null)
                {
                    com.amap.api.services.core.PoiItem poiItem=(com.amap.api.services.core.PoiItem)departAdpter.getItem(position);
                    getView().setVisibility(View.GONE);
                    activity.callCarView.getTvDest().setText(poiItem.toString());

                    activity.destAddr.init(poiItem.toString(),poiItem.getLatLonPoint().getLatitude(),poiItem.getLatLonPoint().getLongitude());
                    activity.mainMapView.router();
                }
            }
        });
    }

    @Override
    protected void initData() {
        if(activity.home.lat!=0&&activity.home.lng!=0)
        {
            myHomeDetail.setText(activity.home.name);
        }

        if(activity.company.lat!=0&&activity.company.lng!=0)
        {
            myCompanyDetail.setText(activity.company.name);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_destination:
                activity.hideSoft();
                getView().setVisibility(View.GONE);
                break;

            case R.id.tv_myhome:
                activity.hideSoft();
               activity.homeCompanyView.setVisibilityHome(View.VISIBLE);
                break;
            case R.id.tv_mycompany:
                activity.hideSoft();
                activity.homeCompanyView.setVisibilityCompany(View.VISIBLE);
                break;

            case R.id.tv_myhome_detail:
                activity.hideSoft();

                if(activity.home.lat!=0&&activity.home.lng!=0)
                {
                    activity.callCarView.getTvDest().setText(activity.home.name);
                    LatLonPoint latLonPoint=new LatLonPoint(activity.home.lat,activity.home.lng);

                    activity.destAddr.init(activity.home.name,activity.home.detailAddr,activity.home.lat,activity.home.lng);
                    activity.mainMapView.router();
                    getView().setVisibility(View.GONE);
                }else
                {
                    activity.homeCompanyView.setVisibilityHome(View.VISIBLE);
                }

                break;
            case R.id.tv_mycompany_detail:
                activity.hideSoft();

                if(activity.company.lat!=0&&activity.company.lng!=0)
                {
                    activity.callCarView.getTvDest().setText(activity.company.name);
                    LatLonPoint latLonPoint=new LatLonPoint(activity.company.lat,activity.company.lng);

                    activity.destAddr.init(activity.company.name,activity.company.detailAddr,activity.company.lat,activity.company.lng);
                    activity.mainMapView.router();
                    getView().setVisibility(View.GONE);
                }else
                {
                    activity.homeCompanyView.setVisibilityCompany(View.VISIBLE);
                }

                break;

        }

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (poiResult == null) return;
        departAdpter = new DepartAdpter<Object>(activity, poiResult.getPois(), R.layout.activity_depart_poisearch_list_item);
        lv_destination_poisearch.setAdapter(departAdpter);
        lv_destination_poisearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }



    public void seachHotPoi()
    {

        PoiSearch.Query poiQuery = new PoiSearch.Query(activity.cityName, "",activity.cityCode);
        poiQuery.setPageSize(25);// 设置每页最多返回多少条poiitem
        poiQuery.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(activity, poiQuery);
        poiSearch.setOnPoiSearchListener(DestinationView.this);
        poiSearch.searchPOIAsyn();

    }
}

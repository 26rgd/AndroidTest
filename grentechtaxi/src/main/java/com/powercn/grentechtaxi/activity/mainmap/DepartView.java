package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
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
 * Created by Administrator on 2017/5/10.
 */

@Getter
public class DepartView extends MainChildView implements PoiSearch.OnPoiSearchListener {
    private SearchEditText et_depart_search_edit;
    private ListView lv_depart_poisearch;
    private TextView tv_title_back_hint;
    private ImageView iv_title_depart;

    private DepartAdpter departAdpter;

    public DepartView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        iv_title_depart = (ImageView) findViewById(R.id.iv_title_depart);
        et_depart_search_edit = (SearchEditText) findViewById(R.id.et_depart_search_edit);
        lv_depart_poisearch = (ListView) findViewById(R.id.lv_depart_poisearch);
        tv_title_back_hint = (TextView) findViewById(R.id.tv_title_back_hint);
    }

    @Override
    protected void bindListener() {
        iv_title_depart.setOnClickListener(this);
        et_depart_search_edit.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                SearchEditText v = (SearchEditText) view;
                String keyword = v.getText().toString();
                PoiSearch.Query poiQuery = new PoiSearch.Query(keyword, "", activity.cityCode);
                poiQuery.setPageSize(15);// 设置每页最多返回多少条poiitem
                poiQuery.setPageNum(0);//设置查询页码
                PoiSearch poiSearch = new PoiSearch(activity, poiQuery);
                poiSearch.setOnPoiSearchListener(DepartView.this);
                poiSearch.searchPOIAsyn();
            }
        });
        lv_depart_poisearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (departAdpter != null) {
                    com.amap.api.services.core.PoiItem poiItem = (com.amap.api.services.core.PoiItem) departAdpter.getItem(position);
                    getView().setVisibility(View.GONE);
                    activity.callCarView.getTvStart().setText(poiItem.toString());

                    activity.startAddr.init(poiItem.toString(),poiItem.getLatLonPoint().getLatitude(),poiItem.getLatLonPoint().getLongitude());
                    LatLng latLng=new LatLng(poiItem.getLatLonPoint().getLatitude(),poiItem.getLatLonPoint().getLongitude());
                    activity.mainMapView.addMark(latLng,"在这里上车");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_depart:
                activity.hideSoft();
                getView().setVisibility(View.GONE);
                break;

        }

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (poiResult == null) return;
        departAdpter = new DepartAdpter<Object>(activity, poiResult.getPois(), R.layout.activity_depart_poisearch_list_item);
        lv_depart_poisearch.setAdapter(departAdpter);
        lv_depart_poisearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void onSearchNear(LatLonPoint latLonPoint) {
        try {
            PoiSearch.Query poiQuery = new PoiSearch.Query("", "", activity.cityCode);
            poiQuery.setPageSize(15);// 设置每页最多返回多少条poiitem
            poiQuery.setPageNum(0);//设置查询页码
            PoiSearch poiSearch = new PoiSearch(activity, poiQuery);
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint,300));
            poiSearch.setOnPoiSearchListener(DepartView.this);
            poiSearch.searchPOIAsyn();
        } catch (Exception e) {

        }

    }
}

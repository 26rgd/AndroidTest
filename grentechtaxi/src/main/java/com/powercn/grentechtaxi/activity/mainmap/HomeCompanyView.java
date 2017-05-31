package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.chlid.DepartAdpter;
import com.powercn.grentechtaxi.entity.AddressInfo;
import com.powercn.grentechtaxi.view.SearchEditText;

import lombok.Getter;


/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeCompanyView extends AbstractChildView implements  PoiSearch.OnPoiSearchListener  {
    private SearchEditText searchEditText;
    private ListView listView;
    private ImageView ivBack;
    private TextView tvTitle;
    private DepartAdpter departAdpter;
    private AddrType currentAddrType;
    public HomeCompanyView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_setaddr_back);
        searchEditText=(SearchEditText)findViewById(R.id.search_homecompany);
        listView=(ListView)findViewById(R.id.lv_homecompany);
        tvTitle=(TextView)findViewById(R.id.tv_setaddr_title);


    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        searchEditText.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                SearchEditText v=(SearchEditText)view;
                String keyword= v.getText().toString();
                PoiSearch.Query poiQuery = new PoiSearch.Query(keyword, "", "0755");
                poiQuery.setPageSize(25);// 设置每页最多返回多少条poiitem
                poiQuery.setPageNum(0);//设置查询页码
                PoiSearch poiSearch = new PoiSearch(activity, poiQuery);
                poiSearch.setOnPoiSearchListener(HomeCompanyView.this);
                poiSearch.searchPOIAsyn();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(departAdpter!=null)
                {
                    com.amap.api.services.core.PoiItem poiItem=(com.amap.api.services.core.PoiItem)departAdpter.getItem(position);

                    AddressInfo addressInfo=new AddressInfo();
                    addressInfo.name=poiItem.toString();
                    addressInfo.detailAddr=poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet();
                    addressInfo.lat=(float)poiItem.getLatLonPoint().getLatitude();
                    addressInfo.lng=(float)poiItem.getLatLonPoint().getLongitude();
                    if(currentAddrType==AddrType.Home)
                    {
                        AddressInfo.saveHome(activity,addressInfo);
                        activity.home=addressInfo;
                        activity.destinationView.getMyHomeDetail().setText(activity.home.name);
                    }
                        else
                    {
                        AddressInfo.saveCompany(activity,addressInfo);
                        activity.company=addressInfo;
                        activity.destinationView.getMyCompanyDetail().setText(activity.company.name);
                    }

                    getView().setVisibility(View.GONE);

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
            case R.id.iv_setaddr_back:
                activity.hideSoft();
                getView().setVisibility(View.GONE);
                break;

        }

    }


    public void setVisibilityHome(int visibility) {
        this.currentAddrType=AddrType.Home;
        tvTitle.setText(R.string.setmyhome);
        super.setVisibility(visibility);
    }

    public void setVisibilityCompany(int visibility) {
        this.currentAddrType=AddrType.Company;
        tvTitle.setText(R.string.setmycompany);
        super.setVisibility(visibility);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (poiResult == null) return;
        departAdpter = new DepartAdpter<Object>(activity, poiResult.getPois(), R.layout.activity_depart_poisearch_list_item);
        listView.setAdapter(departAdpter);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Getter
    public enum AddrType
    {
        Home("家",0),Company("公司",1);
        private String name;
        private int value;

        AddrType(String name, int value) {
            this.name = name;
            this.value = value;
        }

    }
}

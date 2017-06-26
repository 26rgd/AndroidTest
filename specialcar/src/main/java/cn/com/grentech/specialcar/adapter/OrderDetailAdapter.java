package cn.com.grentech.specialcar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractAdapter;
import cn.com.grentech.specialcar.common.unit.DateUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderDetailInfo;
import cn.com.grentech.specialcar.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import static android.R.attr.id;
import static cn.com.grentech.specialcar.entity.OrderDetailInfo.flag;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderDetailAdapter extends AbstractAdapter {
    private Order info;
    @Getter
    private double mileage;
    @Getter
    private int mflag;
    public OrderDetailAdapter(Context context, List data, int itemres) {
        super(context, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tvLine1 = (TextView) view.findViewById(R.id.tv_orderdetail_line1);
        TextView tvLine2 = (TextView) view.findViewById(R.id.tv_orderdetail_line2);
        Item item = (Item) getItem(position);
        tvLine1.setText(item.getName());
        tvLine2.setText(item.getValue());
        return view;
    }

    private void bulidItem(String name,String value)
    {
        Item item= new Item(name,value);
        getData().add(item);
    }
    public void update(Order info) {
        getData().clear();
        this.info=info;
//        OrderDetailInfo.createrName.setValue(info.getCreaterName());
//        getData().add(OrderDetailInfo.createrName);
        bulidItem(OrderDetailInfo.createrName.getName(),info.getCreaterName());

//        OrderDetailInfo.createrPhone.setValue(info.getCreaterPhone());
//        getData().add(OrderDetailInfo.createrPhone);
        bulidItem(OrderDetailInfo.createrPhone.getName(),info.getCreaterPhone());

//        OrderDetailInfo.riderName.setValue(info.getRiderName());
//        getData().add(OrderDetailInfo.riderName);
        bulidItem(OrderDetailInfo.riderName.getName(),info.getRiderName());

//        OrderDetailInfo.riderPhone.setValue(info.getRiderPhone());
//        getData().add(OrderDetailInfo.riderPhone);
        bulidItem(OrderDetailInfo.riderPhone.getName(),info.getRiderPhone());

//        OrderDetailInfo.riderCount.setValue(String.valueOf(info.getRiderCount()));
//        getData().add(OrderDetailInfo.riderCount);
        bulidItem(OrderDetailInfo.riderCount.getName(),String.valueOf(info.getRiderCount()));

//        OrderDetailInfo.fromAddr.setValue(info.getFrom());
//        getData().add(OrderDetailInfo.fromAddr);
        bulidItem(OrderDetailInfo.fromAddr.getName(),info.getFrom());

//        OrderDetailInfo.toAddr.setValue(info.getTo());
//        getData().add(OrderDetailInfo.toAddr);
        bulidItem(OrderDetailInfo.toAddr.getName(),info.getTo());

//        OrderDetailInfo.startTime.setValue(DateUnit.formatDate(info.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
//        getData().add(OrderDetailInfo.startTime);
        bulidItem(OrderDetailInfo.startTime.getName(),DateUnit.formatDate(info.getStartTime(),"yyyy-MM-dd HH:mm:ss"));

//        OrderDetailInfo.mileage.setValue(String.valueOf((int)info.getMileage())+"米");
//        getData().add(OrderDetailInfo.mileage);
        mileage=info.getMileage();
        bulidItem(OrderDetailInfo.mileage.getName(),String.valueOf((int)info.getMileage())+"米");

//        OrderDetailInfo.flag.setValue(OrderStatus.values()[info.getFlag()].getName());
//        getData().add(OrderDetailInfo.flag);
        this.mflag=info.getFlag();
        bulidItem(OrderDetailInfo.flag.getName(),OrderStatus.values()[info.getFlag()].getName());
        notifyDataSetChanged();
    }
   public void upDateMileage(int id,double mileage)
   {
       if(info!=null&&info.getId()==id)
       {
           OrderDetailInfo.mileage.setValue(String.valueOf((int)mileage)+"米");
           for (Item line:(List<Item>)getData())
           {
               if(line.getName().equals(OrderDetailInfo.mileage.getName()))
               {
                   line.setValue(String.valueOf((int)mileage)+"米");
               }
           }

           info.setMileage(mileage);
           this.mileage=mileage;
           notifyDataSetChanged();
       }
   }

    public void upDateFlag(int id,int mflag)
    {
        if(true)
        {
            OrderDetailInfo.flag.setValue(OrderStatus.values()[mflag].getName());
            for (Item line:(List<Item>)getData())
            {
             if(line.getName().equals(OrderDetailInfo.flag.getName()))
             {
                 line.setValue(OrderStatus.values()[mflag].getName());
                 StringUnit.println(tag,OrderStatus.values()[mflag].getName());

             }
            }
            info.setFlag(mflag);
            this.mflag=mflag;
            notifyDataSetChanged();
        }
    }

    @Getter
    @Setter
    class Item
    {
        public String name;
        public String value;

        public Item(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}

package cn.com.grentech.specialcar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.other.GpsFilter;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/6/23.
 */

@Getter
@Setter
public class LoadLine implements Serializable {
    private transient String tag =this.getClass().getName();

    private transient static final long serialVersionUID = -3546403219344404215L;
    private Order orderinfo;
    private int index = 0;
    private LineInfo[] lines = new LineInfo[4500];

    public LoadLine(Order orderinfo) {
        this.orderinfo = orderinfo;
    }

    @Getter
    @Setter
    private class LineInfo implements Serializable {
        private transient static final long serialVersionUID = -7830943583338303584L;
        private ArrayList<GpsInfo> gpsList = new ArrayList<>();
    }

    public Double getTotalDistance() {
        double result = 0.0;
        for (int i = 0; i <= index; i++) {
            LineInfo lineInfo = lines[i];
            if (lineInfo != null) {
                if (lineInfo.getGpsList().size() >= 2) {
                    result = result + GpsFilter.getMoveDistance(lineInfo.getGpsList());
                }
            }
        }
        return result;
    }

    public void addGps(GpsInfo g) {
        try {
            LineInfo info = lines[index];
            if (info == null) {
                lines[index] = new LineInfo();
                info = lines[index];
            }
            info.getGpsList().add(g);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    public List<GpsInfo> getListGps()
    {
        List<GpsInfo> list=new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            LineInfo lineInfo = lines[i];
            if (lineInfo != null) {

                list.addAll(lineInfo.getGpsList());
            }
        }
        return list;
    }
}

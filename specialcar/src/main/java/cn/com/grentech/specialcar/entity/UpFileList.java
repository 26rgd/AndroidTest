package cn.com.grentech.specialcar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * Created by Administrator on 2017/7/21.
 */

@Data
public class UpFileList implements Serializable {
    private transient static final long serialVersionUID = 6578532713185074229L;
    private String phone;
    private Set<FileUploadInfo> logs=new HashSet<>();//日志文件
    private Set<FileUploadInfo> roadLines=new HashSet<>();//RoadLine文件
    private Set<FileUploadInfo> roadJsons=new HashSet<>();//dat文件
    private Set<FileUploadInfo> files=new HashSet<>();//dat文件

}

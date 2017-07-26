package cn.com.grentech.specialcar.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/7/21.
 */

@Data
public class FileUploadInfo implements Serializable {

    private transient static final long serialVersionUID = 7484020594229664032L;

    private String filename;
    private String path;
    private Boolean isUpdate;
    private long lastModified;


    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (this == obj)
                return true;
            if (obj instanceof FileUploadInfo) {
                FileUploadInfo fileUploadInfo = (FileUploadInfo) obj;
                if (fileUploadInfo.getFilename().equals(this.filename))
                    return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return filename.hashCode();
    }
}

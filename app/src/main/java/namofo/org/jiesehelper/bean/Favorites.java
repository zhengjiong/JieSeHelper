package namofo.org.jiesehelper.bean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import namofo.org.jiesehelper.constants.AppDatabase;

/**
 * create by zhengjiong
 * Date: 2015-07-12
 * Time: 14:06
 */
@Table(databaseName = AppDatabase.NAME)
public class Favorites extends BaseModel{

    public Favorites(){}

    public Favorites(int id, String subject, String imgUrl, String detailUrl, long date) {
        this.id = id;
        this.subject = subject;
        this.imgUrl = imgUrl;
        this.detailUrl = detailUrl;
        this.date = date;
    }

    @Column
    @PrimaryKey(autoincrement = false)
    int id;

    @Column(name = "subject", defaultValue = "")
    String subject;

    @Column(name = "img_url")
    String imgUrl;

    @Column(name = "detail_url")
    String detailUrl;

    @Column(name = "date")
    long date;//收藏時間

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public long getData() {
        return date;
    }

    public void setData(long data) {
        this.date = data;
    }
}
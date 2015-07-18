package namofo.org.jiesehelper.bean;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

import namofo.org.jiesehelper.constants.AppDatabase;

@Table(databaseName = AppDatabase.NAME, tableName = "article")
public class Article extends BaseModel implements Serializable{

    @Column
    @PrimaryKey
    int id;

    @Expose
    @Column(name = "nid")
    int nid;

    @Expose
    @Column(name = "subject", defaultValue = "")
    String subject;

    @Column(name = "content")
    String content;

    @Column(name = "category")
    int category;//文章分類

    @Column(name = "iscollect")
    boolean iscollect;

    @Column(name = "file_path")
    String file_path;

    @Column(name = "start_page")
    int start_page;

    @Column(name = "end_page")
    int end_page;

    @Expose
    @Column(name = "img_url")
    String imgUrl;

    @Expose
    @Column(name = "detail_url")
    String detailUrl;

    @Expose
    @Column(name = "readtimes")
    int readtimes;

    @Expose
    @Column(name = "cdate")
    String cdate;

    @Column
    @ForeignKey(
        references = {@ForeignKeyReference(columnName = "file_type",
                        columnType = Integer.class,
                        foreignColumnName = "id"),},
        saveForeignKeyModel = false)
    ArticleFileType articleFileType;

    public Article(){}

    public Article(int nid, String subject, String imgUrl, String detailUrl) {
        this.nid = nid;
        this.subject = subject;
        this.imgUrl = imgUrl;
        this.detailUrl = detailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean iscollect() {
        return iscollect;
    }

    public void setIscollect(boolean iscollect) {
        this.iscollect = iscollect;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getStart_page() {
        return start_page;
    }

    public void setStart_page(int start_page) {
        this.start_page = start_page;
    }

    public int getEnd_page() {
        return end_page;
    }

    public void setEnd_page(int end_page) {
        this.end_page = end_page;
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

    public int getReadtimes() {
        return readtimes;
    }

    public void setReadtimes(int readtimes) {
        this.readtimes = readtimes;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public ArticleFileType getArticleFileType() {
        return articleFileType;
    }

    public void setArticleFileType(ArticleFileType articleFileType) {
        this.articleFileType = articleFileType;
    }

    public static List<Article> json2List(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, new TypeToken<List<Article>>(){}.getType());
    }
}

package namofo.org.jiesehelper.bean;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import namofo.org.jiesehelper.constants.AppDatabase;

@Table(databaseName = AppDatabase.NAME, tableName = "article")
public class Article extends BaseModel {

    @Column
    @PrimaryKey
    int id;

    @Column(name = "title", defaultValue = "")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "category")
    int category;

    @Column(name = "iscollect")
    boolean iscollect;

    @Column(name = "file_path")
    String file_path;

    @Column(name = "start_page")
    int start_page;

    @Column(name = "end_page")
    int end_page;

    @Column
    @ForeignKey(
        references = {@ForeignKeyReference(columnName = "file_type",
                        columnType = Integer.class,
                        foreignColumnName = "id"),},
        saveForeignKeyModel = false)
    ArticleFileType articleFileType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArticleFileType getArticleFileType() {
        return articleFileType;
    }

    public void setArticleFileType(ArticleFileType articleFileType) {
        this.articleFileType = articleFileType;
    }
}

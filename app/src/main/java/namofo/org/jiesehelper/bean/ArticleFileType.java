package namofo.org.jiesehelper.bean;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import namofo.org.jiesehelper.constants.AppDatabase;

@Table(databaseName = AppDatabase.NAME, tableName = "article_file_type")
public class ArticleFileType extends BaseModel {

    @PrimaryKey
    @Column(name = "id")
    int id;

    @Column(name = "file_name")
    String file_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}

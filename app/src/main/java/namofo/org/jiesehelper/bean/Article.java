package namofo.org.jiesehelper.bean;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import namofo.org.jiesehelper.constants.AppDatabase;

@Table(databaseName = AppDatabase.NAME, tableName = "article")
public class Article extends BaseModel {

    @Column
    @PrimaryKey
    public int id;

    @Column(name = "title", defaultValue = "")
    public String title;
    public String content;

    @Column(name = "category")
    public int category;
    public boolean iscollect;
    public String file_path;
    public int start_page;
    public int end_page;

    //@Column(name = "file_type")
    //public int file_type;

    @Column
    @ForeignKey(
        references = {@ForeignKeyReference(columnName = "file_type",
                        columnType = Integer.class,
                        foreignColumnName = "id"),},
        saveForeignKeyModel = false)
    public ArticleFileType articleFileType;


}

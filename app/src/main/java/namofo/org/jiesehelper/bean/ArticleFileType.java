package namofo.org.jiesehelper.bean;


import java.io.Serializable;

public class ArticleFileType implements Serializable {

    private Long id;
    private String file_name;

    public ArticleFileType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

}

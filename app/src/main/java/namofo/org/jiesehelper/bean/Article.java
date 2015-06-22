package namofo.org.jiesehelper.bean;


import java.io.Serializable;

public class Article implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String category;
    private Boolean iscollect;
    private String file_path;
    private int start_page;
    private int end_page;
    private long file_type;

    private ArticleFileType articleFileType;
    private Long articleFileType__resolvedKey;


    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIscollect() {
        return iscollect;
    }

    public void setIscollect(Boolean iscollect) {
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

    public long getFile_type() {
        return file_type;
    }

    public void setFile_type(long file_type) {
        this.file_type = file_type;
    }

    public ArticleFileType getArticleFileType() {
        return articleFileType;
    }

    public void setArticleFileType(ArticleFileType articleFileType) {
        this.articleFileType = articleFileType;
    }

    public Long getArticleFileType__resolvedKey() {
        return articleFileType__resolvedKey;
    }

    public void setArticleFileType__resolvedKey(Long articleFileType__resolvedKey) {
        this.articleFileType__resolvedKey = articleFileType__resolvedKey;
    }
}

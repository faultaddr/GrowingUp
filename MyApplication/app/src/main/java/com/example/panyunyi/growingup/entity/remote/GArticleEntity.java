package com.example.panyunyi.growingup.entity.remote;

/**
 * Created by panyu on 2017/7/6.
 */
public class GArticleEntity {
    private String articleId;
    private String articleContent;
    private String articleDate;
    private String articleType;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GArticleEntity that = (GArticleEntity) o;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (articleContent != null ? !articleContent.equals(that.articleContent) : that.articleContent != null)
            return false;
        if (articleDate != null ? !articleDate.equals(that.articleDate) : that.articleDate != null) return false;
        if (articleType != null ? !articleType.equals(that.articleType) : that.articleType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleId != null ? articleId.hashCode() : 0;
        result = 31 * result + (articleContent != null ? articleContent.hashCode() : 0);
        result = 31 * result + (articleDate != null ? articleDate.hashCode() : 0);
        result = 31 * result + (articleType != null ? articleType.hashCode() : 0);
        return result;
    }
}

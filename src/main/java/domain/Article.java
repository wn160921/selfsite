package domain;

import java.util.List;

public class Article {
    String title;
    String Articleurl;
    String auther;
    String autherUrl;
    int agreeNum;
    String content;
    int discussNum;
    List<ArticleDiscuss> articleDiscussList;
    String dateModified;

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dataModified) {
        this.dateModified = dataModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleurl() {
        return Articleurl;
    }

    public void setArticleurl(String articleurl) {
        Articleurl = articleurl;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getAutherUrl() {
        return autherUrl;
    }

    public void setAutherUrl(String autherUrl) {
        this.autherUrl = autherUrl;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDiscussNum() {
        return discussNum;
    }

    public void setDiscussNum(int discussNum) {
        this.discussNum = discussNum;
    }

    public List<ArticleDiscuss> getArticleDiscussList() {
        return articleDiscussList;
    }

    public void setArticleDiscussList(List<ArticleDiscuss> articleDiscussList) {
        this.articleDiscussList = articleDiscussList;
    }
}

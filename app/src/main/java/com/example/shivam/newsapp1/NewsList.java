package com.example.shivam.newsapp1;

public class NewsList {

    private String webTitle, sectionName, webPublicationDate, webPublicationTime, webUrl, author;

    public NewsList(String webTitle, String sectionName, String webPublicationDate, String webPublicationTime, String webUrl, String author) {
        this.webTitle = webTitle;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webPublicationTime = webPublicationTime;
        this.webUrl = webUrl;
        this.author = author;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebPublicationTime() {
        return webPublicationTime;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return author;
    }
}

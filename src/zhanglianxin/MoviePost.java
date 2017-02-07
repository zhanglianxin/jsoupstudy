package zhanglianxin;

import java.util.List;

public class MoviePost {
    private int uid;
    private String title;
    private String lastModified;
    private String imgPath;
    private List<String> links;

    public MoviePost() {
        super();
    }

    public MoviePost(int uid, String title, String lastModified,
            String imgPath, List<String> links) {
        super();
        this.uid = uid;
        this.title = title;
        this.lastModified = lastModified;
        this.imgPath = imgPath;
        this.links = links;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "MoviePost [uid=" + uid + ", title=" + title + ", lastModified="
                + lastModified + ", imgPath=" + imgPath + ", links=" + links
                + "]";
    }

}

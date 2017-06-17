package multitype.viewItem;


import bean.UserBean;
import bean.UserRatingBean;

/**
 * Created by Administrator on 2017/4/8.
 */
public class ReviewItem implements Item {
    private UserBean author;
    private int likes;
    private String content;
    private String morecontent;
    private String update;
    private UserRatingBean rating;

    public ReviewItem(){

    }
    public UserBean getAuthor() {
        return author;
    }

    public void setAuthor(UserBean author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public UserRatingBean getRating() {
        return rating;
    }

    public void setRating(UserRatingBean rating) {
        this.rating = rating;
    }

    public String getMorecontent() {
        return morecontent;
    }

    public void setMorecontent(String morecontent) {
        this.morecontent = morecontent;
    }

}

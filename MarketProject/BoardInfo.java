package com.androidlec.marketproject;

public class BoardInfo {
    int seqno;
    int uSeqno;
    String id;
    String image;
    String title;
    String price;
    String content;
    int hit;
    String sido;
    String latitude;
    String longtitude;
    int isDone;
    String insertDate;
    String deleteDate;
    int likes;
    int likeCheck;

//    public BoardInfo(int seqno, int uSeqno, String content, int hit, String sido, String latitude,
//                     String longtitude, int isDone, String insertDate, String deleteDate,
//                     String title, String price) {
//        this.seqno = seqno;
//        this.uSeqno = uSeqno;
//        this.content = content;
//        this.hit = hit;
//        this.sido = sido;
//        this.latitude = latitude;
//        this.longtitude = longtitude;
//        this.isDone = isDone;
//        this.insertDate = insertDate;
//        this.deleteDate = deleteDate;
//        this.title = title;
//        this.price = price;
//    }

    public BoardInfo(int seqno, int uSeqno, String title, String price, String content, int hit, String image, String sido, String latitude, String longtitude, int isDone, int likes, int likeCheck){
        this.seqno = seqno;
        this.uSeqno = uSeqno;
        this.title = title;
        this.price = price;
        this.content = content;
        this.hit = hit;
        this.image = image;
        this.sido = sido;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.isDone = isDone;
        this.likes = likes;
        this.likeCheck = likeCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(int likeCheck) {
        this.likeCheck = likeCheck;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(int uSeqno) {
        this.uSeqno = uSeqno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

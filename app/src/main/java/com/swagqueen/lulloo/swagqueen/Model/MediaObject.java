package com.swagqueen.lulloo.swagqueen.Model;

public class MediaObject {
  private int uId;
  private String title;
  private String mediaUrl;
  private String mediaCoverImgUrl;



  public int getId() {
    return uId;
  }

  public void setId(int uId) {
    this.uId = uId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String mTitle) {
    this.title = mTitle;
  }

  public String getUrl() {
    return mediaUrl;
  }

  public void setUrl(String mUrl) {
    this.mediaUrl = mUrl;
  }

  public String getCoverUrl() {
    return mediaCoverImgUrl;
  }

  public void setCoverUrl(String mCoverUrl) {
    this.mediaCoverImgUrl = mCoverUrl;
  }
}

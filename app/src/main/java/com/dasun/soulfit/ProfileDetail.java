package com.dasun.soulfit;

public class ProfileDetail {
    private String uid;
    private String displayName;
    private String secondaryEmail;
    private String phone;
    private String fb;
    private String twitter;

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    private String proPic;

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public ProfileDetail(){}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public ProfileDetail(String dname, String sEmail, String phn, String fb ){
        this.displayName = dname;
        this.secondaryEmail = sEmail;
        this.phone = phn;
        this.fb = fb;
    }


}

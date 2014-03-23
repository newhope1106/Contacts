package com.newhope.contacts;

public class ContactBean {
    private int contactId;//联系人ID
    private String displayName;//联系人姓名
    private String phoneNum;//联系人手机号
    private String sortKey;//排序Key
    private Long photoId;//头像ID
    private String lookUpKey;
    private int selected = 0;//被选中的行号
    private String formattedNumber;//被格式化的号码
    private String pinyin;//姓名对应的汉语拼音
                                                                                                                                                                                         
    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getSortKey() {
        return sortKey;
    }
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    public Long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
    public String getLookUpKey() {
        return lookUpKey;
    }
    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }
    public int getSelected() {
        return selected;
    }
    public void setSelected(int selected) {
        this.selected = selected;
    }
    public String getFormattedNumber() {
        return formattedNumber;
    }
    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}

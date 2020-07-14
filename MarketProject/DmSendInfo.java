package com.androidlec.marketproject;

public class DmSendInfo {

    int dm_Seqno;
    String dm_Title;
    String dm_Content;
    int dm_bReceive;
    String dm_ReceiveId;

    public DmSendInfo(int dm_Seqno, String dm_Title, String dm_Content, int dm_bReceive, String dm_ReceiveId) {
        this.dm_Seqno = dm_Seqno;
        this.dm_Title = dm_Title;
        this.dm_Content = dm_Content;
        this.dm_bReceive = dm_bReceive;
        this.dm_ReceiveId = dm_ReceiveId;
    }

    public int getDm_Seqno() {
        return dm_Seqno;
    }

    public void setDm_Seqno(int dm_Seqno) {
        this.dm_Seqno = dm_Seqno;
    }

    public String getDm_Title() {
        return dm_Title;
    }

    public void setDm_Title(String dm_Title) {
        this.dm_Title = dm_Title;
    }

    public String getDm_Content() {
        return dm_Content;
    }

    public void setDm_Content(String dm_Content) {
        this.dm_Content = dm_Content;
    }

    public int getDm_bReceive() {
        return dm_bReceive;
    }

    public void setDm_bReceive(int dm_bReceive) {
        this.dm_bReceive = dm_bReceive;
    }

    public String getDm_ReceiveId() {
        return dm_ReceiveId;
    }

    public void setDm_ReceiveId(String dm_ReceiveId) {
        this.dm_ReceiveId = dm_ReceiveId;
    }
}

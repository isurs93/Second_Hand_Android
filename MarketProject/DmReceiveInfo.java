package com.androidlec.marketproject;

public class DmReceiveInfo {
    int dm_Seqno;
    int dm_bSend;
    String dm_Title;
    String dm_Content;
    String dm_SendId;

    public DmReceiveInfo(int dm_Seqno, String dm_Title, String dm_Content, int dm_bSend, String dm_SendId) {
        this.dm_Seqno = dm_Seqno;
        this.dm_bSend = dm_bSend;
        this.dm_Title = dm_Title;
        this.dm_Content = dm_Content;
        this.dm_SendId = dm_SendId;
    }

    public int getDm_Seqno() {
        return dm_Seqno;
    }

    public void setDm_Seqno(int dm_Seqno) {
        this.dm_Seqno = dm_Seqno;
    }

    public int getDm_bSend() {
        return dm_bSend;
    }

    public void setDm_bSend(int dm_bSend) {
        this.dm_bSend = dm_bSend;
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

    public String getDm_SendId() {
        return dm_SendId;
    }

    public void setDm_SendId(String dm_SendId) {
        this.dm_SendId = dm_SendId;
    }
}

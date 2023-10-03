package com.pigeon.post.models;

public enum IMAPProvider {
    GMAIL("imap.gmail.com",993,true,true),
    HOTMAIL("outlook.office365.com",587,true,false),
    MICROSOFT("outlook.office365.com",993,true,true),
    YAHOO("smtp.mail.yahoo.com",465,true,true);
    public final String host;
    public final int port;
    public final boolean auth;
    public final boolean ttls;

    IMAPProvider(String host, int port, boolean auth, boolean ttls) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.ttls = ttls;
    }
}

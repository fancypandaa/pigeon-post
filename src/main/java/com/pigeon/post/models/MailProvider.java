package com.pigeon.post.models;
public enum MailProvider {
    GMAIL("smtp.gmail.com",587,true,true),
    HOTMAIL("smtp-mail.outlook.com",587,true,false),
    MICROSOFT("smtp-mail.outlook.com",587,true,false),
    YAHOO("smtp.mail.yahoo.com",465,true,true);
    public final String host;
    public final int port;
    public final boolean auth;
    public final boolean ttls;

    MailProvider(String host, int port, boolean auth, boolean ttls) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.ttls = ttls;
    }
}

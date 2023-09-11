package com.pigeon.post.models;
public enum MailProvider {
    GMAIL("smtp.gmail.com",587,true,true),
    HOTMAIL("smtp.office365.com",587,true,false),
    MICROSOFT("smtp.office365.com",587,true,false),
    YAHOO("smtp.mail.yahoo.com",465,true,true);
    public final String host;
    public final int port;
    public final boolean auth;
    public final boolean ttls;
//    private static final Map<String, MailSenderImpl> BY_LABEL = new HashMap<>();
//    static {
//        for (MailSenderImpl e:values()){
//            BY_LABEL.put(e.host,e);
//
//        }
//    }
    MailProvider(String host, int port, boolean auth, boolean ttls) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.ttls = ttls;
    }
}

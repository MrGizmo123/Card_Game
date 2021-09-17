package Engine.Networking;

import java.net.InetAddress;

public class Message {

    private String source;
    private String msg;

    public Message(String source, String msg) {
        this.source = source;
        this.msg = msg;
    }

    public String getSource() {
        return source;
    }

    public String getMsg() {
        return msg;
    }
}

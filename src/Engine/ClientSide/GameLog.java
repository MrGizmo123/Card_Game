package Engine.ClientSide;

public class GameLog {

    StringBuilder log;

    public GameLog()
    {
        log = new StringBuilder();
    }

    public void append(String s)
    {
        log.append(s + "\\n");
        System.out.println(s);
    }

}

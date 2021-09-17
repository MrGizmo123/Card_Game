package utils;

public class TimeMeasurer {

    private static long lastMillis;

    public static void init()
    {
        lastMillis = System.currentTimeMillis();
    }

    public static void measureFromLast(String note)
    {
        long currentMillis = System.currentTimeMillis();
        long elapsedMillis = currentMillis - lastMillis;

        System.out.println("Elapsed time: " + elapsedMillis + "ms, Note: " + note);

        lastMillis = System.currentTimeMillis();
    }

}

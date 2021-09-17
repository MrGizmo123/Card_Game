package utils;

import Engine.ClientSide.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <T extends GameObject> List<GameObject> convertToGameObjects(List<T> input)
    {
        List<GameObject> result = new ArrayList<>();

        for(T element : input)
        {
            GameObject converted = (GameObject) element;
            result.add(converted);
        }

        return result;
    }

    public static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.isEmpty())
        {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            //System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

}

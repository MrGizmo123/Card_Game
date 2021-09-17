package Engine.ClientSide;

import PachTinDon.App_532;

import utils.TimeMeasurer;


public class Main {


    public static void main(String[] args) {

        App_532.init();

        TimeMeasurer.init();

        while(true)
        {
            App_532.update();
        }
    }

}

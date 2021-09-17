package PachTinDon;

import Engine.Cards.Suite;
import PachTinDon.App_532;

import javax.swing.*;
import java.awt.*;

public class BottomControlPanel extends JPanel {

    private static final String spadesSymbol = "♠";
    private static final String clubsSymbol = "♣";
    private static final String diamondsSymbol = "♦";
    private static final String heartsSymbol = "♥";

    private JButton place;
    private JLabel requiredHaat;
    private JLabel currentHaat;
    private JLabel hukumLabel;
    private JLabel hukumSymbol;

    public BottomControlPanel()
    {
        super();

        place = new JButton("Place");
        requiredHaat = new JLabel("required haat : 0");
        currentHaat = new JLabel("current haat: 0");
        hukumLabel = new JLabel("Hukum: ");
        hukumSymbol = new JLabel();

        hukumSymbol.setFont(new Font(hukumLabel.getFont().getName(), Font.PLAIN, 50));

        place.setEnabled(false); //button is greyed out until your turn

        place.addActionListener(e -> App_532.uiHandler.cardPlaced());   // tell the interface that a card has been placed, and then disable itself

        this.add(place);
        this.add(requiredHaat);
        this.add(currentHaat);
        this.add(hukumLabel);
        this.add(hukumSymbol);

    }

    public void setRequiredHaat(int required)
    {
        requiredHaat.setText("required haat: " + required);
    }

    public void setCurrentHaat(int current)
    {
        currentHaat.setText("current haat:  " + current);
    }

    public void setHukumLabel(Engine.Cards.Suite hukum)
    {
        hukumLabel.setText("Hukum: " + hukum.toString().toLowerCase());
        if(hukum == Engine.Cards.Suite.CLUBS)
        {
            hukumSymbol.setForeground(Color.BLACK);
            hukumSymbol.setText(clubsSymbol);
        }
        else if(hukum == Engine.Cards.Suite.SPADES)
        {
            hukumSymbol.setForeground(Color.BLACK);
            hukumSymbol.setText(spadesSymbol);
        }
        else if(hukum == Suite.HEARTS)
        {
            hukumSymbol.setForeground(Color.RED);
            hukumSymbol.setText(heartsSymbol);
        }
        else
        {
            hukumSymbol.setForeground(Color.RED);
            hukumSymbol.setText(diamondsSymbol);
        }
    }

    public void enablePlaceButton()
    {
        place.setEnabled(true);
    }

    public void disablePlaceButton()
    {
        place.setEnabled(false);
    }
}

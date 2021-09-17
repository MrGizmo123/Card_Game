package Engine.ServerSide;

import Engine.Cards.Card;
import Engine.Cards.CardType;
import Engine.Cards.Deck;
import Engine.Cards.Suite;
import Engine.Networking.Server;

public class ServerSideGame {

    private static final int playerRequirement = 3;

    public static Engine.Cards.Suite hukum;

    private static Engine.Cards.Deck mainDeck;

    private static Server server;

    private static Engine.Cards.Deck placedCards;

    private static int player1Index;

    public static void main(String args[]) throws InterruptedException {
        server = new Server();

        new Thread(server).start();

        player1Index = 0;  //the first to join will be player 1
    }

    public static void playerEntered(int noOfPlayers)
    {
        if(noOfPlayers == playerRequirement)
        {
            System.out.println("Server locked");
            server.lockServer();

            try {
                Thread.sleep(1000); // wait for everything to connect properly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("starting game...");

            server.sendMessage("gameStarted=1");

            dealOutFirstFiveCards();

            sendRequiredHaats();

            startRound1();
        }
    }

    public static void hukumChosen(Engine.Cards.Suite h)
    {
        hukum = h;

        dealOutSecondFiveCards();
    }

    public static void sendRequiredHaats()
    {
        server.sendMessageByIndex("requiredHaat=5", (0 + player1Index) % playerRequirement);
        server.sendMessageByIndex("requiredHaat=3", (1 + player1Index) % playerRequirement);
        server.sendMessageByIndex("requiredHaat=2", (2 + player1Index) % playerRequirement);
    }

    public static void dealOutFirstFiveCards()
    {
        mainDeck = new Engine.Cards.FullDeck();

        mainDeck.removeCardsByNumber(n -> n<6);
        mainDeck.removeCards(s -> (s == Engine.Cards.Suite.CLUBS || s == Engine.Cards.Suite.DIAMONDS), n -> n==6);

        System.out.println("Main deck" + mainDeck.toString());
        System.out.println("no of cards in main deck: " + mainDeck.getCards().size());

        Engine.Cards.Deck d5 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());
        Engine.Cards.Deck d3 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());
        Engine.Cards.Deck d2 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());

        server.sendMessageByIndex("addCards=" + d5.toString(), 0);
        server.sendMessageByIndex("addCards=" + d3.toString(), 1);
        server.sendMessageByIndex("addCards=" + d2.toString(), 2);
    }

    public static void dealOutSecondFiveCards()
    {
        Engine.Cards.Deck d5 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());
        Engine.Cards.Deck d3 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());
        Engine.Cards.Deck d2 = mainDeck.giveRandom(5);
        System.out.println(mainDeck.getCards().size());

        server.sendMessageByIndex("addCards=" + d5.toString(), 0);
        server.sendMessageByIndex("addCards=" + d3.toString(), 1);
        server.sendMessageByIndex("addCards=" + d2.toString(), 2);
    }

    public static void startRound1()
    {
        placedCards = new Engine.Cards.Deck();

        String player1Name = server.getClientNameByIndex(player1Index);
        server.sendMessage("whoseTurn=" + player1Name);
    }

    public static void cardPlaced(int playerIndex, String cardName)
    {
        placedCards.addCard(new Card(CardType.valueOf(cardName)));
        server.sendMessage("placedCards=" + placedCards.toString());
        server.sendMessage("alert=" + server.getClientNameByIndex(playerIndex) + " has placed a card");

        if(playerIndex == ((player1Index + 2) % playerRequirement))
        {
            checkHaat();
            placedCards = new Deck();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            server.sendMessage("placedCards=" + placedCards.toString());
        }
        else
        {
            whoseTurn((playerIndex+1) % playerRequirement);
        }

    }

    private static void whoseTurn(int playerIndex)
    {
        String playerName = server.getClientNameByIndex(playerIndex);

        server.sendMessage("whoseTurn=" + playerName);
    }

    public static void checkHaat()
    {
        Engine.Cards.Suite s1 = placedCards.getCards().get(0).getType().getSuite();
        Engine.Cards.Suite s2 = placedCards.getCards().get(1).getType().getSuite();
        Suite s3 = placedCards.getCards().get(2).getType().getSuite();

        int n1 = placedCards.getCards().get(0).getType().getNumber();
        int n2 = placedCards.getCards().get(1).getType().getNumber();
        int n3 = placedCards.getCards().get(2).getType().getNumber();

        if(s1 != hukum && s2 != hukum && s3 != hukum)
        {
           if(s1 == s2 && s1 == s3)
           {
               if(n1 > n2 && n1 > n3)
               {
                   player1Wins();
               }
               else if(n2 > n1 && n2 > n3)
               {
                   player2Wins();
               }
               else if(n3 > n2 && n3 > n1)
               {
                   player3Wins();
               }
           }
           else if(s1 == s2 && s1 != s3)
           {
               if(n1 > n2)
               {
                   player1Wins();
               }
               else
               {
                   player2Wins();
               }
           }
           else if(s1 != s2 && s1 == s3)
           {
               if(n1 > n3)
               {
                   player1Wins();
               }
               else
               {
                   player3Wins();
               }
           }
           else
           {
               player1Wins();
           }
        }
        else
        {
            if(s1 == hukum && s2 != hukum && s3 != hukum)
            {
                player1Wins();
            }
            else if(s1 != hukum && s2 == hukum && s3 != hukum)
            {
                player2Wins();
            }
            else if(s1 != hukum && s2 != hukum && s3 == hukum)
            {
                player3Wins();
            }
            else if(s1 == hukum && s2 == hukum && s3 != hukum)
            {
                if(n1 > n2)
                {
                    player1Wins();
                }
                else
                {
                    player2Wins();
                }
            }
            else if(s1 == hukum && s2 != hukum && s3 == hukum)
            {
                if(n1 > n3)
                {
                    player1Wins();
                }
                else
                {
                    player3Wins();
                }
            }
            else if(s1 != hukum && s2 == hukum && s3 == hukum)
            {
                if(n2 > n3)
                {
                    player2Wins();
                }
                else
                {
                    player3Wins();
                }
            }
            else
            {
                if(n1 > n2 && n1 > n3)
                {
                    player1Wins();
                }
                else if(n2 > n1 && n2 > n3)
                {
                    player2Wins();
                }
                else if(n3 > n2 && n3 > n1)
                {
                    player3Wins();
                }
            }
        }
    }

    public static void player1Wins()
    {
        server.sendMessage("alert=" + server.getClientNameByIndex(player1Index) + " won this haat");
        server.sendMessageByIndex("haatMade=1", player1Index);

        whoseTurn(player1Index);
    }

    public static void player2Wins()
    {
        int player2Index = (player1Index + 1) % playerRequirement;

        server.sendMessage("alert=" + server.getClientNameByIndex(player2Index) + " won this haat");
        server.sendMessageByIndex("haatMade=1", player2Index);

        whoseTurn(player2Index); //start next turn

        player1Index = player2Index;  // make winner player 1
    }

    public static void player3Wins()
    {
        int player3Index = (player1Index + 2) % playerRequirement;

        server.sendMessage("alert=" + server.getClientNameByIndex(player3Index) + " won this haat");
        server.sendMessageByIndex("haatMade=1", player3Index);

        whoseTurn(player3Index); //start next turn

        player1Index = player3Index;  // make the winner player 1
    }

}

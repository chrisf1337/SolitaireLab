/**
 * A Card is an object that represents one of the 52 cards in a standard playing
 * card deck. A Card has a rank and suit that uniquely identifies it, and each 
 * Card may be turned face up or down. Each card is also associated with a gif
 * graphic in the card directory that serves as its representation in the 
 * Solitaire game.
 * @author Christopher Fu
 * @version 2013-03-04
 */
public class Card 
{
    public int rank;
    public String suit;
    public boolean isFaceUp;
    
    /**
     * Method: Card
     * Usage:  Card c = new Card(int, String)
     * *************************************************************************
     * Creates a new Card with the given rank and suit. Aces are denoted by 1, 
     * 2s by 2, etc. Jacks, queens, and kings are denoted by 11, 12, and 13, 
     * respectively. Clubs are denoted by "c", diamonds by "d", hearts by "h", 
     * and spades by "s". Each Card is by default face down.
     * @param rank the rank of the Card
     * @param suit the suit of the Card
     */
    public Card(int rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }
    
    /**
     * Method: getRank
     * Usage:  int card.getRank()
     * *************************************************************************
     * Returns the rank of the Card. Aces are denoted by 1, 2s by 2, etc. Jacks,
     * queens, and kings are denoted by 11, 12, and 13, respectively.
     * @return the rank of the Card
     */
    public int getRank() 
    {
        return rank;
    }

    /**
     * Method: getSuit
     * Usage:  String card.getSuit()
     * *************************************************************************
     * Returns the suit of the Card. Clubs are denoted by "c", diamonds by "d",
     * hearts by "h", and spades by "s".
     * @return the suit of the card
     */
    public String getSuit() 
    {
        return suit;
    }

    /**
     * Method: isFaceUp
     * Usage:  boolean card.isFaceUp()
     * *************************************************************************
     * @return true of the Card is face up; otherwise, false
     */
    public boolean isFaceUp() 
    {
        return isFaceUp;
    }
    
    /**
     * Method: isRed
     * Usage:  boolean card.isRed()
     * *************************************************************************
     * @return true if the suit of the Card is diamonds or a hearts; otherwise, 
     * false
     */
    public boolean isRed()
    {
        return suit == "d" || suit == "h";
    }
    
    /**
     * Method: turnUp
     * Usage:  card.turnUp()
     * *************************************************************************
     * Turns the Card face up by setting isFaceUp to true.
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * Method: turnDown
     * Usage:  card.turnDown()
     * *************************************************************************
     * Turns the Card face down by setting isFaceUp to false.
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    /**
     * Method: getFileName()
     * Usage:  String card.getFileName()
     * *************************************************************************
     * Gets the filename of the Card's graphic in the card directory. The format
     * of the filename of the Card is as follows:
     * cards/[rank][suit].gif
     * 
     * Rank is a for aces, t for 10s, and j, q, and k for jacks, queens, and
     * kings. 
     * 
     * Suit follows the formate returned by getSuit.
     * 
     * @return the filename of the Card's image
     */
    public String getFileName()
    {
        if(!isFaceUp)
            return "cards/back.gif";
        else if(rank == 1) // ace
            return "cards/a" + suit + ".gif";
        else if(rank == 10) // 10
            return "cards/t" + suit + ".gif";
        else if(rank == 11) // jack
            return "cards/j" + suit + ".gif";
        else if(rank == 12) // queen
            return "cards/q" + suit + ".gif";
        else if(rank == 13) // king
            return "cards/k" + suit + ".gif";
        else // all numerical cards
            return "cards/" + rank + suit + ".gif";
    }
}

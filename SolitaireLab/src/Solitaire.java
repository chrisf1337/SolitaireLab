import java.util.*;

public class Solitaire
{
	public static void main(String[] args)
	{
	    new Solitaire(true);
	}

	private Stack<Card> stock;
	private Stack<Card> waste;
	private Stack<Card>[] foundations;
	private Stack<Card>[] piles;
	private SolitaireDisplay display;
	
	/**
	 * Creates a 
	 */
	public Solitaire(boolean debug)
	{
		foundations = new Stack[4];
		for(int i = 0; i < foundations.length; i++)
		    foundations[i] = new Stack<Card>();
		
		piles = new Stack[7];
		for(int i = 0; i < piles.length; i++)
		    piles[i] = new Stack<Card>();
		
		stock = new Stack<Card>();
		waste = new Stack<Card>();
		createStock();
		deal();

		display = new SolitaireDisplay(this);
	}

	//returns the card on top of the stock,
	//or null if the stock is empty
	public Card getStockCard()
	{
	    if(stock.isEmpty())
	        return null;
	    else
	        return stock.peek();
	}

	//returns the card on top of the waste,
	//or null if the waste is empty
	public Card getWasteCard()
	{
	    if(waste.isEmpty())
	        return null;
	    else
	        return waste.peek();
	}

	//precondition:  0 <= index < 4
	//postcondition: returns the card on top of the given
	//               foundation, or null if the foundation
	//               is empty
	public Card getFoundationCard(int index)
	{
	    if(foundations[index].isEmpty())
	        return null;
	    else
	        return foundations[index].peek();
	}

	//precondition:  0 <= index < 7
	//postcondition: returns a reference to the given pile
	public Stack<Card> getPile(int index)
	{
	    return piles[index];
	}

	private void createStock()
	{
	    ArrayList<Card> cards = new ArrayList<Card>();
	    for(int i = 1; i <= 13; i++)
	    {
	        cards.add(new Card(i, "c"));
	        cards.add(new Card(i, "d"));
	        cards.add(new Card(i, "h"));
	        cards.add(new Card(i, "s"));
	    }
	    
	    while(cards.size() > 0)
	    {
	        stock.push(cards.remove((int) (Math.random() * cards.size())));
	    }
	}
	
	private void deal()
	{
	    for(int i = 0; i < piles.length; i++)
	    {
	        for(int j = 0; j < i + 1; j++)
	            piles[i].push(stock.pop());
	        piles[i].peek().turnUp();
	    }
	}
	
	private void dealThreeCards()
	{
	    for(int i = 0; i < 3 && !stock.isEmpty(); i++)
	    {
	        Card c = stock.pop();
	        c.turnUp();
	        waste.push(c);
	    }    
	}
	
	private void resetStock()
	{
	    while(!waste.isEmpty())
	    {
	        Card c = waste.pop();
	        c.turnDown();
	        stock.push(c);
	    }
	}
	
	//called when the stock is clicked
	public void stockClicked()
	{
	    if(!display.isWasteSelected() || !display.isPileSelected())
	    {
    	    if(!stock.isEmpty())
    	        dealThreeCards();
    	    else
    	    {
    	        resetStock();
    	        display.unselect();
    	    }
    		System.out.println("stock clicked");
	    }
	}

	//called when the waste is clicked
	public void wasteClicked()
	{
	    if(!waste.isEmpty() && !display.isWasteSelected())
	        display.selectWaste();
	    else if(display.isWasteSelected())
	        display.unselect();
		System.out.println("waste clicked");
	}

	//precondition:  0 <= index < 4
	//called when given foundation is clicked
	public void foundationClicked(int index)
	{
	    if(display.isWasteSelected())
	    {
            if(canAddToFoundation(waste.peek(), index))
            {
	            foundations[index].push(waste.pop());
	            display.unselect();
            }
	    }
        else if(display.isPileSelected())
        {
            if(canAddToFoundation(getPile(display.selectedPile()).peek(), index))
            {
                foundations[index].push(getPile(display.selectedPile()).pop());
                display.unselect();
            }
        }
		System.out.println("foundation #" + index + " clicked");
	}

	//precondition:  0 <= index < 7
	//called when given pile is clicked
	public void pileClicked(int index)
	{
	    //FIX ME
	    System.out.println(display.selectedPile());
	    Stack<Card> cards;
	    
	    Stack<Card> pile = getPile(index);
	    
	    // turn face-down cards on top of piles up
	    if(!pile.isEmpty() && !pile.peek().isFaceUp())
	        pile.peek().turnUp();
	    
	    // unselect pile if you click it again
	    else if(display.selectedPile() == index)
	        display.unselect();
	    
	    // the clicked pile is not the selected pile, so we determine if we can
	    // add cards from the selected pile to the clicked pile
	    else if(display.isPileSelected())    
	    {
	        cards = removeFaceUpCards(display.selectedPile());
	        
	        /*if(!cards.isEmpty() && canAddToPile(cards.peek(), index))
	        {
    	        addToPile(cards, index);
    	        display.unselect();
	        }
	        else
	        {
	            addToPile(cards, display.selectedPile());
	            display.selectPile(index);
	        }*/
	        
	        // FIXME
	        if(!cards.isEmpty())
	        {
	            LinkedList<Card> otherCards = new LinkedList<Card>();
	            ListIterator<Card> it = cards.listIterator(cards.size());
	            Card card;
	            while(it.hasPrevious() && !canAddToPile(card = it.previous(), index))
	            {
	                it.remove();
	                otherCards.addLast(card);
	            }
	            addToPile(cards, index);
	            addToPile(otherCards, display.selectedPile());
	            if(!pile.isEmpty())
	                display.selectPile(index);
	        }
	    }
	    // the waste is selected, so we determine if we can add the top waste
	    // card to the clicked pile
	    else if(display.isWasteSelected())
        {
	        if((!pile.isEmpty() && canAddToPile(waste.peek(), index)) || 
                    (pile.isEmpty() && waste.peek().getRank() == 13))
            {
                pile.push(waste.pop());
                display.unselect();
            }
            else
                display.selectPile(index);
        }
	    // if nothing has been selected, select the clicked pile
	    else if(!pile.isEmpty() && pile.peek().isFaceUp())
	        display.selectPile(index);
	    
	    
	    
	    
		System.out.println("pile #" + index + " clicked");
	}
	
	/**
	 * Precondition: 0 <= index <= 7
	 * @return true if the given Card can be added to the top of the given pile
	 * at index; otherwise, false 
	 */
	private boolean canAddToPile(Card card, int index)
	{
	    Stack<Card> pile = getPile(index);
	    if(pile.isEmpty() && card.getRank() == 13)
	        return true;
	    else if(!pile.isEmpty() && pile.peek().isFaceUp())
	        return (pile.peek().getRank() == card.getRank() + 1 && 
	                ((pile.peek().isRed() && !card.isRed()) || 
	                (!pile.peek().isRed() && card.isRed())));
	    else
	        return false;
	}
	
	/** 
	 * Removes all face-up cards on the top of the given pile and returns a 
	 * stack containing these cards.
	 * 
	 * Precondition: 0 <= index < 7
	 * @param index
	 * @return a Stack of face-up Cards on the top of the given pile.
	 */
	private Stack<Card> removeFaceUpCards(int index)
	{
	    Stack<Card> pile = getPile(index);
	    Stack<Card> faceUpCards = new Stack<Card>();
	    ListIterator<Card> it = pile.listIterator(pile.size());
	    Card card;
	    while(it.hasPrevious() && (card = it.previous()).isFaceUp())
	    {
            faceUpCards.push(card);
            it.remove();
	    }
	    for(int i = 0; i < faceUpCards.size(); i++)
	        System.out.println(faceUpCards.get(i).getRank() + faceUpCards.get(i).getSuit());
	    return faceUpCards;
	}
	
	/**
	 * Adds the Cards in cards to the given pile.
	 * 
	 * Precondition: 0 <= index < 7
	 * @param cards
	 * @param index
	 */
	private void addToPile(Stack<Card> cards, int index) 
	{
	    Stack<Card> pile = getPile(index);
	    while(!cards.empty())
	        pile.push(cards.pop());
	}
	
	private void addToPile(Queue<Card> cards, int index)
	{
	    Stack<Card> pile = getPile(index);
	    while(!cards.isEmpty())
	        pile.push(cards.remove());
	}
	
	/**
	 * Precondition: 0 <= index < 4
	 * @param card
	 * @param index
	 * @return
	 */
	private boolean canAddToFoundation(Card card, int index)
	{
	    Card foundationCard = getFoundationCard(index);
	    if(foundationCard == null)
	        return card.getRank() == 1;
	    else 
	        return (foundationCard.getRank() == card.getRank() - 1 && 
	            foundationCard.getSuit().equals(card.getSuit()));
	        
	}
}
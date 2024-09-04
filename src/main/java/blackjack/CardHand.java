package blackjack;

/** A card hand with cards {@link Card} to a card holder {@link CardHolder} */
public class CardHand extends CardContainer {
    /**
     * Method to add a card to this hand
     * 
     * @param card a card
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * Method to get the sum to this cards
     * 
     * @return the sum
     */
    public int getCardSum() {
        return this.cards.stream().mapToInt(card -> card.getFace()).sum();
    }

}

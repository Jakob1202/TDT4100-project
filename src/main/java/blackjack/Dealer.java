package blackjack;

/** A dealer to a {@link Table}*/
public class Dealer extends CardHolder {

    /**
     * Method to check if this dealer can draw a new card in the game
     * 
     * @return {@code true} if this dealer can draw a new card, {@code true}
     *         otherwise
     */
    public boolean canDrawCard() {
        if(super.getCardSum() == 21) {
            return false;
        } else if (super.getCardSum() < 17) {
            return true;
        } else {
            if (super.getAceCount() > 0) {
                int newDealerSum = super.getCardSum() - 10;
                super.setCardSum(newDealerSum);
                super.decreaseAceCount();
                return true;
            } else {
                return false;
            }
        }
    }
}

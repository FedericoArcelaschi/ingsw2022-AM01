package it.polimi.ingsw.server.model.exceptions;

public class CoinException extends Exception{
    private final int expectedCoins;
    private final int availableCoins;

    /**
     * @param expectedCoins coins needed
     * @param availableCoins coins actually in castle
     */
    public CoinException(int expectedCoins, int availableCoins) {
        this.expectedCoins = expectedCoins;
        this.availableCoins = availableCoins;
    }

    @Override
    public final String getMessage() {
        if(expectedCoins == 1)
            return "You had only 0 coins, while 1 coin was needed.";
        return  "You had only " + availableCoins + " coins, while " + expectedCoins + " coins were needed.";
    }

}

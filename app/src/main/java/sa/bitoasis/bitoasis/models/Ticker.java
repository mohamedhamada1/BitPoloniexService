package sa.bitoasis.bitoasis.models;

import android.app.Person;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Ticker {

    private int currencyPairId;
    private BigDecimal lastTradePrice;
    private BigDecimal lowestAsk;
    private BigDecimal highestBid;
    private BigDecimal percentChangeInLast24Hours;
    private BigDecimal baseCurrencyVolumeInLast24Hours;
    private BigDecimal quotaCurrencyVolumeInLast24Hours;
    private int isFrozen;
    private BigDecimal highestTradePriceInLast24Hours;
    private BigDecimal lowestTradePriceInLast24Hours;


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }


        if (!(obj instanceof Ticker)) {
            return false;
        }


        final Ticker other = (Ticker) obj;

        if (this.currencyPairId != other.currencyPairId)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.currencyPairId ;
    }

//&& lastTradePrice.compareTo(other.getLastTradePrice()) == 0 &&lowestAsk.compareTo(other.getLowestAsk())==0
}

package derekzuk.structure.binancespringwebsocketclient.dto;

import java.math.BigDecimal;

public class MedianDTO {
    private final String symbol;
    private final Double seen;
    private final BigDecimal median;
    private final BigDecimal mostRecentPrice;

    public MedianDTO(String symbol, Double seen, BigDecimal median, BigDecimal mostRecentPrice) {
        this.symbol = symbol;
        this.seen = seen;
        this.median = median;
        this.mostRecentPrice = mostRecentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getSeen() {
        return seen;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public BigDecimal getMostRecentPrice() {
        return mostRecentPrice;
    }
}
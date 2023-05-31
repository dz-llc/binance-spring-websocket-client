package derekzuk.structure.mediancalculator.dto;

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
}
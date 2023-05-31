package derekzuk.structure.mediancalculator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Median {
    public Double seen = 0d;
    public List<BigDecimal> prices = new ArrayList<>();
    public BigDecimal medianPrice;
    public BigDecimal lastSeenPrice;
}

package derekzuk.structure.mediancalculator.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class MedianService {

    public Map<String, Median> medianPrice = new HashMap<>();

    public void calculateMedian(String message) {
        // populate statistics
        JSONObject jsonObject = new JSONObject(message);
        String price = jsonObject.getString("p");
        BigDecimal priceBigDecimal = new BigDecimal(price);

        String symbol = jsonObject.getString("s").toLowerCase();
        Median m = medianPrice.get(symbol);
        m.seen++;
        m.prices.add(priceBigDecimal);

        if (m.seen % 2 == 0) {
            // even
            Double mid = (m.seen / 2);
            m.medianPrice = m.prices.get(mid.intValue());
        } else {
            Double mid = (m.seen / 2);
            System.out.println("m.seen: " + m.seen);
            System.out.println("mid:" + mid);
            m.medianPrice = m.prices.get(mid.intValue());
            System.out.println("m.medianPrice: " + m.medianPrice);
        }

    }

    public void populatePrice(String symbol) {
        medianPrice.put(symbol.toLowerCase(), new Median());
    }
}


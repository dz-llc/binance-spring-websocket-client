package derekzuk.structure.binancespringwebsocketclient.service;

import derekzuk.structure.binancespringwebsocketclient.datastore.MedianDatastore;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MedianService {

    public void calculateMedian(String message) {
        // populate statistics
        JSONObject jsonObject = new JSONObject(message);
        String price = jsonObject.getString("p");
        BigDecimal priceBigDecimal = new BigDecimal(price);

        String symbol = jsonObject.getString("s").toLowerCase();
        Median m = MedianDatastore.medianPrice.get(symbol);
        m.seen++;
        m.prices.add(priceBigDecimal);

        if (m.seen % 2 == 0) {
            // even
            Double mid = (m.seen / 2);
            m.medianPrice = m.prices.get(mid.intValue());
        } else {
            Double mid = (m.seen / 2);
            m.medianPrice = m.prices.get(mid.intValue());
        }

    }

    public void populatePrice(String symbol) {
        MedianDatastore.medianPrice.put(symbol.toLowerCase(), new Median());
    }
}


package derekzuk.structure.binancespringwebsocketclient.controller;

import derekzuk.structure.binancespringwebsocketclient.datastore.MedianDatastore;
import derekzuk.structure.binancespringwebsocketclient.dto.MedianDTO;
import derekzuk.structure.binancespringwebsocketclient.service.Median;
import derekzuk.structure.binancespringwebsocketclient.service.MedianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RestController
public class MedianController {

    @Autowired
    MedianService medianService;

    @GetMapping("/symbols")
    public ResponseEntity<Set<String>> symbols() {
        Set<String> symbols = MedianDatastore.medianPrice.keySet();
        return new ResponseEntity<>(symbols, HttpStatus.OK);
    }

    @GetMapping("/seenSymbols")
    public ResponseEntity<Set<String>> seenSymbols() {
        Set<String> res = new HashSet<>();
        Set<String> symbols = MedianDatastore.medianPrice.keySet();
        for (String symbol : symbols) {
            if (!MedianDatastore.medianPrice.get(symbol).seen.equals(0.0)) {
                res.add(symbol);
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/symbols/{symbol}")
    public ResponseEntity<MedianDTO> symbols(@PathVariable String symbol) {
        if (MedianDatastore.medianPrice.containsKey(symbol.toLowerCase())) {
            Median median = MedianDatastore.medianPrice.get(symbol.toLowerCase());
            MedianDTO medianDTO;
            if (median.seen.equals(0.0)) {
                medianDTO = new MedianDTO(symbol.toLowerCase(), median.seen, new BigDecimal(0), new BigDecimal(0));
            } else {
                // This pair has been seen at least once
                medianDTO = new MedianDTO(symbol.toLowerCase(), median.seen, median.medianPrice, median.lastSeenPrice);
            }
            return new ResponseEntity<>(medianDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
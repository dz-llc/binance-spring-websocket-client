package derekzuk.structure.mediancalculator.controller;

import derekzuk.structure.mediancalculator.dto.MedianDTO;
import derekzuk.structure.mediancalculator.service.Median;
import derekzuk.structure.mediancalculator.service.MedianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class MedianController {

    @Autowired
    MedianService medianService;

    @GetMapping("/symbols")
    public ResponseEntity<Set<String>> symbols() {
        Set<String> symbols = medianService.medianPrice.keySet();
        return new ResponseEntity<>(symbols, HttpStatus.OK);
    }

    @GetMapping("/symbols/{symbol}")
    public ResponseEntity<MedianDTO> symbols(@PathVariable String symbol) {
        if (medianService.medianPrice.containsKey(symbol.toLowerCase())) {
            Median median = medianService.medianPrice.get(symbol.toLowerCase());
            MedianDTO medianDTO = new MedianDTO(symbol.toLowerCase(), median.seen, median.medianPrice, median.lastSeenPrice);
            return new ResponseEntity<>(medianDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
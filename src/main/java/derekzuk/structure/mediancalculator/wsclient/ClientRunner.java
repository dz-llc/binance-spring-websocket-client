package derekzuk.structure.mediancalculator.wsclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BinanceClient.main(new String[0]);
    }

}

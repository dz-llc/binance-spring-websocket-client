package derekzuk.structure.mediancalculator.wsclient;

import derekzuk.structure.mediancalculator.service.MedianService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class BinanceClient extends WebSocketClient {

    MedianService medianService;

    public BinanceClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public BinanceClient(URI serverURI) {
        super(serverURI);
    }

    public BinanceClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public BinanceClient(URI serverURI, MedianService medianService) {
        super(serverURI);
        this.medianService = medianService;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("opened connection");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);

        medianService.calculateMedian(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws URISyntaxException {
        MedianService medianService = new MedianService();

        String exchangeInfo = null;
        try {
            exchangeInfo = getHTML("https://api.binance.com/api/v3/exchangeInfo");
        } catch (Exception e) {
            System.out.println("e: " + e);
        }

        List<String> symbolsList = new ArrayList<>();

        // Populate symbolsList
        JSONObject jsonObject = new JSONObject(exchangeInfo);
        JSONArray symbols = jsonObject.getJSONArray("symbols");
        System.out.println("symbols.length: " + symbols.length());
        for (int i = 0; i < symbols.length(); i++) {
            String jsonString = symbols.get(i).toString();
            JSONObject symbolObject = new JSONObject(jsonString);
            String symbol = symbolObject.getString("symbol");
            symbolsList.add(symbol);

            medianService.populatePrice(symbol.toLowerCase());
        }

        // create combined stream url
        List<String> combinedStreamURLs = new ArrayList<>();
        String baseURL = "wss://stream.binance.com:9443/ws";
        StringBuilder sb = new StringBuilder(baseURL);
        int counter = 0;
        for (String symbol : symbolsList) {
            if (counter < 1100) {
                sb.append("/" + symbol.toLowerCase() + "@trade");
                counter++;
            } else {
                System.out.println("sb.toString(): " + sb);
                combinedStreamURLs.add(sb.toString());
                sb = new StringBuilder(baseURL);
                counter = 0;
            }
        }

        for (String streamURL : combinedStreamURLs) {
            BinanceClient c = new BinanceClient(new URI(streamURL), medianService);
            c.connect();
        }
    }

}
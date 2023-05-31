package derekzuk.structure.binancespringwebsocketclient.datastore;

import derekzuk.structure.binancespringwebsocketclient.service.Median;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MedianDatastore {

    public static ConcurrentMap<String, Median> medianPrice = new ConcurrentHashMap<>();

}

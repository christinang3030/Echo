package echo;

import java.net.Socket;
import java.util.HashMap;

public class CacheHandler extends ProxyHandler {

    static HashMap<String, String> cache = new HashMap<>();

    public CacheHandler(Socket s) { super(s); }
    public CacheHandler() { super(); }

    protected synchronized String search(String request) {
        String response = cache.get(request);
        notify();
        return response;
    }

    protected synchronized void update(String request, String response) {
        cache.put(request, response);
        notify();
    }

    @Override
    protected String response(String request) throws Exception {
        String cachedResp = search(request);
        System.out.println("----- Searching cache for request");
        if (cachedResp != null) {
            System.out.println("----- Request FOUND");
            return cachedResp;
        }

        System.out.println("----- Request NOT found");
        peer.send(request);
        String response = peer.receive();
        update(request, response);
        return response;
    }
}

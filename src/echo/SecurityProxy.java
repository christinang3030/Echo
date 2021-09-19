package echo;

import java.net.Socket;
import java.util.HashMap;

public class SecurityProxy extends ProxyHandler {

    static HashMap<String, String> userTable = new HashMap<>();
    boolean terminated = true;

    public SecurityProxy(Socket s) { super(s); }
    public SecurityProxy() { super(); }

    @Override
    protected String response(String request) throws Exception {
        if (!terminated) { // login verified, all subsequent requests are forwarded to peer
            return super.response(request);
        } else { // session has been previously terminated
            String[] rqst = request.split(" ");
            String response = "";
            String errorMsg = "LOGIN ERROR: To add a user and password, type 'new', followed by a unique user name and password. "
                    + "To login, type 'login', followed by your username and password.";
            if (rqst.length != 3) {
                return errorMsg;
            }
            String cmmd = rqst[0];
            String user = rqst[1];
            String pass = rqst[2];
            if (cmmd.equalsIgnoreCase("new")) {
                userTable.put(user, pass);
                response = "New user and password has been successfully added.";
            } else if (cmmd.equalsIgnoreCase("login")) {
                if (userTable.get(user).equals(pass)) {
                    terminated = false; // login verified
                    response = "Login has been verified.";
                } else {
                    response = "Login has failed.";
                }
            } else {
                response = errorMsg;
            }
            return response;
        }
    }
}

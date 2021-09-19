package casino;

import echo.ProxyHandler;

public class CasinoTracker extends ProxyHandler {
    private double wins = 0, losses = 0, winPercentage = 0;
    public static double maxWinPercentage = 0;
    protected String response(String msg) throws Exception {
        String response = "";
        if (msg.equalsIgnoreCase("stats")) {
            response = "win percentage = " + 100 * winPercentage + "%";
        } else if (msg.equalsIgnoreCase("max")) {
            synchronized(this) {
                response = "max win percentage = " + 100 * maxWinPercentage + "%";
            }
        } else {
            response = super.response(msg);
            if (response.matches(".*you win!")) wins++;
            else if (response.matches(".*you lose!")) losses++;
            winPercentage = (wins + losses > 0)? wins / (wins + losses): 0;
            synchronized(this) {
                maxWinPercentage = Math.max(maxWinPercentage, winPercentage);
            }
        }
        return response;
    }
}
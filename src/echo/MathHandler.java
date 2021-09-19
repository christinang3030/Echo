package echo;

import java.net.Socket;

public class MathHandler extends RequestHandler {

    public MathHandler(Socket sock) { super(sock); }

    public MathHandler() { super(); }

    protected String response(String request) throws Exception {
        String result = "";
        String[] command = request.split(" ");
        if (command.length < 3) { return "Insufficient number of arguments"; }
        String operator = command[0];

        double[] numbers = new double[command.length-1];
        for(int i = 1; i < command.length; i++) {
            numbers[i-1] = Double.parseDouble(command[i]);
        }

        if (operator.equalsIgnoreCase("add")) {
            double answer = 0;
            for(double num : numbers) {
                answer += num;
            }
            result = "" + answer;
        } else if (operator.equalsIgnoreCase("mul")) {
            double answer = 1;
            for(double num : numbers) {
                answer *= num;
            }
            result = "" + answer;
        } else if (operator.equalsIgnoreCase("sub")) {
            double answer = numbers[0];
            for(int i = 1; i < numbers.length; i++) {
                answer -= numbers[i];
            }
            result = "" + answer;
        } else if (operator.equalsIgnoreCase("div")) {
            double answer = numbers[0];
            for(int i = 1; i < numbers.length; i++) {
                answer /= numbers[i];
            }
            result = "" + answer;
        } else {
            result = "Unrecognized operator: " + operator;
        }
        return result;
    }

}

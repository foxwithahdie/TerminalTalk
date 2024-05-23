import java.util.*;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client();

        TerminalParser.terminalLoop(client, (client1, input) -> {
            int handledInput = TerminalParser.handleInput(client1, input.nextLine());
            System.out.println(handledInput);
            return handledInput != 1;
        });
    }
}

import java.util.Scanner;

public class TerminalParser {
    public static void terminalLoop(Client client, ClientTerminalLoop clientTerminalLoop) {
        Scanner input = new Scanner(System.in);
        System.out.print("> ");
        while (input.hasNextLine()) {
            boolean terminalLoop = clientTerminalLoop.clientTerminalLoop(client, input);

            while (terminalLoop && input.hasNext()) {
                if (client.getClientSocket() != null && client.getClientSocket().isConnected()) {
                    client.getClientSocket().println(input.nextLine());
                    System.out.println();
                    System.out.println(client.getClientSocket().readLine());
                }
                terminalLoop = clientTerminalLoop.clientTerminalLoop(client, input);

                System.out.print("> you: ");
            }
            if (!terminalLoop) {
                System.out.print("> ");
            }
        }
    }

    public static void terminalLoop(Client client, Server server, ServerTerminalLoop serverTerminalLoop) {
        Scanner input = new Scanner(System.in);
    }

    public static int handleInput(Client client, String inputString) {
        if (inputString.contains("\\connect")) {
            int portNumber = -1;
            String alias = "";

            for (int i = "\\connect".length() + 1; i < inputString.length(); i++) {
                if (Character.isDigit(inputString.charAt(i))) {
                    String portString = Character.toString(inputString.charAt(i));
                    int portNumberIndex = i + 1;
                    while (portNumberIndex < inputString.length() && Character.isDigit(inputString.charAt(portNumberIndex))) {
                        portString += Character.toString(inputString.charAt(portNumberIndex));
                        portNumberIndex++;
                    }
                    portNumber = Integer.parseInt(portString);
                    i = portNumberIndex - 1;

                }
                else if (inputString.substring(i).contains("alias=")) {
                    alias = inputString.substring(i + "alias=".length() + 1);
                    i = inputString.length();
                }
            }

            if (!alias.isEmpty()) {
                alias = alias.trim();
                client.changeAlias(alias);
            } else {
                client.changeAlias("Anon");
            }

            if (portNumber > 0) {
                client.connect(portNumber);
            } else {
                System.out.println("> System: Bad port!");
            }

            return 0;

        } else if (inputString.contains("\\disconnect")) {
            client.disconnect();
            return 1;
        }
        return -1;
    }
}

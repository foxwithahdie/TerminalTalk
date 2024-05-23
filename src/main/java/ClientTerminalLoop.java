import java.util.Scanner;

@FunctionalInterface
public interface ClientTerminalLoop {
    public boolean clientTerminalLoop(Client client, Scanner input);
}

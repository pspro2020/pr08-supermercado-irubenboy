import classes.Client;
import classes.Supermarket;

public class Main {
    private static final int MAX_CLIENTS = 50;

    public static void main(String[] args) {
        Supermarket ruizGalan = new Supermarket(4);
        for (int i = 0; i < MAX_CLIENTS; i++) {
            new Thread(new Client(ruizGalan), "Client-" + i).start();
        }
    }
}

package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable{

    private final Supermarket supermarket; // Caja que ocupará el cliente
    private final DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");

    /* Constructor */
    public Client(Supermarket supermarket){
        this.supermarket = supermarket;
    }


    @Override
    public void run() {

        try {
            // El cliente espera la cola para poder entrar a comprar.
            waitToCashRegister();
            // Una vez que entra realiza la compra o al menos lo intenta
            supermarket.buy();
        } catch (InterruptedException e) {
            return;
        }
        // Si consigue comprar, paga y se va
        payAndGo();
    }

    private void payAndGo() {
        System.out.printf("%s => %s have paid and gone the supermarket\n",
                LocalDateTime.now().format(f), Thread.currentThread().getName());
    }

    private void waitToCashRegister() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        System.out.printf("%s => %s is waiting to buy\n", LocalDateTime.now().format(f),
                Thread.currentThread().getName());
    }
}

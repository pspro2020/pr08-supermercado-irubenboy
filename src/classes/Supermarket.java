package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Supermarket {

    private final int MAX_CASH_REGISTER; // Número máximo de cajas que tiene el supermercado
    private final boolean[] availables; // Array que contiene la disponibilidad de cada caja
    private final DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Semaphore semaphore;

    /* Constructor */
    public Supermarket(int max_cash_register) {
        this.MAX_CASH_REGISTER = max_cash_register;
        availables = new boolean[MAX_CASH_REGISTER];
        semaphore = new Semaphore(MAX_CASH_REGISTER, true);
        for (int i = 0; i < MAX_CASH_REGISTER; i++) {
            availables[i] = true; // Al crearse, todas las cajas están disponibles
        }
    }

    //Está función controla cuando un cliente quiere entrar a una caja
    public void buy() throws InterruptedException {
        System.out.printf("%s => %s wants to go in a cash register\n", LocalDateTime.now().format(f),
                Thread.currentThread().getName()); // Se muestra el mensaje
        semaphore.acquire();
        try {
            // Se selecciona una caja registradora
            int cashRegister = selectCashRegister();
            if(cashRegister > 0){ // Si se ha seleccionada una caja registradora
                purchaseInTheCash(cashRegister); // Comprar en dicha caja registradora
                goOut(cashRegister); // Abandona la caja registradora
            }
        } finally {
            semaphore.release(); // Suelta el semaforo
        }

    }

    private void goOut(int cashRegister) {
        System.out.printf("%s => %s go out and have abandoned the cash registrator #%d\n",
                LocalDateTime.now().format(f), Thread.currentThread().getName(), cashRegister+1);
    }

    private void purchaseInTheCash(int cashRegister) throws InterruptedException {
        System.out.printf("%s => %s is buying in the cash register #%d\n",
                LocalDateTime.now().format(f), Thread.currentThread().getName(), cashRegister+1);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4)+1);
    }

    private int selectCashRegister() {
        for (int i = 0; i < MAX_CASH_REGISTER; i++) { // Se recoge las cajas para saber si hay alguna libre
            if(availables[i]){
                availables[i] = false;
                return i; // La primera que esté libre se selecciona
            }
        }
        return -1; // Si no hay ninguna libre, devuelve -1
    }


}

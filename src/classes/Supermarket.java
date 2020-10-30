package classes;

public class Supermarket {

    private final int MAX_CASH_REGISTER; // Número máximo de cajas que tiene el supermercado
    private final boolean[] availables; // Array que contiene la disponibilidad de cada caja

    /* Constructor */
    public Supermarket(int max_cash_register) {
        this.MAX_CASH_REGISTER = max_cash_register;
        availables = new boolean[MAX_CASH_REGISTER];
        for (int i = 0; i < MAX_CASH_REGISTER; i++) {
            availables[i] = true; // Al crearse, todas las cajas están disponibles
        }
    }

    //Está función controla cuando un cliente quiere entrar a una caja
    public void goIn(){
        System.out.printf("%s wants to go in a cash register", Thread.currentThread().getName()); // Se muestra el mensaje

        // Se selecciona una caja registradora
        int cashRegister = selectCashRegister();
        if(cashRegister > 0){ // Si se ha seleccionada una caja registradora
            buy(cashRegister); // Comprar en dicha caja registradora
            goOut(cashRegister); // Abandona la caja registradora
        }

    }

    private void goOut(int cashRegister) {
        System.out.printf("%s go out and have abandoned the cash registrator #%d", Thread.currentThread().getName(), cashRegister+1);
    }

    private void buy(int cashRegister) {
        System.out.printf("%s is buying in the cash register #%d", Thread.currentThread().getName(), cashRegister+1);
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

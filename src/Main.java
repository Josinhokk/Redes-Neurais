import RedesNeurais.Adaline;
import RedesNeurais.Perceptron;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner e = new Scanner(System.in);
        Perceptron p = new Perceptron();;
        Adaline a = new Adaline();
        int entrada = -1;

        while(entrada != 0){
            System.out.println("====================");
            System.out.println("1 - Perceptron");
            System.out.println("2 - Adaline");
            System.out.println("0 - Sair");
            System.out.println("====================");
            entrada = e.nextInt();
            switch(entrada){
                case 1:
                    p.executar();
                    break;
                case 2:
                    a.executar();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("opção invalida");
                    break;
            }

        }
        System.out.println("Encerrando");
    }
}

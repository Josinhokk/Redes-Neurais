package RedesNeurais;

public class Adaline implements Executor {

    @Override
    public void executar() {
        //aprendizado
        double n = 0.01;

        int nEntradas = 3;

        double w[] = new double[nEntradas];

        int epoca = 0;

        double matriz[][] = {
                {-1, 0, 0, 0},
                {-1, 1, 0, 0},
                {-1, 0, 1, 0},
                {-1, 1, 1, 1}};


        //gerar pesos
        for (int i = 0; i < nEntradas; i++) {
            w[i] = (Math.random() * 2) - 1;
        }
        while (epoca != 1000) {
            //soma ponderada
            for (int i = 0; i < matriz.length; i++) {
                double soma = 0;
                for (int j = 0; j < nEntradas; j++) {
                    soma += w[j] * matriz[i][j];
                }

                double t = matriz[i][3];

                double erro = t - soma;

                for (int e = 0; e < nEntradas; e++) {
                    w[e] = w[e] + n * erro * matriz[i][e];
                }

                System.out.printf("Epoca %03d | [%.0f %.0f %.0f] | T: %.0f | Soma: %6.2f | Erro: %6.2f | W: [%.2f, %.2f, %.2f]\n",
                        epoca, matriz[i][0], matriz[i][1], matriz[i][2], t, soma, erro, w[0], w[1], w[2]);

            }
            epoca++;
            System.out.println("----------");
        }


    }
}

package RedesNeurais;

public class Perceptron implements Executor {
    @Override
    public void executar() {
        //taxa de aprendizado
        double n = 0.6;
        int nEntradas = 3;
        double matriz[][] = {
                {-1, 0, 0, 0},
                {-1, 1, 0, 0},
                {-1, 0, 1, 0},
                {-1, 1, 1, 1}};

        //saida
        int y = 0;
        int epocas = 0;
        boolean treinado = false;

        //pesos
        double w[] = new double[nEntradas];
        for (int i = 0; i < nEntradas; i++) {
            w[i] = (Math.random() * 2) - 1;
        }
        while(epocas != 1000 && treinado == false){
            boolean erroEpoca = false;

            //Soma ponderada
            for (int i = 0; i < matriz.length; i++) {
                double soma = 0;
                for (int j = 0; j < matriz.length - 1; j++) {
                    soma += matriz[i][j] * w[j];
                }

                //função de ativação
                if (soma >= 0) {
                    y = 1;
                }else{
                    y = 0;
                }

                int t = (int) matriz[i][3];
                double erro = t - y;

                if(erro !=0){
                    erroEpoca = true;
                    for(int j = 0; j < nEntradas; j++){
                        w[j] = w[j]+n*erro*matriz[i][j];
                    }
                }
                System.out.printf("Epoca %03d | [%.0f %.0f %.0f] | T: %d | Y: %d | E: %2.0f | W: [%.2f, %.2f, %.2f]\n",
                        epocas, matriz[i][0], matriz[i][1], matriz[i][2], t, y, erro, w[0], w[1], w[2]);

                if (!erroEpoca) {
                    treinado = true;
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Convergiu! Aprendizado finalizado na época: " + epocas);
                }
                epocas++;
                System.out.println("----------"); // Separador de épocas

            }

        }


    }
}

package RedesNeurais;

public class LVQ implements Executor {

    @Override
    public void executar() {

        double matriz[][] =
                {{2, 5},
                        {3, 1}};


        int classeOriginal[] = {0, 1};

        //aprendizado
        double n = 0.01;

        int nNeuronio = 2;
        int nEntradas = 2;

        double w[][] = new double[nNeuronio][nEntradas];

        int classeW[] = {0, 1};


        int epocas = 0;

        //gerar pesos
        //cada linha representa um neuronio
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < nEntradas; j++) {
                w[i][j] = 1 + Math.random() * 4;
            }
        }

        while (epocas < 100000) {
            //distancia Euclidiana -> raiz de (xi - wi)^2 + (xi - wi)^2
            for (int i = 0; i < matriz.length; i++) {
                //variaveis responsaveis por guardar qual é a menor distancia e a classe do neuronio
                double menorDistancia = Double.MAX_VALUE;
                int bmu = -1;

                //aqui vai passar por todos os neuronios
                for (int linhaNeuronio = 0; linhaNeuronio < nNeuronio; linhaNeuronio++) {
                    double somaDistancia = 0;

                    for (int j = 0; j < nEntradas; j++) {
                        double diferenca = matriz[i][j] - w[linhaNeuronio][j];
                        somaDistancia += diferenca * diferenca;
                    }


                    //tirar a raiz
                    double distanciaFinal = Math.sqrt(somaDistancia);

                    if (distanciaFinal < menorDistancia) {
                        menorDistancia = distanciaFinal;
                        bmu = linhaNeuronio;
                    }
                }

                if (classeW[bmu] == classeOriginal[i]) {
                    for (int j = 0; j < nEntradas; j++) {
                        w[bmu][j] = w[bmu][j] + n * (matriz[i][j] - w[bmu][j]);
                    }

                } else {
                    for (int j = 0; j < nEntradas; j++) {
                        w[bmu][j] = w[bmu][j] - n * (matriz[i][j] - w[bmu][j]);
                    }
                }


            }
            //reduzir taxa de aprendizado
            n = n * 0.99;
            epocas++;
        }
        System.out.println("Treinamento finalizado em " + epocas + " épocas.");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Posicionamento Final dos Neurônios (Prototipos):");
        for (int i = 0; i < nNeuronio; i++) {
            System.out.printf("Neurônio da Classe %d | Coordenadas: [%.2f, %.2f]\n",
                    classeW[i], w[i][0], w[i][1]);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("Entradas Originais para comparação:");
        System.out.printf("Alvo Classe 0: [%.2f, %.2f]\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Alvo Classe 1: [%.2f, %.2f]\n", matriz[1][0], matriz[1][1]);

    }
}

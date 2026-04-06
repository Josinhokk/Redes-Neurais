package RedesNeurais;

public class MLP implements Executor {
    @Override
    public void executar() {
        //taxa de aprendizado
        double n = 0.1;

        int epocas = 0;

        double matriz[][] = {
                {-1, 0, 0, 0},
                {-1, 1, 0, 1},
                {-1, 0, 1, 1},
                {-1, 1, 1, 0}};

        int nEntradas = 3;
        int nNeuronios = 3;

        double[][] wOculta = new double[nNeuronios][nEntradas];
        //saida
        double[][] wSaida = new double[1][nNeuronios];

        //gerando pesos da camada oculta
        for (int i = 0; i < nEntradas; i++) {
            for (int j = 0; j < nNeuronios; j++) {
                wOculta[j][i] = (Math.random() * 2) - 1;
            }
        }

        //gerando pesos do w saida
        for (int i = 0; i < nNeuronios; i++) {
            wSaida[0][i] = (Math.random() * 2) - 1;
        }

        while (epocas < 10000) {

            for (int i = 0; i < matriz.length; i++) {

                //armazenar a saida da camada oculta
                double[] yOculta = new double[nNeuronios];
                for (int linha = 0; linha < nNeuronios; linha++) {
                    //zerar a soma para o neuronio atual
                    double soma = 0;

                    for (int j = 0; j < nEntradas; j++) {
                        soma += wOculta[linha][j] * matriz[i][j];
                    }
                    //aplicando a função sigmoide
                    yOculta[linha] = 1.0 / (1.0 + Math.exp(-soma));

                }
                //calculando a "entrada" do proximo neuronio
                double somaSaida = 0;

                for (int j = 0; j < nNeuronios; j++) {
                    somaSaida += yOculta[j] * wSaida[0][j];
                }
                //saida final
                double yFinal = 1.0 / (1.0 + Math.exp(-somaSaida));

                //BACKPROPAGATION agora
                //Calcular o erro de saida
                double t = matriz[i][3];
                double deltaSaida = (t - yFinal) * yFinal * (1 - yFinal);

                //vetor para guardar o erro de cada neu
                double[] deltaOculto = new double[nNeuronios];

                for (int j = 0; j < nNeuronios; j++) {
                    deltaOculto[j] = yOculta[j] * (1 - yOculta[j]) * (deltaSaida * wSaida[0][j]);
                }

                //Atualizar os pesos da Camada de Saída
                // Quem é a entrada da saída? É o yOculta!
                for (int j = 0; j < nNeuronios; j++) {
                    wSaida[0][j] = wSaida[0][j] + n * deltaSaida * yOculta[j];
                }

                //Atualizar os pesos da Camada Oculta
                // Quem é a entrada da oculta? É a matriz original!
                for (int linha = 0; linha < nNeuronios; linha++) {
                    for (int coluna = 0; coluna < nEntradas; coluna++) {
                        wOculta[linha][coluna] = wOculta[linha][coluna] + n * deltaOculto[linha] * matriz[i][coluna];
                    }
                }


            }
            epocas++;
        }
        System.out.println("=== TESTE FINAL MLP (XOR) ===");
        System.out.println("Treinamento concluído em " + epocas + " épocas.");
        System.out.println("---------------------------------------------------------");

        // Passando os dados pela rede treinada (Apenas o Forward Pass)
        for (int i = 0; i < matriz.length; i++) {

            double[] yO = new double[nNeuronios];
            for (int linha = 0; linha < nNeuronios; linha++) {
                double soma = 0;
                for (int j = 0; j < nEntradas; j++) {
                    soma += wOculta[linha][j] * matriz[i][j];
                }
                yO[linha] = 1.0 / (1.0 + Math.exp(-soma));
            }

            double somaSaida = 0;
            for (int j = 0; j < nNeuronios; j++) {
                somaSaida += yO[j] * wSaida[0][j];
            }
            double yFinal = 1.0 / (1.0 + Math.exp(-somaSaida));

            // Imprimindo o resultado
            System.out.printf("Entrada: [%2.0f, %2.0f] | Target: %.0f | Saída Bruta: %.4f | Arredondado: %d\n",
                    matriz[i][1], matriz[i][2], matriz[i][3], yFinal, Math.round(yFinal));
        }

    }
}

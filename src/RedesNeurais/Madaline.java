package RedesNeurais;

import java.util.Arrays;

public class Madaline implements Executor {
    @Override
    public void executar() {
        //taxa de aprendizado
        double n = 0.01;

        int nEntradas = 3;
        int nNeuronios = 2;

        //pesos
        //i é o neuronio e j o peso
        double w[][] = new double[nNeuronios][nEntradas];

        double matriz[][] = {
                {-1, -1, -1, -1},
                {-1, 1, -1, 1},
                {-1, -1, 1, 1},
                {-1, 1, 1, -1}};

        //gerar pesos
        for (int i = 0; i < nEntradas; i++) {
            for (int j = 0; j < nNeuronios; j++) {
                w[j][i] = (Math.random() * 2) - 1;
            }
        }

        double[] guardarSoma = new double[nNeuronios];
        double[] guardarY = new double[nNeuronios];

        int epocas = 0;
        while (epocas != 10000) {


            //percorrer as linhas
            for (int i = 0; i < matriz.length; i++) {
                //fazer a soma ponderada de cada neuronio
                for (int linha = 0; linha < nNeuronios; linha++) {
                    double soma = 0;
                    for (int j = 0; j < nEntradas; j++) {
                        soma += matriz[i][j] * w[linha][j];

                        //guardar a soma de cada neuronio nesse vetor
                        guardarSoma[linha] = soma;
                    }
                }

                //Y é a saida
                for (int j = 0; j < guardarSoma.length; j++) {
                    if (guardarSoma[j] >= 0) {
                        guardarY[j] = 1;
                    } else {
                        guardarY[j] = -1;
                    }
                }

                //pegar a soma de ambos neuronios e passar pela mesma função
                double soma = Arrays.stream(guardarY).sum();
                ;
                if (soma >= 0) {
                    soma = 1;
                } else {
                    soma = -1;
                }

                //target
                double t = matriz[i][3];
                if (soma != t) {
                    //escolher o neuronio correto com menor valor absoluto
                    double menor = Math.abs(guardarSoma[0]);
                    int indice = 0;

                    for (int j = 1; j < guardarSoma.length; j++) {
                        double atual = Math.abs(guardarSoma[j]);

                        if (menor > atual) {
                            menor = atual;
                            indice = j;
                        }

                    }
                    //pegar o neuronio correto;
                    for (int j = 0; j < nEntradas; j++) {
                        w[indice][j] = w[indice][j] + n * (t - guardarSoma[indice]) * matriz[i][j];
                    }


                }

            }

            epocas++;
        }

        System.out.println("Treinamento finalizado em " + epocas + " épocas.");
        System.out.println("Pesos Finais da Camada Oculta:");
        for(int i = 0; i < nNeuronios; i++){
            System.out.printf("Neurônio %d: [%.2f, %.2f, %.2f]\n", i, w[i][0], w[i][1], w[i][2]);
        }
    }
}
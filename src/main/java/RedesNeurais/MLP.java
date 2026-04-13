package RedesNeurais;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

public class MLP implements Executor {
    @Override
    public void executar() {
        //taxa de aprendizado
        double n = 0.1;

        int epocas = 0;

        double matriz[][] = {
                {-1, 0, 0, 0, 1},
                {-1, 1, 0, 1, 0},
                {-1, 0, 1, 1, 0},
                {-1, 1, 1, 0, 1}};

        int nEntradas = 3;
        int nNeuronios = 3;

        double[][] wOculta = new double[nNeuronios][nEntradas];

        //saida é o peso do segundo conjunto de neuronios que vai receber a saida da camada oculta de entrada
        double[][] wSaida = new double[2][nNeuronios];

        //gerando pesos da camada oculta
        for (int i = 0; i < nEntradas; i++) {
            for (int j = 0; j < nNeuronios; j++) {
                wOculta[i][j] = (Math.random() * 2) - 1;
            }
        }

        //gerando pesos do w saida
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < nNeuronios; j++) {
                wSaida[i][j] = (Math.random() * 2) - 1;
            }
        }


        while (epocas < 100000) {

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
                double[] yFinal = new double[2];


                for (int linha = 0; linha < 2; linha++) {
                    //zerar a soma no inicio de cada linha pq cada linha é um neuronio
                    double somaSaida = 0;

                    for (int j = 0; j < nNeuronios; j++) {
                        //aqui estamos fazendo o calculo de x e w do proximo neuronio w saida é os pesos gerados aleatoriamente
                        //x é o yOculta que é a saida dos neuronios da primeira camada
                        somaSaida += yOculta[j] * wSaida[linha][j];
                    }

                    //ja que teremos que 2 saidas target temos que armazenar essas 2 saidas
                    //saida final
                    yFinal[linha] = 1.0 / (1.0 + Math.exp(-somaSaida));
                }


                //BACKPROPAGATION agora
                //Calcular o erro de saida
                double[] t = {matriz[i][3], matriz[i][4]};
                double[] deltaSaida = new double[2];

                //aqui calculamos o "erro" de cada saida comparado com o tgt
                for (int j = 0; j < 2; j++) {
                    deltaSaida[j] = (t[j] - yFinal[j]) * yFinal[j] * (1 - yFinal[j]);
                }


                //vetor para guardar o erro de cada neu
                double[] deltaOculto = new double[nNeuronios];

                for (int j = 0; j < nNeuronios; j++) {
                    // Somar os erros vindos das 2 saídas
                    double somaErro = 0;
                    for (int saida = 0; saida < 2; saida++) {
                        somaErro += deltaSaida[saida] * wSaida[saida][j];
                    }

                    // Aplicar na fórmula do deltaOculto
                    deltaOculto[j] = yOculta[j] * (1 - yOculta[j]) * somaErro;
                }

                //Atualizar os pesos da Camada de Saída
                // Quem é a entrada da saída? É o yOculta!
                for (int linha = 0; linha < 2; linha++) {
                    for (int j = 0; j < nNeuronios; j++) {
                        wSaida[linha][j] = wSaida[linha][j] + n * deltaSaida[linha] * yOculta[j];
                    }
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
        System.out.println("=== TESTE FINAL MLP (2 SAÍDAS) ===");
        System.out.println("Treinamento concluído em " + epocas + " épocas.");
        System.out.println("---------------------------------------------------------");

        // Arrays para guardar os resultados
        double[] resultadosY1 = new double[4];
        double[] resultadosY2 = new double[4];
        String[] labels = new String[4];

        for (int i = 0; i < matriz.length; i++) {
            // Calcular yOculta (forward pass)
            double[] yO = new double[nNeuronios];
            for (int linha = 0; linha < nNeuronios; linha++) {
                double soma = 0;
                for (int j = 0; j < nEntradas; j++) {
                    soma += wOculta[linha][j] * matriz[i][j];
                }
                yO[linha] = 1.0 / (1.0 + Math.exp(-soma));
            }

            // Calcular yFinal (as 2 saídas)
            double[] yF = new double[2];
            for (int linha = 0; linha < 2; linha++) {
                double somaSaida = 0;
                for (int j = 0; j < nNeuronios; j++) {
                    somaSaida += yO[j] * wSaida[linha][j];
                }
                yF[linha] = 1.0 / (1.0 + Math.exp(-somaSaida));
                // Guardar os resultados
                resultadosY1[i] = yF[0];
                resultadosY2[i] = yF[1];
                labels[i] = String.format("[%.0f, %.0f]", matriz[i][1], matriz[i][2]);
            }

            // Imprimir resultado
            System.out.printf("Entrada: [%2.0f, %2.0f] | Targets: [%.0f, %.0f] | Saídas: [%.4f, %.4f] | Arredondado: [%d, %d]\n",
                    matriz[i][1], matriz[i][2],
                    matriz[i][3], matriz[i][4],
                    yF[0], yF[1],
                    Math.round(yF[0]), Math.round(yF[1]));
        }
        mostrarGraficosY1(resultadosY1, resultadosY2, labels);
    }

    private void mostrarGraficosY1(double[] resultadosY1, double[] resultadosY2, String[] labels) {

        JFrame frame1 = new JFrame("Gráfico Y1");
        JFrame frame2 = new JFrame("Gráfico Y2");

        int largura = 800;
        int altura = 600;

        DefaultCategoryDataset datasetY1 = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetY2 = new DefaultCategoryDataset();

        for (int i = 0; i < resultadosY1.length; i++) {
            datasetY1.addValue(resultadosY1[i], "Y1", labels[i]);
        }

        for (int i = 0; i < resultadosY2.length; i++) {
            datasetY2.addValue(resultadosY2[i], "Y2", labels[i]);
        }

        JFreeChart chart1 = ChartFactory.createLineChart(
                "Saída Y1",
                "Entradas [x1, x2]",
                "Valor de Y1",
                datasetY1
        );

        JFreeChart chart2 = ChartFactory.createLineChart(
                "Saída Y2",
                "Entradas [x1, x2]",
                "Valor de Y2",
                datasetY2
        );

        ChartPanel panel = new ChartPanel(chart1);
        panel.setPreferredSize(new java.awt.Dimension(largura, altura));
        ChartPanel panel2 = new ChartPanel(chart2);
        panel2.setPreferredSize(new java.awt.Dimension(largura, altura));

        frame1.setContentPane(panel);
        frame1.pack();
        frame1.setVisible(true);

        frame2.setContentPane(panel2);
        frame2.pack();
        frame2.setVisible(true);
    }

}

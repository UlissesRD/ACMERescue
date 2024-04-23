package ui;

import dadosEventos.Ciclone;
import dadosEventos.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class PainelCiclone extends JFrame implements ActionListener {
    private JTextField codigo;
    private JTextField data;
    private JTextField latidude;
    private JTextField longitude;
    private JTextField velocidade;
    private JTextField precipitacao;
    private JButton retorna;
    private JButton confirma;
    private JButton limpa;
    private JButton encerra;
    private JTextArea areaParaMostrarDados;
    private TelaInicialEventos telaInicialEventos;
    private ACMERescue acmeRescue;

    public PainelCiclone(TelaInicialEventos telaInicialEventos) {
        super();
        this.telaInicialEventos = telaInicialEventos;
        acmeRescue = telaInicialEventos.getAcmeRescue();

        codigo = new JTextField(20);
        data = new JTextField(20);
        latidude = new JTextField(20);
        longitude = new JTextField(20);

        confirma = new JButton("CONFIRMAR CADASTRO");
        limpa = new JButton("LIMPAR CAMPOS");
        retorna = new JButton("RETORNAR A TELA ANTERIOR");
        encerra = new JButton("ENCERRAR PROGRAMA");

        areaParaMostrarDados = new JTextArea(10,30);

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53,94,59));
        cabecalho.setPreferredSize(new Dimension(600,50));
        JLabel titulo = new JLabel("CADASTRAR CICLONE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 35));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(cabecalho);

        ///////////// CAMPOS DE TEXTO - ENTRADA \\\\\\\\\\\\\\\\

        velocidade = new JTextField(20);
        precipitacao = new JTextField(20);

        container.add(Box.createVerticalGlue()); //quebra de linha
        container.add(campoComRotulo("Codigo:", codigo));
        container.add(campoComRotulo("Data:", data));
        container.add(campoComRotulo("Latitude:", latidude));
        container.add(campoComRotulo("Longitude:", longitude));
        container.add(campoComRotulo("Velocidade:", velocidade));
        container.add(campoComRotulo("Precipitacao:", precipitacao));


        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        JPanel areaTxt = new JPanel();
        areaTxt.add(areaParaMostrarDados);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\

        JPanel botoes = new JPanel(new GridLayout(4,1));
        botoes.add(confirma);
        botoes.add(limpa);
        botoes.add(retorna);
        botoes.add(encerra);
        container.add(botoes);


        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        confirma.addActionListener(this);
        limpa.addActionListener(this);
        //mostrarDadosCadastrados.addActionListener(this);
        encerra.addActionListener(this);
        retorna.addActionListener(this);

        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\

        this.add(container);
        this.setSize(600, 800);
        this.setTitle("Exemplo de Eventos");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    ///////////// MÉTODO PARA ROTULAR CAMPOS DE TEXTO \\\\\\\\\\\\\\\\
    private JPanel campoComRotulo(String txt, JTextField campoTexto) {
        JPanel painel = new JPanel(new FlowLayout());
        JLabel rotulo = new JLabel(txt);
        rotulo.setForeground(new Color(53,94,59));
        campoTexto.setColumns(20);
        painel.add(rotulo);
        painel.add(campoTexto);
        return painel;
    }

    ///////////// OVERRIDE - TRATAR BOTOES \\\\\\\\\\\\\\\\

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirma) {
            try {
                String codigoS = codigo.getText();
                String dataS = data.getText();
                double latitudeD = Double.parseDouble(latidude.getText());
                double longitudeD = Double.parseDouble(longitude.getText());
                double velocidadeD = Double.parseDouble(velocidade.getText());
                double precipitacaoD = Double.parseDouble(precipitacao.getText());
                String latitudeS = String.valueOf(latitudeD);
                String longitudeS = String.valueOf(longitudeD);
                String velocidadeS = String.valueOf(velocidadeD);
                String precipitacaoS = String.valueOf(precipitacaoD);

                if (eventoExiste(codigoS)) {
                    throw new IllegalAccessException();
                }

                if (codigoS.isEmpty() || dataS.isEmpty() || latitudeS.isEmpty() || longitudeS.isEmpty() || velocidadeS.isEmpty() || precipitacaoS.isEmpty()) {
                    throw new NullPointerException();
                }

                if (velocidadeD < 0){
                    throw new RuntimeException();
                }

                if (precipitacaoD < 0){
                    throw new IndexOutOfBoundsException();
                }

                String erroFormatoData = validarFormatoData(dataS);
                if (erroFormatoData != null) {
                    throw new IllegalArgumentException(erroFormatoData);
                }
                else {

                    Evento novoEvento = new Ciclone(codigoS, dataS, latitudeD, longitudeD, velocidadeD, precipitacaoD);
                    telaInicialEventos.getEventos().add(novoEvento);

                    Collections.sort(telaInicialEventos.getEventos(), (evento1, evento2) -> evento1.getCodigo().compareTo(evento2.getCodigo()));

                    codigo.setText("");
                    data.setText("");
                    latidude.setText("");
                    longitude.setText("");
                    velocidade.setText("");
                    precipitacao.setText("");

                    areaParaMostrarDados.setForeground(new Color(53, 94, 59));
                    areaParaMostrarDados.setText("EVENTO CADASTRADO COM SUCESSO");
                    //acmeRescue.getPainelAtendimento().getComboBox().addItem(novoEvento.getCodigo());
                }
            }
            catch (IllegalAccessException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> JA EXISTE UM EVENTO COM ESSE CODIGO <----");
            }
            catch (IndexOutOfBoundsException ex){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> A PRECIPITACAO DE UM CICLONE NÃO PODE SER NEGATIVA <----");
            }
            catch (NullPointerException ex){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> VOCE NAO PREENCHEU TODOS OS CAMPOS <----");
            }
            catch (NumberFormatException ex){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("************************************ ERRO ************************************\n" +
                        "\n" +
                        "  ESTE ERRO APARECEU POR UM DOS DOIS MOTIVOS ABAIXO:\n" +
                        "\n" +
                        "        1 | VOCE NÃO PREENCHEU TODOS OS CAMPOS DE DADOS\n" +
                        "        2 | VOCE INSERIU UM ALGARISMO INVALIDO\n" +
                        "\n" +
                        "  REVISE OS CAMPOS DE DADOS E TENTE NOVAMENTE");
            }
            catch (IllegalArgumentException es){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        es.getMessage());
            }
            catch (RuntimeException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> A VELOCIDADE DE UM CICLONE NÃO PODE SER NEGATIVA <----");
            }
            catch (Exception es){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> REVISE SEUS DADOS, UM ALGARISMO INVALIDO FOI DETECTADO <----");
            }
        }
        else if (e.getSource() == limpa) {
            areaParaMostrarDados.setText("");
            codigo.setText("");
            data.setText("");
            latidude.setText("");
            longitude.setText("");
        }
        else if(e.getSource() == retorna){
            areaParaMostrarDados.setText("");
            codigo.setText("");
            data.setText("");
            latidude.setText("");
            longitude.setText("");
            telaInicialEventos.setVisible(true);
            this.dispose();
        }
        else if(e.getSource() == encerra){
            System.exit(0);
        }
    }

    private boolean eventoExiste(String codigo) {
        return telaInicialEventos.getEventos().stream().anyMatch(evento -> evento.getCodigo().equals(codigo));
    }

    private String validarFormatoData(String data) {
        try {
            String[] partes = data.split("/");

            if (partes.length != 3) {
                return "----> DATA COM O FORMATO INVÁLIDO. USE O FORMATO XX/XX/XXXX<----";
            }

            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);

            if (partes[0].length() != 2 || partes[1].length() != 2 || partes[2].length() != 4) {
                return "----> DATA COM O FORMATO INVÁLIDO. USE O FORMATO XX/XX/XXXX<----";
            }

            if (dia < 01 || dia > 31 || mes < 01 || mes > 12 || ano < 0000) {
                return "----> DIA, MÊS OU/E ANO INVÁLIDO(S) <----";
            }

            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) {
                return "----> O MÊS " + mes + " NÃO POSSUI 31 DIAS <----";
            }

            if (mes == 2 && dia >= 30){
                return "----> FEVEREIRO NÃO POSSUI " + dia + " DIAS <----";
            }

            if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
                if(mes == 02 && dia >= 29) {
                    return "----> O ANO " + ano + " é BISSEXTO, PORTANTO FEVEREIRO NÃO TEM " + dia + " DIAS <----";
                }
            }

            return null;
        } catch (NumberFormatException e) {
            return "----> FORMATO INVÁLIDO. USE O FORMATO XX/XX/XXXX <----";
        }
    }
}

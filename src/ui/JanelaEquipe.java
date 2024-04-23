package ui;
import dadosAtendimento.Atendimento;
import dadosEquipe.Equipe;
import dadosEventos.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JanelaEquipe extends JFrame implements ActionListener {
    private JTextField codinome;
    private JTextField quantidade;
    private JTextField latidude;
    private JTextField longitude;
    private JButton retorna;
    private JButton confirma;
    private JButton limpa;
    private JButton mostraDadosEquipes;
    private JButton encerra;
    private JTextArea areaParaMostrarDados;
    private List<Equipe> equipesCadastradas = new ArrayList<>();
    private ACMERescue acmeRescue;



    public JanelaEquipe(ACMERescue acmeRescue) {
        super();
        this.acmeRescue = acmeRescue;
        this.equipesCadastradas = new ArrayList<>();

        codinome = new JTextField(20);
        quantidade = new JTextField(20);
        latidude = new JTextField(20);
        longitude = new JTextField(20);

        confirma = new JButton("CONFIRMAR CADASTRO");
        limpa = new JButton("LIMPAR CAMPOS");
        retorna = new JButton("RETORNAR A TELA INICIAL");
        encerra = new JButton("ENCERRAR PROGRAMA");
        mostraDadosEquipes = new JButton("MOSTRAR EQUIPES CADASTRADAS");

        areaParaMostrarDados = new JTextArea(10, 30);

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("CADASTRAR EQUIPE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 35));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(cabecalho);

        ///////////// CAMPOS DE TEXTO - ENTRADA \\\\\\\\\\\\\\\\


        container.add(Box.createVerticalGlue()); //quebra de linha
        container.add(campoComRotulo("Codinome:", codinome));
        container.add(campoComRotulo("Quantidade:", quantidade));
        container.add(campoComRotulo("Latitude:", latidude));
        container.add(campoComRotulo("Longitude:", longitude));


        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        JPanel areaTxt = new JPanel();
        areaTxt.add(areaParaMostrarDados);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\

        JPanel botoes = new JPanel(new GridLayout(5, 1));
        botoes.add(confirma);
        botoes.add(limpa);
        botoes.add(retorna);
        botoes.add(encerra);
        botoes.add(mostraDadosEquipes);
        container.add(botoes);


        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        confirma.addActionListener(this);
        limpa.addActionListener(this);
        encerra.addActionListener(this);
        retorna.addActionListener(this);
        mostraDadosEquipes.addActionListener(this);

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
        rotulo.setForeground(new Color(53, 94, 59));
        campoTexto.setColumns(20);
        painel.add(rotulo);
        painel.add(campoTexto);
        return painel;
    }

    ///////////// OVERRIDE - TRATAR BOTOES \\\\\\\\\\\\\\\\

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == limpa) {
            areaParaMostrarDados.setText("");
            codinome.setText("");
            quantidade.setText("");
            latidude.setText("");
            longitude.setText("");
        } else if (e.getSource() == retorna) {

            acmeRescue.setVisible(true);
            this.dispose();
        } else if (e.getSource() == encerra) {
            System.exit(0);
        }
        else if (e.getSource()==mostraDadosEquipes){
            StringBuilder stringBuilder = new StringBuilder();
            if (!getEquipes().isEmpty()) {
                areaParaMostrarDados.setForeground(new Color(53, 94, 59));
                stringBuilder.append("Equipes Cadastradas:\n");
                for (Equipe equipe : getEquipes()) {
                    stringBuilder.append(equipe.geraDescricaoEquipe()).append("\n");
                }
            } else {
                areaParaMostrarDados.setForeground(Color.RED);
                stringBuilder.append("----> NENHUMA EQUIPE CADASTRADA NO SISTEMA <----\n");
            }
            areaParaMostrarDados.setText(String.valueOf(stringBuilder));
        }else if (e.getSource() == confirma){
            try {
                String codinomeText = codinome.getText();
                String quantidadeText = quantidade.getText();
                String latitudeText = latidude.getText();
                String longitudeText = longitude.getText();

                if (codinomeText.isEmpty() || quantidadeText.isEmpty() || latitudeText.isEmpty() || longitudeText.isEmpty()) {
                    throw new NullPointerException();
                }

                int quantidade = Integer.parseInt(quantidadeText);
                double latitude = Double.parseDouble(latitudeText);
                double longitude = Double.parseDouble(longitudeText);

                boolean equipeCadastrada = false;
                for (Equipe equipe : equipesCadastradas) {
                    if (equipe.getCodinome().equals(codinomeText)) {
                        equipeCadastrada = true;
                        break;
                    }
                }

                if (equipeCadastrada) {
                    throw new IllegalArgumentException();
                } else {
                    Equipe novaEquipe = new Equipe(codinomeText, quantidade, latitude, longitude);
                    equipesCadastradas.add(novaEquipe);

                    Collections.sort(equipesCadastradas, Comparator.comparing(Equipe::getCodinome));

                    limparCampos();
                    areaParaMostrarDados.setForeground(new Color(53, 94, 59));
                    areaParaMostrarDados.setText("EQUIPE CADASTRADA COM SUCESSO");
                }
            } catch (NumberFormatException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("************************************ ERRO ************************************\n" +
                        "\n" +
                        "  ESTE ERRO APARECEU POR UM DOS DOIS MOTIVOS ABAIXO:\n" +
                        "\n" +
                        "        1 | VOCE NÃO PREENCHEU TODOS OS CAMPOS DE DADOS\n" +
                        "        2 | VOCE INSERIU UM ALGARISMO INVALIDO\n" +
                        "\n" +
                        "  REVISE OS CAMPOS DE DADOS E TENTE NOVAMENTE");
            } catch (NullPointerException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> VOCE NAO PREENCHEU TODOS OS CAMPOS <----");
            } catch (IllegalArgumentException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> JA EXISTE UMA EQUIPE COM ESSE CODINOME <----");
            }

        }
    }

    private void limparCampos() {
        codinome.setText("");
        quantidade.setText("");
        latidude.setText("");
        longitude.setText("");
    }

    public List<Equipe> getEquipes() {
        return equipesCadastradas;
    }
}
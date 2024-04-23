package ui;

import dadosEventos.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaInicialEventos extends JFrame implements ActionListener {
    private JButton abrirPainelSeca;
    private JButton abrirPainelCiclone;
    private JButton abrirPainelTerremoto;
    private JButton mostraDadosEventos;
    private JButton encerra;
    private JButton limpa;
    private JButton retorna;
    private JTextArea areaDeTexto;
    private ArrayList<Evento> eventos = new ArrayList<>();
    private ArrayList<Evento> eventosDisponiveis = new ArrayList<>();

    private ACMERescue acmeRescue;


    public TelaInicialEventos(ACMERescue acmeRescue) {
        super("Escolha de EVENTO");
        this.acmeRescue = acmeRescue;

        abrirPainelSeca = new JButton("CADASTRAR SECA");
        abrirPainelCiclone = new JButton("CADASTRAR CICLONE");
        abrirPainelTerremoto = new JButton("CADASTRAR TERREMOTO");
        mostraDadosEventos = new JButton("MOSTRAR EVENTOS CADASTRADOS");
        limpa = new JButton("LIMPAR CAIXA DE TEXTO");
        retorna = new JButton("RETORNAR A TELA INICIAL");
        encerra = new JButton("ENCERRAR PROGRAMA");


        ///////////// CABECALHO \\\\\\\\\\\\\\\\
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("ESCOLHA DE EVENTO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 50));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(cabecalho);

        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        container.add(Box.createVerticalGlue()); //quebra de linha
        areaDeTexto = new JTextArea(10, 30);
        JPanel areaTxt = new JPanel();
        areaDeTexto.setText("\n\n\n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
        areaTxt.add(areaDeTexto);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\
        JPanel painelBotoes = new JPanel(new GridLayout(7, 1));
        painelBotoes.add(abrirPainelSeca);
        painelBotoes.add(abrirPainelCiclone);
        painelBotoes.add(abrirPainelTerremoto);
        painelBotoes.add(mostraDadosEventos);
        painelBotoes.add(limpa);
        painelBotoes.add(retorna);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        abrirPainelSeca.addActionListener(this);
        abrirPainelCiclone.addActionListener(this);
        abrirPainelTerremoto.addActionListener(this);
        mostraDadosEventos.addActionListener(this);
        limpa.addActionListener(this);
        retorna.addActionListener(this);
        encerra.addActionListener(this);


        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\
        this.add(container);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == abrirPainelSeca) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelSeca(this);
            this.dispose();
        } else if (e.getSource() == abrirPainelCiclone) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelCiclone(this);
            this.dispose();
        } else if (e.getSource() == abrirPainelTerremoto) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == mostraDadosEventos){
            areaDeTexto.setText(mostraDados());
            areaDeTexto.setForeground(new Color(53,94,59));
            try {
                if (eventos.isEmpty()) {
                    throw new NullPointerException();
                } else {
                    areaDeTexto.setText("");
                    areaDeTexto.append("Eventos Cadastrados:\n");
                }

                areaDeTexto.setText(mostraDados());
            }
            catch (NullPointerException exception){
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" +
                        "----> NENHUM EVENTO CADASTRADO NO SISTEMA <----");
            }
        } else if (e.getSource() == limpa) {
            areaDeTexto.setText("");
        }
        else if(e.getSource() == retorna){
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            acmeRescue.setVisible(true);
            this.dispose();
        }
        else if(e.getSource() == encerra){
            System.exit(0);
        }
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    private String mostraDados() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Evento evento : getEventos()) {
            stringBuilder.append(evento.geraDescricao()).append("\n");
        }
        return stringBuilder.toString();
    }

    public ACMERescue getAcmeRescue(){return acmeRescue;}
}

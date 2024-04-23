package ui;

import dadosAtendimento.Atendimento;
import dadosEquipamentos.Equipamento;
import dadosEquipe.Equipe;
import dadosEventos.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ACMERescue extends JFrame implements ActionListener {
    private JButton cadastrarEvento;
    private JButton cadastrarEquipe;
    private JButton cadastrarEquipamento;
    private JButton cadastrarAtendimento;
    private JButton mostraTodosOsDados;
    private JButton vincularEquipamentoAEquipe;
    private JButton alocarAtendimentos;
    private JButton consultarAtendimentos;
    private JButton alterarSituacaoAtendimento;
    private JButton carregarDadosIniciais;
    private JButton salvarDados;
    private JButton carregarDados;
    private JButton limpa;
    private JButton encerra;
    private JTextArea areaDeTexto;

    private TelaInicialEventos telaEventos;
    private PainelAtendimento painelAtendimento;
    private JanelaEquipe janelaEquipe;
    private JanelaInicialEquipamentos janelaInicialEquipamentos;
    private JanelaVincularEquipamentoEquipe janelaVincularEquipamentoEquipe;
    private PainelDadosIniciais painelDadosIniciais;
    private ArrayList<Atendimento> atendimentos = new ArrayList<>();


    public ACMERescue () {
        super("Escolha de EVENTO");

        cadastrarEvento = new JButton("CADASTRAR EVENTO");
        cadastrarEquipe = new JButton("CADASTRAR EQUIPE");
        cadastrarEquipamento = new JButton("CADASTRAR EQUIPAMENTO");
        cadastrarAtendimento = new JButton("CADASTRAR ATENDIMENTO");
        mostraTodosOsDados = new JButton("MOSTRAR DADOS CADASTRADOS");
        vincularEquipamentoAEquipe = new JButton("VINCULAR EQUIPAMENTO A EQUIPE");
        alocarAtendimentos = new JButton("ALOCAR ATENDIMENTO");
        consultarAtendimentos = new JButton("CONSULTAR ATENDIMENTO");
        alterarSituacaoAtendimento = new JButton("ALTERAR SITUACAO DE ATENDIMENTO");
        carregarDadosIniciais = new JButton("CARREGAR DADOS INICIAIS");
        salvarDados = new JButton("SALVAR DADOS");
        carregarDados = new JButton("CARREGAR DADOS");
        limpa = new JButton("LIMPAR CAIXA DE TEXTO");
        encerra = new JButton("ENCERRAR PROGRAMA");


        ///////////// CABECALHO \\\\\\\\\\\\\\\\
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("ACME RESCUE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 60));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(cabecalho);

        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        container.add(Box.createVerticalGlue()); //quebra de linha
        areaDeTexto = new JTextArea(10, 30);
        JPanel areaTxt = new JPanel();
        areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
        areaTxt.add(areaDeTexto);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\
        JPanel painelBotoes = new JPanel(new GridLayout(7, 1));
        painelBotoes.add(cadastrarEvento);
        painelBotoes.add(cadastrarEquipe);
        painelBotoes.add(cadastrarEquipamento);
        painelBotoes.add(cadastrarAtendimento);
        painelBotoes.add(mostraTodosOsDados);
        painelBotoes.add(vincularEquipamentoAEquipe);
        painelBotoes.add(alocarAtendimentos);
        painelBotoes.add(consultarAtendimentos);
        painelBotoes.add(alterarSituacaoAtendimento);
        painelBotoes.add(carregarDadosIniciais);
        painelBotoes.add(salvarDados);
        painelBotoes.add(carregarDados);
        painelBotoes.add(limpa);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        cadastrarEvento.addActionListener(this);
        cadastrarEquipe.addActionListener(this);
        cadastrarEquipamento.addActionListener(this);
        cadastrarAtendimento.addActionListener(this);
        mostraTodosOsDados.addActionListener(this);
        vincularEquipamentoAEquipe.addActionListener(this);
        alocarAtendimentos.addActionListener(this);
        consultarAtendimentos.addActionListener(this);
        alterarSituacaoAtendimento.addActionListener(this);
        carregarDadosIniciais.addActionListener(this);
        salvarDados.addActionListener(this);
        carregarDados.addActionListener(this);
        limpa.addActionListener(this);
        encerra.addActionListener(this);

        telaEventos = new TelaInicialEventos(this);
        telaEventos.setVisible(false);
        janelaEquipe = new JanelaEquipe(this);
        janelaEquipe.setVisible(false);

        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\

        this.add(container);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        janelaInicialEquipamentos = new JanelaInicialEquipamentos(this);
        janelaInicialEquipamentos.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarEvento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            telaEventos.setVisible(true);
            this.dispose();
        } else if (e.getSource() == cadastrarEquipe) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            janelaEquipe.setVisible(true);
            this.dispose();
        } else if (e.getSource() == cadastrarEquipamento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            janelaInicialEquipamentos.setVisible(true);
            this.dispose();
        } else if (e.getSource() == cadastrarAtendimento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelAtendimento(this);
            this.dispose();
        }else if (e.getSource() == mostraTodosOsDados){
            areaDeTexto.setText(mostraDados());
            areaDeTexto.setForeground(new Color(53,94,59));
            try {
                if (telaEventos.getEventos().isEmpty() && getAtendimentos().isEmpty() && janelaEquipe.getEquipes().isEmpty() &&
                janelaInicialEquipamentos.getEquipamentos().isEmpty()) {
                    throw new NullPointerException();
                } else {
                    areaDeTexto.setText("");
                }
                areaDeTexto.setText(mostraDados());
            }
            catch (NullPointerException exception){
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" +
                        "----> NENHUM DADO CADASTRADO NO SISTEMA <----");
            }
        } else if (e.getSource() == vincularEquipamentoAEquipe) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new JanelaVincularEquipamentoEquipe(this);
            this.dispose();
        } else if (e.getSource() == alocarAtendimentos) {
            //new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == consultarAtendimentos) {
            //new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == alterarSituacaoAtendimento) {
            //new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == carregarDadosIniciais) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelDadosIniciais(this);
            this.dispose();
        } else if (e.getSource() == salvarDados) {
            //new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == carregarDados) {
            //new PainelTerremoto(this);
            this.dispose();
        } else if (e.getSource() == limpa) {
            areaDeTexto.setText("");
        }else if(e.getSource() == encerra){
            System.exit(0);
        }
    }
    private String mostraDados() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!telaEventos.getEventos().isEmpty()) {
            stringBuilder.append("Eventos Cadastrados:\n");
            for (Evento evento : telaEventos.getEventos()) {
                stringBuilder.append(evento.geraDescricao()).append("\n");
            }
        } else {
            stringBuilder.append("Nenhum evento cadastrado\n");
        }

        if (!janelaEquipe.getEquipes().isEmpty()) {
            stringBuilder.append("\nEquipes Cadastradas:\n");
            for (Equipe equipe : janelaEquipe.getEquipes()) {
                stringBuilder.append(equipe.geraDescricaoEquipe()).append("\n");
            }
        } else {
            stringBuilder.append("\nNenhuma equipe cadastrada\n");
        }

        if (!getAtendimentos().isEmpty()) {
            stringBuilder.append("\nAtendimentos Cadastrados:\n");
            for (Atendimento atendimento : getAtendimentos()) {
                stringBuilder.append(atendimento.dadosAtendimento()).append("\n");
            }
        } else {
            stringBuilder.append("\nNenhum atendimento cadastrado\n");
        }

        if (!janelaInicialEquipamentos.getEquipamentos().isEmpty()) {
            stringBuilder.append("\nEquipamentos Cadastradas:\n");
            for (Equipamento equipamento : janelaInicialEquipamentos.getEquipamentos()) {
                stringBuilder.append(equipamento.geraDescricao()).append("\n");
            }
        } else {
            stringBuilder.append("\nNenhum equipamento cadastrada\n");
        }

        return stringBuilder.toString();
    }

    public TelaInicialEventos getTelaInicialEventos() {
        return telaEventos;
    }

    public PainelAtendimento getPainelAtendimento(){return painelAtendimento;}

    public ArrayList<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public boolean codigoEventoExistente(String codigo) {
        for (Evento evento : telaEventos.getEventos()) {
            if (evento.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public JanelaEquipe getJanelaEquipe() {
        return janelaEquipe;
    }

    public JanelaInicialEquipamentos getJanelaInicialEquipamentos() {
        return janelaInicialEquipamentos;
    }
}
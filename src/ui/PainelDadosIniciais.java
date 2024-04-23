package ui;

import dadosAtendimento.Atendimento;
import dadosEquipamentos.Equipamento;
import dadosEquipe.Equipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PainelDadosIniciais extends JFrame implements ActionListener {
    private JButton abrirPainelDIAtendimento;
    private JButton abrirPainelDIEvento;
    private JButton abrirPainelDIEquipamento;
    private JButton abrirPainelDIEquipe;
    private JButton mostraDados;
    private JButton encerra;
    private JButton limpa;
    private JButton retorna;
    private JTextArea areaDeTexto;
    private ArrayList<Equipamento> equipamentos = new ArrayList<>();

    private ACMERescue acmeRescue;
    private PainelDadosIniciaisAtendimento painelDadosIniciaisAtendimento;
    private PainelDadosIniciaisEquipe painelDadosIniciaisEquipe;
    private PainelDadosIniciaisEquipamento painelDadosIniciaisEquipamento;



    public PainelDadosIniciais(ACMERescue acmeRescue) {
        super("Escolha de DADOS INICIAIS A SEREM IMPORTADOS");
        this.acmeRescue = acmeRescue;

        abrirPainelDIAtendimento = new JButton("CARREGAR DADOS INICIAIS DE ATENDIMENTO");
        abrirPainelDIEquipamento = new JButton("CARREGAR DADOS INICIAIS DE EQUIPAMENTO");
        abrirPainelDIEquipe = new JButton("CARREGAR DADOS INICIAIS DE EQUIPE");
        abrirPainelDIEvento = new JButton("CARREGAR DADOS INICIAIS DE EVENTO");
        mostraDados = new JButton("MOSTRAR DADOS CADASTRADOS");
        limpa = new JButton("LIMPAR CAIXA DE TEXTO");
        retorna = new JButton("RETORNAR A TELA INICIAL");
        encerra = new JButton("ENCERRAR PROGRAMA");


        ///////////// CABECALHO \\\\\\\\\\\\\\\\
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("ESCOLHA DE DADO DE ENTRADA");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 30));
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
        JPanel painelBotoes = new JPanel(new GridLayout(8, 1));
        painelBotoes.add(abrirPainelDIEquipe);
        painelBotoes.add(abrirPainelDIEquipamento);
        painelBotoes.add(abrirPainelDIAtendimento);
        painelBotoes.add(abrirPainelDIEvento);
        painelBotoes.add(mostraDados);
        painelBotoes.add(limpa);
        painelBotoes.add(retorna);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        abrirPainelDIEquipe.addActionListener(this);
        abrirPainelDIEquipamento.addActionListener(this);
        abrirPainelDIAtendimento.addActionListener(this);
        abrirPainelDIEvento.addActionListener(this);
        mostraDados.addActionListener(this);
        limpa.addActionListener(this);
        retorna.addActionListener(this);
        encerra.addActionListener(this);


        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\
        this.add(container);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        painelDadosIniciaisAtendimento = new PainelDadosIniciaisAtendimento(this);
        painelDadosIniciaisAtendimento.setVisible(false);
        painelDadosIniciaisEquipe = new PainelDadosIniciaisEquipe(this);
        painelDadosIniciaisEquipe.setVisible(false);
        painelDadosIniciaisEquipamento = new PainelDadosIniciaisEquipamento(this);
        painelDadosIniciaisEquipamento.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == abrirPainelDIEquipe) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            painelDadosIniciaisEquipe.setVisible(true);
            this.dispose();
        } else if (e.getSource() == abrirPainelDIEquipamento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            painelDadosIniciaisEquipamento.setVisible(true);
            this.dispose();
        } else if (e.getSource() == abrirPainelDIAtendimento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            painelDadosIniciaisAtendimento.setVisible(true);
            this.dispose();
        } else if (e.getSource() == abrirPainelDIEvento) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            //new PainelEscavadeira(this);
            this.dispose();
        } else if (e.getSource() == mostraDados){
            areaDeTexto.setForeground(new Color(53,94,59));
            try {
                if (painelDadosIniciaisAtendimento.getFilaAtendimentosPendentes().isEmpty() && painelDadosIniciaisEquipe.getEquipesCarregadas().isEmpty()
                && painelDadosIniciaisEquipamento.getEquipamentosCarregados().isEmpty()) {
                    throw new NullPointerException();
                } else {
                    areaDeTexto.setText("");
                    areaDeTexto.append("Dados Cadastrados:\n");
                }

                areaDeTexto.append(mostraDadosAtendimentos());
                areaDeTexto.append(mostraDadosEquipes());
                areaDeTexto.append(mostraDadosEquipamentos());

                //areaDeTexto.append("TESTE");

            }
            catch (NullPointerException exception){
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" +
                        "----> NENHUM DADO CADASTRADO NO SISTEMA <----");
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

    public ArrayList<Equipamento> getEventos() {
        return equipamentos;
    }

    private String mostraDadosAtendimentos() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Queue<Atendimento> filaAtendimentosPendentes = painelDadosIniciaisAtendimento.getFilaAtendimentosPendentes();
            for (Atendimento atendimento : filaAtendimentosPendentes) {
                stringBuilder.append(atendimento.obterDadosFormatados()).append("\n");
            }
            return stringBuilder.toString();
        }catch(NullPointerException ex){
            String resposta = "NENHUM DADO DE ATENDIMENTO FOI CARREGADO\n";
            return resposta;
        }
    }

    private String mostraDadosEquipes(){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Equipe> equipesCarregadas = painelDadosIniciaisEquipe.getEquipesCarregadas();
            //System.out.println(painelDadosIniciaisEquipe.getEquipesCarregadas());
            for (Equipe equipe : equipesCarregadas) {
                stringBuilder.append(equipe.obterDadosFormatados()).append("\n");
            }
            return stringBuilder.toString();
        } catch(NullPointerException ex){
            String resposta = "\n NENHUM DADO DE EQUIPE FOI CARREGADO\n";
            return resposta;
        }
    }

    private String mostraDadosEquipamentos(){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Equipamento> equipamentosCarregados = painelDadosIniciaisEquipamento.getEquipamentosCarregados();
            //System.out.println(painelDadosIniciaisEquipe.getEquipesCarregadas());
            for (Equipamento equipamento : equipamentosCarregados) {
                stringBuilder.append(equipamento.obterDadosFormatados()).append("\n");
            }
            return stringBuilder.toString();
        } catch(NullPointerException ex){
            String resposta = "\n NENHUM DADO DE EQUIPE FOI CARREGADO\n";
            return resposta;
        }
    }

    public ACMERescue getAcmeRescue(){return acmeRescue;}

}



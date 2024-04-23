package ui;

import dadosEquipamentos.Equipamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JanelaInicialEquipamentos extends JFrame implements ActionListener {
    private JButton abrirPainelBarco;
    private JButton abrirPainelCaminhaoTanque;
    private JButton abrirPainelEscavadeira;
    private JButton mostraDadosEventos;
    private JButton encerra;
    private JButton limpa;
    private JButton retorna;
    private JTextArea areaDeTexto;
    private ArrayList<Equipamento> equipamentos = new ArrayList<>();
    private ArrayList<Equipamento> equipamentosDisponiveis = new ArrayList<>();

    private ACMERescue acmeRescue;


    public JanelaInicialEquipamentos(ACMERescue acmeRescue) {
        super("Escolha de EQUIPAMENTO");
        this.acmeRescue = acmeRescue;

        abrirPainelBarco = new JButton("CADASTRAR BARCO");
        abrirPainelCaminhaoTanque = new JButton("CADASTRAR CAMINHAOTANQUE");
        abrirPainelEscavadeira = new JButton("CADASTRAR ESCAVADEIRA");
        mostraDadosEventos = new JButton("MOSTRAR EQUIPAMENTOS CADASTRADOS");
        limpa = new JButton("LIMPAR CAIXA DE TEXTO");
        retorna = new JButton("RETORNAR A TELA INICIAL");
        encerra = new JButton("ENCERRAR PROGRAMA");


        ///////////// CABECALHO \\\\\\\\\\\\\\\\
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("ESCOLHA DE EQUIPAMENTO");
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
        JPanel painelBotoes = new JPanel(new GridLayout(7, 1));
        painelBotoes.add(abrirPainelBarco);
        painelBotoes.add(abrirPainelCaminhaoTanque);
        painelBotoes.add(abrirPainelEscavadeira);
        painelBotoes.add(mostraDadosEventos);
        painelBotoes.add(limpa);
        painelBotoes.add(retorna);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        abrirPainelBarco.addActionListener(this);
        abrirPainelCaminhaoTanque.addActionListener(this);
        abrirPainelEscavadeira.addActionListener(this);
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
        if (e.getSource() == abrirPainelBarco) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelBarco(this);
            this.dispose();
        } else if (e.getSource() == abrirPainelCaminhaoTanque) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelCaminhaoTanque(this);
            this.dispose();
        } else if (e.getSource() == abrirPainelEscavadeira) {
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            new PainelEscavadeira(this);
            this.dispose();
        } else if (e.getSource() == mostraDadosEventos){
            areaDeTexto.setText(mostraDados());
            areaDeTexto.setForeground(new Color(53,94,59));
            try {
                if (equipamentos.isEmpty()) {
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

    public ArrayList<Equipamento> getEventos() {
        return equipamentos;
    }

    private String mostraDados() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Equipamento equipamento1 : getEventos()) {
            stringBuilder.append(equipamento1.geraDescricao()).append("\n");
        }
        return stringBuilder.toString();
    }

    public ACMERescue getAcmeRescue(){return acmeRescue;}

    public ArrayList<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public boolean equipamentoExiste(int identificador) {
        for (Equipamento equip : equipamentos) {
            if (equip.getId() == identificador) {
                return true; // Equipamento encontrado
            }
        }
        return false; // Equipamento não encontrado
    }

}


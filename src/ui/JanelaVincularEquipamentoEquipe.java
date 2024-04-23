package ui;

import dadosEquipe.Equipe;
import dadosEquipamentos.Equipamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;


public class JanelaVincularEquipamentoEquipe extends JFrame implements ActionListener {
    private JComboBox<Equipamento> equipamentoComboBox;
    private JComboBox<Equipe> equipeComboBox;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JButton vinculos;
    private ACMERescue acmeRescue;

    private JTextArea areaParaMostrarDados;
    private JTextField resultadoVinculo;

    // Conjunto para rastrear os vínculos já cadastrados
    private Set<String> vinculosCadastrados;
    private Map<String, String> equipamentoEquipeMap;

    public JanelaVincularEquipamentoEquipe(ACMERescue acmeRescue) {
        super("Vincular Equipamento a Equipe");
        this.acmeRescue = acmeRescue;
        this.vinculosCadastrados = new HashSet<>();
        this.equipamentoEquipeMap = new HashMap<>();

        equipamentoComboBox = new JComboBox<>(acmeRescue.getJanelaInicialEquipamentos().getEquipamentos().toArray(new Equipamento[0]));
        equipeComboBox = new JComboBox<>(acmeRescue.getJanelaEquipe().getEquipes().toArray(new Equipe[0]));
        btnConfirmar = new JButton("CONFIRMAR");
        btnCancelar = new JButton("CANCELAR");
        vinculos = new JButton("MOSTRAR VINCULOS");

        areaParaMostrarDados = new JTextArea(10, 30);
        resultadoVinculo = new JTextField(30);

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("VINCULAR EQUIPAMENTO A EQUIPE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 28));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(cabecalho);
        container.add(Box.createVerticalGlue()); //quebra de linha


        JPanel areaTxt = new JPanel();
        areaTxt.add(areaParaMostrarDados);
        container.add(areaTxt);

        container.add(campoComRotulo("Selecione o Equipamento (ID):", equipamentoComboBox));
        container.add(campoComRotulo("Selecione a Equipe(Codinome):", equipeComboBox));

        // Adiciona espaço em branco entre os componentes
        container.add(Box.createVerticalStrut(20));

        // Adiciona botões diretamente ao contêiner
        JPanel botoes = new JPanel(new GridLayout(4,1));
        botoes.add(btnConfirmar);
        botoes.add(btnCancelar);
        botoes.add(vinculos);

        container.add(botoes);

        add(container, BorderLayout.CENTER);

        // Adiciona ActionListener aos botões
        btnConfirmar.addActionListener(this);
        btnCancelar.addActionListener(this);
        vinculos.addActionListener(this);

        // Configurações da janela
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel campoComRotulo(String txt, JComponent componente) {
        JPanel painel = new JPanel(new FlowLayout());
        JLabel rotulo = new JLabel(txt);
        painel.add(rotulo);
        painel.add(componente);
        return painel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirmar) {
            Equipamento equipamentoSelecionado = (Equipamento) equipamentoComboBox.getSelectedItem();
            Equipe equipeSelecionada = (Equipe) equipeComboBox.getSelectedItem();

            if (equipamentoSelecionado != null && equipeSelecionada != null) {
                String chaveEquipamento = String.valueOf(equipamentoSelecionado.getId());

                // Verifica se o equipamento já está vinculado a alguma equipe
                if (equipamentoEquipeMap.containsKey(chaveEquipamento)) {
                    areaParaMostrarDados.setForeground(Color.RED);
                    areaParaMostrarDados.setText("ERRO:\n" +
                            "----> O EQUIPAMENTO JÁ ESTÁ VINCULADO À OUTRA EQUIPE <----");
                } else {
                    String chaveVinculo = equipamentoSelecionado.getId() + "-" + equipeSelecionada.getCodinome();

                    // Verifica se o vínculo já foi cadastrado
                    if (vinculosCadastrados.contains(chaveVinculo)) {
                        areaParaMostrarDados.setForeground(Color.RED);
                        areaParaMostrarDados.setText("ERRO:\n" +
                                "----> ALGUM ELEMENTO JÁ ESTÁ VINCULADO <----");
                    } else {
                        equipeSelecionada.getEquipamentos().add(equipamentoSelecionado);
                        vinculosCadastrados.add(chaveVinculo);

                        // Atualiza o mapa com a equipe à qual o equipamento está vinculado
                        equipamentoEquipeMap.put(chaveEquipamento, equipeSelecionada.getCodinome());

                        areaParaMostrarDados.setForeground(new Color(53, 94, 59));
                        areaParaMostrarDados.setText("VÍNCULO CADASTRADO COM SUCESSO");
                    }
                }
            } else {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> SELECIONE UM EQUIPAMENTO E UMA EQUIPE <----");
            }
        } else if (e.getSource() == btnCancelar) {
            acmeRescue.setVisible(true);
            dispose(); // Fecha a janela de vinculação
        } else if (e.getSource() == vinculos) {
            mostrarVinculos();  // Chama o método para mostrar os vínculos
        }
    }


    private void mostrarVinculos() {
        StringBuilder sb = new StringBuilder("Vínculos Cadastrados:\n");
        for (String vinculo : vinculosCadastrados) {
            sb.append(vinculo).append("\n");
        }
        areaParaMostrarDados.setForeground(Color.BLACK);
        areaParaMostrarDados.setText(sb.toString());
    }

}
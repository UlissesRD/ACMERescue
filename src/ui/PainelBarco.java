package ui;

import dadosEquipamentos.Equipamento;
import dadosEquipamentos.Barco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class PainelBarco extends JFrame implements ActionListener {
    private JTextField id;
    private JTextField nome;
    private JTextField custoDia;
    private JTextField capacidade;
    private JButton retorna;
    private JButton confirma;
    private JButton limpa;
    private JButton encerra;
    private JTextArea areaParaMostrarDados;

    private JanelaInicialEquipamentos janelaInicialEquipamentos;
    private ACMERescue acmeRescue;

    public PainelBarco(JanelaInicialEquipamentos janelaInicialEquipamentos) {
        super();
        this.janelaInicialEquipamentos=janelaInicialEquipamentos;
        acmeRescue = janelaInicialEquipamentos.getAcmeRescue();

        id = new JTextField(20);
        nome = new JTextField(20);
        custoDia = new JTextField(20);
        capacidade = new JTextField(20);

        confirma = new JButton("CONFIRMAR CADASTRO");
        limpa = new JButton("LIMPAR CAMPOS");
        retorna = new JButton("RETORNAR A TELA ANTERIOR");
        encerra = new JButton("ENCERRAR PROGRAMA");

        areaParaMostrarDados = new JTextArea(10,30);

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53,94,59));
        cabecalho.setPreferredSize(new Dimension(600,50));
        JLabel titulo = new JLabel("CADASTRAR BARCO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 35));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(cabecalho);

        ///////////// CAMPOS DE TEXTO - ENTRADA \\\\\\\\\\\\\\\\



        container.add(Box.createVerticalGlue()); //quebra de linha
        container.add(campoComRotulo("ID:", id));
        container.add(campoComRotulo("Nome:", nome));
        container.add(campoComRotulo("CustoDia:", custoDia));
        container.add(campoComRotulo("Capacidade:", capacidade));



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
                int Id = Integer.parseInt(id.getText());
                String nomes = nome.getText();
                double custoD =Double.parseDouble(custoDia.getText());
                int capacidades = Integer.parseInt(capacidade.getText());

                String IDs = id.getText();
                String custoS = String.valueOf(custoD);
                String capacidadeS = String.valueOf(capacidade);



                if (equipamentoExiste(Id)) {
                    throw new IllegalAccessException();
                }

                if (IDs.isEmpty() || nomes.isEmpty() || nomes.isEmpty() || custoS.isEmpty() || capacidadeS.isEmpty()) {
                    throw new NullPointerException();
                }

                else {
                    Equipamento novoEquipamento = new Barco(Id, nomes, custoD, capacidades);
                    janelaInicialEquipamentos.getEquipamentos().add(novoEquipamento);

                    Collections.sort(janelaInicialEquipamentos.getEquipamentos(), (equipamento1, equipamento2) -> Integer.compare(equipamento1.getId(), equipamento2.getId()));

                    id.setText("");
                    nome.setText("");
                    custoDia.setText("");
                    capacidade.setText("");

                    areaParaMostrarDados.setForeground(new Color(53, 94, 59));
                    areaParaMostrarDados.setText("EVENTO CADASTRADO COM SUCESSO");
                    acmeRescue.getPainelAtendimento().getComboBox().addItem(String.valueOf(novoEquipamento.getId()));
                }
            }
            catch (IllegalAccessException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> JA EXISTE UM EVENTO COM ESSE CODIGO <----");
            }
            catch (NullPointerException ex){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> VOCE NAO PREENCHEU TODOS OS CAMPOS <----");
            } catch (NumberFormatException ex){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("************************************ ERRO ************************************\n" +
                        "\n" +
                        "  ESTE ERRO APARECEU POR UM DOS DOIS MOTIVOS ABAIXO:\n" +
                        "\n" +
                        "        1 | VOCE NÃO PREENCHEU TODOS OS CAMPOS DE DADOS\n" +
                        "        2 | VOCE INSERIU UM ALGARISMO INVALIDO\n" +
                        "\n" +
                        "  REVISE OS CAMPOS DE DADOS E TENTE NOVAMENTE");
            } catch (IllegalArgumentException es){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        es.getMessage());
            } catch (RuntimeException ex) {
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> A MAGNITUDE DE UM TERREMOTO NÃO PODE SER NEGATIVA <----");
            } catch (Exception es){
                areaParaMostrarDados.setForeground(Color.RED);
                areaParaMostrarDados.setText("ERRO:\n" +
                        "----> REVISE SEUS DADOS, UM ALGARISMO INVALIDO FOI DETECTADO <----");
            }
        }
        else if (e.getSource() == limpa) {
            areaParaMostrarDados.setText("");
            id.setText("");
            nome.setText("");
            custoDia.setText("");
            capacidade.setText("");
        }
        else if(e.getSource() == retorna){
            areaParaMostrarDados.setText("");
            id.setText("");
            nome.setText("");
            custoDia.setText("");
            capacidade.setText("");
            janelaInicialEquipamentos.setVisible(true);
            this.dispose();
        }
        else if(e.getSource() == encerra){
            System.exit(0);
        }
    }

    private boolean equipamentoExiste(int identificador) {
        return janelaInicialEquipamentos.equipamentoExiste(identificador);
    }

}


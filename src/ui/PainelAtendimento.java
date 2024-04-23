package ui;

import dadosAtendimento.Estado;
import dadosEventos.Evento;
import dadosAtendimento.Atendimento;
import ui.TelaInicialEventos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PainelAtendimento extends JFrame implements ActionListener {
    private JComboBox<String> eventosComboBox;
    private JButton cadastrar;
    private JButton retorna;
    private JButton encerra;
    private JTextArea areaDeTexto;
    private JTextField codigo;
    private JTextField dataInicio;
    private JTextField duracao;

    private ArrayList<Atendimento> atendimentos;

    private ArrayList<Evento> eventosJaVinculados;
    private ACMERescue acmeRescue;

    public PainelAtendimento(ACMERescue acmeRescue) {
        super("Cadastramento de Atendimentos");
        this.acmeRescue = acmeRescue;


        atendimentos = new ArrayList<>();

        eventosComboBox = new JComboBox<>();

        codigo = new JTextField(20);
        dataInicio = new JTextField(20);
        duracao = new JTextField(20);

        cadastrar = new JButton("CADASTRAR ATENDIMENTO");
        retorna = new JButton("RETORNAR A TELA ANTERIOR");
        encerra = new JButton("ENCERRAR PROGRAMA");

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("CADASTRAR ATENDIMENTO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 35));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(cabecalho);

        ///////////// CAMPOS DE TEXTO - ENTRADA \\\\\\\\\\\\\\\\

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Codigo do evento:"));

        container.add(panel);

        panel.add(eventosComboBox);
        container.add(Box.createVerticalGlue()); //quebra de linha
        container.add(campoComRotulo("Codigo do atendimento:", codigo));
        container.add(campoComRotulo("Data de Inicio:", dataInicio));
        container.add(campoComRotulo("Duracao:", duracao));

        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        container.add(Box.createVerticalGlue()); //quebra de linha
        areaDeTexto = new JTextArea(10, 30);
        JPanel areaTxt = new JPanel();
        areaDeTexto.setForeground(new Color(53,94,59));
        areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
        areaTxt.add(areaDeTexto);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1));
        painelBotoes.add(cadastrar);
        painelBotoes.add(retorna);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        cadastrar.addActionListener(this);
        retorna.addActionListener(this);
        encerra.addActionListener(this);

        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\
        this.add(container);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        eventosJaVinculados = acmeRescue.getTelaInicialEventos().getEventos();

        for (Evento evento : eventosJaVinculados) {
            eventosComboBox.addItem(evento.getCodigo());
        }

        System.out.println("CONSTRUTOR INICIADO: " + eventosJaVinculados.size());
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

    public Evento buscaEventoPorCodigo(String codigo){
        if(acmeRescue.getTelaInicialEventos().getEventos().isEmpty()){
            for(Evento e : acmeRescue.getTelaInicialEventos().getEventos()){
                if(e.getCodigo().equalsIgnoreCase(codigo)){
                    return e;
                }
            }
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrar) {
            try {
                int codI = Integer.parseInt(codigo.getText());
                String dataInicioS = dataInicio.getText();
                int duracaoI = Integer.parseInt(duracao.getText());

                String codEventoS = eventosComboBox.getSelectedItem().toString();

                if (eventosComboBox.getItemCount() == 0) {
                    throw new IllegalArgumentException("NENHUM EVENTO CADASTRADO AINDA, CADASTRE UM EVENTO \n ANTES DE CADASTRAR UM ATENDIMENTO");
                }

                if (codigo.getText().isEmpty() || dataInicioS.isEmpty() || duracao.getText().isEmpty()) {
                    throw new IllegalArgumentException("----> VOCE DEVE PREENCHER TODOS OS CAMPOS DE DADOS <----");
                }
                if (eventosComboBox.getSelectedItem() == null || acmeRescue.getTelaInicialEventos().getEventos() == null || acmeRescue.getTelaInicialEventos().getEventos().isEmpty()){
                    throw new IllegalArgumentException("NENHUM EVENTO CADASTRADO AINDA, CADASTRE UM EVENTO \n ANTES DE CADASTRAR UM ATENDIMENTO");
                }
                if (codEventoS.isEmpty() || !acmeRescue.codigoEventoExistente(codEventoS)) {
                    throw new RuntimeException("----> ESCOLHA UM CODIGO DE EVENTO EXISTENTE <----");
                }
                if (atendimentoExiste(codigo.getText())) {
                    throw new IllegalAccessException("----> JA EXISTE UM ATENDIMENTO COM ESSE CODIGO <----");
                }

                Atendimento atendimento = new Atendimento(codI, dataInicioS, duracaoI, Estado.PENDENTE, buscaEventoPorCodigo(codEventoS));
                atendimentos.add(atendimento);
                System.out.println("ATENDIMENTOS:\n\n" + atendimentos);
                System.out.println("ATENDIMENTOS: " + atendimentos.size());

                codigo.setText("");
                dataInicio.setText("");
                duracao.setText("");

                areaDeTexto.setForeground(new Color(53, 94, 59));
                areaDeTexto.setText("ATENDIMENTO CADASTRADO COM SUCESSO");
                acmeRescue.setVisible(true);
                this.dispose();

                /*
                if (duracaoI < 0) {
                    throw new IllegalArgumentException("----> A DURACAO DE UM ATENDIMENTO NAO PODE SER NEGATIVA <----");
                }

                String erroFormatoData = validarFormatoData(dataInicioS);
                if (erroFormatoData != null) {
                    throw new IllegalArgumentException(erroFormatoData);
                } else {
                    Atendimento novoAtendimento = new Atendimento(codI, dataInicioS, duracaoI, estInicial, eventoCadastrado);
                    acmeRescue.getAtendimentos().add(novoAtendimento);
                    eventosComboBox.addItem(codEventoS);


                    Collections.sort(acmeRescue.getAtendimentos(), (atendimento1, atendimento2) -> Integer.compare(atendimento1.getCodigo(), atendimento2.getCodigo()));


                }*/
            }catch(Exception ex){
                System.err.println("Erro" + ex);
            }
            /*catch (NumberFormatException np) {
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("************************************ ERRO ************************************\n" +
                        "\n" +
                        "  ESTE ERRO APARECEU POR UM DOS DOIS MOTIVOS ABAIXO:\n" +
                        "\n" +
                        "        1 | VOCE NÃO PREENCHEU TODOS OS CAMPOS DE DADOS\n" +
                        "        2 | VOCE INSERIU UM ALGARISMO INVALIDO\n" +
                        "\n" +
                        "  REVISE OS CAMPOS DE DADOS E TENTE NOVAMENTE");
            }catch (IllegalArgumentException | ArithmeticException | IllegalAccessException ex) {
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" + ex.getMessage());
            }catch (NullPointerException np) {
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" +
                        //"NENHUM EVENTO CADASTRADO AINDA, CADASTRE UM EVENTO \n ANTES DE CADASTRAR UM ATENDIMENTO");
                        np.getMessage());
            }catch (RuntimeException rt) {
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n" + rt.getMessage());
            } catch (Exception ex) {
                areaDeTexto.setForeground(Color.RED);
                areaDeTexto.setText("ERRO:\n----> !!!ERRO GERAL!!! <----" + "\n" +
                        "\nREVISE SEUS DADOS");
            }*/
        }else if(e.getSource() == retorna) {
            codigo.setText("");
            dataInicio.setText("");
            duracao.setText("");
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> ESCOLHA UMA DAS OPCOES ABAIXO <----");
            acmeRescue.setVisible(true);
            this.dispose();
        }
        else if(e.getSource() == encerra){
            System.exit(0);
        }
    }

    private boolean codigoEventoExistente(String codEventoS) {
        return true;
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

    public JComboBox<String> getComboBox(){return eventosComboBox;}

    private boolean atendimentoExiste(String codigo) {
        return acmeRescue.getAtendimentos() != null &&
                acmeRescue.getAtendimentos().stream().anyMatch(atendimento -> Objects.equals(atendimento.getCodigo(), codigo));
    }

    /*
    public boolean eventoVinculou(Evento evs){
        for (Evento ev : eventosJaVinculados){
            if (ev.getCodigo() == evs.getCodigo()){
                return true;
            }
        }
        return false;
    }*/
}

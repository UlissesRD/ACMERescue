package ui;

import dadosAtendimento.Atendimento;
import dadosEventos.Evento;
import ui.TelaInicialEventos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

import dadosAtendimento.Estado;
public class PainelDadosIniciaisAtendimento extends JFrame implements ActionListener {
    private JTextField campoNomeArquivo;
    private JButton botaoCarregar;
    private JButton retorna;
    private JButton encerra;
    private JTextArea areaDeTexto;
    private ACMERescue acmeRescue;


    private Queue<Atendimento> filaAtendimentosPendente = new LinkedList<>();
    private ArrayList<Atendimento> atendimentosArquivo = new ArrayList<>();
    private PainelDadosIniciais painelDadosIniciais;


    public PainelDadosIniciaisAtendimento(PainelDadosIniciais painelDadosIniciais) {
        this.painelDadosIniciais = painelDadosIniciais;
        acmeRescue = painelDadosIniciais.getAcmeRescue();

        campoNomeArquivo = new JTextField(20);
        botaoCarregar = new JButton("CARREGAR ARQUIVO");
        retorna = new JButton("RETORNAR A TELA ANTERIOR");
        encerra = new JButton("ENCERRAR PROGRAMA");

        ///////////// CABECALHO \\\\\\\\\\\\\\\\

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(53, 94, 59));
        cabecalho.setPreferredSize(new Dimension(600, 50));
        JLabel titulo = new JLabel("CARREGAR DADOS INICIAIS");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.ITALIC, 35));
        cabecalho.add(titulo, BorderLayout.CENTER);

        ///////////// CONTAINER PRINCIPAL \\\\\\\\\\\\\\\\

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(cabecalho);

        ///////////// CAMPOS DE TEXTO - ENTRADA \\\\\\\\\\\\\\\\
        JPanel espacoBranco = new JPanel();
        container.add(espacoBranco);
        container.add(campoComRotulo("Nome do Arquivo: ", campoNomeArquivo));

        ///////////// AREA DE TEXTO - SAIDA \\\\\\\\\\\\\\\\

        container.add(Box.createVerticalGlue()); //quebra de linha
        areaDeTexto = new JTextArea(10, 30);
        JPanel areaTxt = new JPanel();
        areaDeTexto.setForeground(new Color(53,94,59));
        areaDeTexto.setText("\n \n \n \n        ----> CADASTRAR DADOS INICIAIS <----");
        areaTxt.add(areaDeTexto);
        container.add(areaTxt);

        ///////////// BOTOES \\\\\\\\\\\\\\\\

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1));
        painelBotoes.add(botaoCarregar);
        painelBotoes.add(retorna);
        painelBotoes.add(encerra);
        container.add(painelBotoes);

        ///////////// TRATAMENTO DOS BOTOES \\\\\\\\\\\\\\\\

        botaoCarregar.addActionListener(this);
        retorna.addActionListener(this);
        encerra.addActionListener(this);

        ///////////// CONFIG DA TELA \\\\\\\\\\\\\\\\

        this.add(container);
        this.setSize(600, 800);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoCarregar) {
            String nomeArquivo = campoNomeArquivo.getText();
            carregarDadosIniciais(nomeArquivo);
        }
        else if(e.getSource() == retorna){
            campoNomeArquivo.setText("");
            areaDeTexto.setForeground(new Color(53,94,59));
            areaDeTexto.setText("\n \n \n \n   ----> CADASTRAR DADOS INICIAIS <----");
            painelDadosIniciais.setVisible(true);
            this.dispose();
        }
        else if(e.getSource() == encerra){
            System.exit(0);
        }
    }

    private ArrayList<Atendimento> carregarDados(String nomeArquivo) throws IOException, ParseException {
        ArrayList<Atendimento> atendimentosCarregados = new ArrayList<>();

        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1);

        if (!"csv".equalsIgnoreCase(extensao) && !"json".equalsIgnoreCase(extensao)) {
            throw new IllegalArgumentException("Extensão de arquivo não suportada: " + extensao);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                Atendimento atendimento = null;
                if ("csv".equalsIgnoreCase(extensao)) {
                    atendimento = criarAtendimento(linha);
                } else if ("json".equalsIgnoreCase(extensao)) {
                    JSONObject jsonAtendimento = new JSONObject(linha);
                    int codigo = jsonAtendimento.getInt("codigo");
                    String dataInicio = jsonAtendimento.getString("dataInicio");
                    int duracao = jsonAtendimento.getInt("duracao");
                    String status = jsonAtendimento.getString("status");
                    int codigoEvento = jsonAtendimento.getInt("codigoEvento");
                    String codEventoS = String.valueOf(codigoEvento);

                    Evento eventoCadastrado = null;

                    for (Evento evento : acmeRescue.getTelaInicialEventos().getEventos()) {
                        boolean eventoVinculado = false;
                        for (Atendimento a : acmeRescue.getAtendimentos()) {
                            if (a.getEvento().getCodigo().equals(codEventoS)) {
                                eventoVinculado = true;
                                break;
                            }
                        }
                        if (!eventoVinculado) {
                            eventoCadastrado = evento;
                        }
                    }

                   atendimento = new Atendimento(codigo, dataInicio, duracao, Estado.PENDENTE, eventoCadastrado);
                }

                atendimentosCarregados.add(atendimento);
            }
        }

        return atendimentosCarregados;
    }

    public void carregarDadosIniciais(String nomeArquivo) {
        try {
            if (nomeArquivo.lastIndexOf('.') == -1) {
                // Se não houver ponto na string, consideramos que o usuário não incluiu a extensão
                String[] extensoes = {"csv", "json"};
                for (String extensao : extensoes) {
                    try {
                        filaAtendimentosPendente.addAll(carregarDados(nomeArquivo + "." + extensao));
                        break;
                    } catch (IOException | ParseException e) {
                        // Se ocorrer erro, tentamos a próxima extensão
                    }
                }
            } else {
                filaAtendimentosPendente.addAll(carregarDados(nomeArquivo));
            }

            areaDeTexto.setForeground(new Color(53, 94, 59));
            areaDeTexto.setText("DADOS CARREGADOS COM SUCESSO!");
        } catch (IOException | ParseException | IllegalArgumentException e) {
            areaDeTexto.setForeground(Color.RED);
            areaDeTexto.setText("Erro: " + e.getMessage());
        }
    }
    private Atendimento criarAtendimento(String linha) throws ParseException {
        String[] dados = linha.split(";");
        //System.out.println("Valor da coluna 0: " + dados[0]);
        int codigo = Integer.parseInt(dados[0].trim());
        String dataInicio = dados[1].trim();
        int duracao = Integer.parseInt(dados[2].trim());
        String status = dados[3].trim();
        int codigoEvento = Integer.parseInt(dados[4].trim());
        String codigoEventoS = String.valueOf(codigoEvento);

        String formatoInvalidoMsg = validarFormatoData(dataInicio);
        if (formatoInvalidoMsg != null) {
            throw new ParseException(formatoInvalidoMsg, 0);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicioFormatada = dateFormat.parse(dataInicio);

        return new Atendimento(codigo, dataInicioFormatada.toString(), duracao, Estado.PENDENTE, codigoEventoS);
    }

    public void exibirDadosLidos() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Atendimentos Pendentes:\n");

        for (Atendimento atendimento : filaAtendimentosPendente) {
            stringBuilder.append(atendimento.toString()).append("\n");
        }

        areaDeTexto.setText(stringBuilder.toString());
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

    public Queue<Atendimento> getFilaAtendimentosPendentes(){return filaAtendimentosPendente;}

    public ACMERescue getAcmeRescue(){return acmeRescue;}

}

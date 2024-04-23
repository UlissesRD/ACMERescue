package ui;

import dadosAtendimento.Atendimento;
import dadosEquipamentos.Equipamento;
import dadosEquipe.Equipe;
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
public class PainelDadosIniciaisEquipe extends JFrame implements ActionListener {
    private JTextField campoNomeArquivo;
    private JButton botaoCarregar;
    private JButton retorna;
    private JButton encerra;
    private JTextArea areaDeTexto;
    private ACMERescue acmeRescue;

    ArrayList<Equipe> equipesCarregadas = new ArrayList<>();

    private PainelDadosIniciais painelDadosIniciais;


    public PainelDadosIniciaisEquipe(PainelDadosIniciais painelDadosIniciais) {
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

        container.add(Box.createVerticalGlue());
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

    private ArrayList<Equipe> carregarDados(String nomeArquivo) throws IOException, ParseException {
        //ArrayList<Equipe> equipesCarregadas = new ArrayList<>();

        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1);

        if (!"csv".equalsIgnoreCase(extensao) && !"json".equalsIgnoreCase(extensao)) {
            throw new IllegalArgumentException("Extensão de arquivo não suportada: " + extensao);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                Equipe equipe = null;
                if ("csv".equalsIgnoreCase(extensao)) {
                    equipe = criarEquipe(linha);
                } else if ("json".equalsIgnoreCase(extensao)) {
                    JSONObject jsonAtendimento = new JSONObject(linha);
                    String codinome = jsonAtendimento.getString("codinome");
                    int quantidade = jsonAtendimento.getInt("quantidade");
                    double latitude = jsonAtendimento.getDouble("latitude");
                    double longitude = jsonAtendimento.getDouble("longitude");


                    equipe = new Equipe(codinome, quantidade, latitude, longitude);
                }

                equipesCarregadas.add(equipe);
            }
        }
        return equipesCarregadas;
    }

    public void carregarDadosIniciais(String nomeArquivo) {
        ArrayList<Equipe> equipesAUX = new ArrayList<>();
        try {
            String[] extensoes = {"csv", "json"};
            for (String extensao : extensoes) {
                try {
                    // Tenta carregar os dados com a extensão atual
                    equipesAUX.addAll(carregarDados(nomeArquivo + "." + extensao));
                    areaDeTexto.setForeground(new Color(53, 94, 59));
                    areaDeTexto.setText("DADOS CARREGADOS COM SUCESSO!");
                    return;  // Sai do método se os dados forem carregados com sucesso
                } catch (IOException | ParseException e) {
                    // Se ocorrer erro, tentamos a próxima extensão
                }
            }

            // Se nenhum dos formatos funcionou, exibe mensagem de erro
            areaDeTexto.setForeground(Color.RED);
            areaDeTexto.setText("Erro: Não foi possível carregar os dados do arquivo.");
        } catch (IllegalArgumentException e) {
            areaDeTexto.setForeground(Color.RED);
            areaDeTexto.setText("Erro: " + e.getMessage());
        }
    }
    private Equipe criarEquipe(String linha) {
        String[] dados = linha.split(";");
        //System.out.println("Valor da coluna 0: " + dados[0]);
        String codinome = dados[0].trim();
        int quantidade = Integer.parseInt(dados[1].trim());
        double latitude = Double.parseDouble(dados[2].trim());
        double longitude = Double.parseDouble(dados[3].trim());

        return new Equipe(codinome, quantidade, latitude, longitude);
    }

    public ArrayList<Equipe> getEquipesCarregadas() {return equipesCarregadas;}

    public ACMERescue getAcmeRescue(){return acmeRescue;}

}

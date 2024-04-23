package ui;

        import dadosAtendimento.Atendimento;
        import dadosEquipamentos.Barco;
        import dadosEquipamentos.CaminhaoTanque;
        import dadosEquipamentos.Equipamento;
        import dadosEquipamentos.Escavadeira;
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
public class PainelDadosIniciaisEquipamento extends JFrame implements ActionListener {
    private JTextField campoNomeArquivo;
    private JButton botaoCarregar;
    private JButton retorna;
    private JButton encerra;
    private JTextArea areaDeTexto;
    private ACMERescue acmeRescue;

    ArrayList<Equipamento> equipamentosCarregados = new ArrayList<>();

    private PainelDadosIniciais painelDadosIniciais;


    public PainelDadosIniciaisEquipamento(PainelDadosIniciais painelDadosIniciais) {
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

    private ArrayList<Equipamento> carregarDados(String nomeArquivo) throws IOException, ParseException {

        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1);

        if (!"csv".equalsIgnoreCase(extensao) && !"json".equalsIgnoreCase(extensao)) {
            throw new IllegalArgumentException("Extensão de arquivo não suportada: " + extensao);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                Equipamento equipamento = null;
                if ("csv".equalsIgnoreCase(extensao)) {
                    equipamento = criarEquipamento(linha);
                } else if ("json".equalsIgnoreCase(extensao)) {
                    JSONObject jsonEquipamento = new JSONObject(linha);
                    int id = jsonEquipamento.getInt("id");
                    String nome = jsonEquipamento.getString("nome");
                    double custoDoDia = jsonEquipamento.getDouble("custoDoDia");
                    int tipo = jsonEquipamento.getInt("tipo");
                    if (tipo == 1){
                        int capacidade = jsonEquipamento.getInt("capacidade");
                        equipamento = new Barco(id, nome, custoDoDia, capacidade);
                    }
                    else if(tipo == 2){
                        double capacidade = jsonEquipamento.getDouble("capacidade");
                        equipamento = new CaminhaoTanque(id, nome, custoDoDia, capacidade);
                    }
                    else if(tipo == 3){
                        String combustivel = jsonEquipamento.getString("combustivel");
                        double carga = jsonEquipamento.getDouble("carga");
                        equipamento = new Escavadeira(id, nome, custoDoDia, combustivel, carga);
                    }
                }

                equipamentosCarregados.add(equipamento);
            }
        }
        return equipamentosCarregados;
    }

    public void carregarDadosIniciais(String nomeArquivo) {
        ArrayList<Equipamento> equipamentosAUX = new ArrayList<>();
        try {
            String[] extensoes = {"csv", "json"};
            for (String extensao : extensoes) {
                try {
                    // Tenta carregar os dados com a extensão atual
                    equipamentosAUX.addAll(carregarDados(nomeArquivo + "." + extensao));
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
    private Equipamento criarEquipamento(String linha) {
        Equipamento equipamento = null;

        String[] dados = linha.split(";");
        int id = Integer.parseInt(dados[0].trim());
        String nome = dados[1].trim();
        double custoDoDia = Double.parseDouble(dados[2].trim());
        String codinome = dados[3].trim();
        int tipo = Integer.parseInt(dados[4].trim());
        if(tipo == 1){
            int capacidade = Integer.parseInt(dados[5].trim());
            equipamento = new Barco(id, nome, custoDoDia, capacidade);
        }
        else if (tipo == 2){
            double capacidade = Double.parseDouble(dados[5].trim());
            equipamento = new CaminhaoTanque(id, nome, custoDoDia, capacidade);
        }
        else if(tipo == 3){
            String combustivel = dados[5].trim();
            double carga = Double.parseDouble(dados[6].trim());
            equipamento = new Escavadeira(id, nome, custoDoDia, combustivel, carga);
        }

        return equipamento;
    }

    public ArrayList<Equipamento> getEquipamentosCarregados() {return equipamentosCarregados;}

    public ACMERescue getAcmeRescue(){return acmeRescue;}

}


package dadosAtendimento;

public enum Estado {
    PENDENTE("PENDENTE"),
    EXECUTANDO("EXECUTANDO"),
    FINALIZADO("FINALIZADO"),
    CANCELADO("CANCELADO");

    private String nome;

    /**
     * Método dadosAtendimento.Estado(String nome) - construtor
     * @param nome
     */
    private Estado(String nome) {
        this.nome = nome;
    }

    /**
     * Método String getNome()
     * @return nome
     */
    public String getNome() {
        return nome;
    }
}

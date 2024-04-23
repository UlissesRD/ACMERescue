package dadosEquipamentos;

public class Equipamento {
	private int id;
	private String nome;
	private double custoDia;

	public Equipamento(int id, String nome, double custoDia) {
		this.id = id;
		this.nome = nome;
		this.custoDia = custoDia;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public double getCustoDia() {
		return custoDia;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public String geraDescricao(){
		return "ID: "+getId()+"; Nome: "+getNome()+"; Custo do dia: "+getCustoDia();
	}

	public String obterDadosFormatados() {
		return "ID: "+getId() +
				", Nome: "+getNome() +
				", Custo do dia: "+getCustoDia();
	}

}

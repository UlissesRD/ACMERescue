package dadosEquipamentos;

public class Barco extends Equipamento {

	private int capacidade;

	public Barco(int id, String nome, double custoDia, int capacidade){
		super(id,nome,custoDia);
		this.capacidade=capacidade;
	}

	public int getCapacidade() {
		return capacidade;
	}

	@Override
	public String geraDescricao() {
		return super.geraDescricao()+" ; Capacidade:"+getCapacidade() +"; Barco";
	}

	@Override
	public String obterDadosFormatados() {
		return super.obterDadosFormatados() +
				", Capacidade: "+getCapacidade() +
				", Barco";
	}
}

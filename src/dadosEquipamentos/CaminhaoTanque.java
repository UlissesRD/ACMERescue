package dadosEquipamentos;

public class CaminhaoTanque extends Equipamento{

	private double capacidade;

	public CaminhaoTanque(int id, String nome, double custoDia,double capacidade){
		super(id,nome,custoDia);
		this.capacidade=capacidade;
	}

	public double getCapacidade() {
		return capacidade;
	}

	@Override
	public String geraDescricao() {
		return super.geraDescricao()+" ; Capacidade:"+getCapacidade() + "; Caminhao Tanque";
	}

	@Override
	public String obterDadosFormatados() {
		return super.obterDadosFormatados() +
				", Capacidade: "+getCapacidade() +
				", Caminhao Tanque";
	}
}

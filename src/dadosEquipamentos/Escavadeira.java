package dadosEquipamentos;

public class Escavadeira extends Equipamento  {

	private String combustivel;

	private double carga;

	public Escavadeira(int id, String nome, double custoDia,String combustivel,double carga){
		super(id, nome, custoDia);
		this.combustivel=combustivel;
		this.carga=carga;
	}

	public double getCarga() {
		return carga;
	}

	public String getCombustivel() {
		return combustivel;
	}

	@Override
	public String geraDescricao() {
		return super.geraDescricao()+" ; Combustivel:"+getCombustivel() +" ; Carga:"+getCarga() +"; Escavadeira";
	}

	@Override
	public String obterDadosFormatados() {
		return super.obterDadosFormatados() +
				", Combustivel: "+getCombustivel() +
				", Carga:"+getCarga() +
				", Escavadeira";
	}
}
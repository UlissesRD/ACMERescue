package dadosEquipe;

import java.util.ArrayList;
import dadosEquipamentos.Equipamento;
public class Equipe {
	private String codinome;
	private int quantidade;
	private double latitude;
	private double longitude;
	private ArrayList<Equipamento> equipamentos;

	public Equipe(String codinome, int quantidade, double latitude, double longitude) {
		this.codinome = codinome;
		this.quantidade = quantidade;
		this.latitude = latitude;
		this.longitude = longitude;
		this.equipamentos = new ArrayList<>();
	}

	public String getCodinome() {
		return codinome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public ArrayList<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	@Override
	public String toString() {
		return getCodinome();
	}

	public String geraDescricaoEquipe(){
		return "Codinome: "+getCodinome()+"; Quantidade: "+getQuantidade()+"; Latitude: "+getLatitude()+" ; Longitude"+getLongitude()+
				"Equipamentos: "+getEquipamentos();
	}

	public String obterDadosFormatados() {
		return "Codinome: "+getCodinome() +
				", Quantidade: "+getQuantidade() +
				", Latitude: "+getLatitude() +
				", Longitude"+getLongitude();
	}
}

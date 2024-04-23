package dadosAtendimento;

import dadosEquipe.Equipe;
import dadosEventos.Evento;

import dadosEquipamentos.Equipamento;

public class Atendimento {
	private int cod;
	private String dataInicio;
	private int duracao;
	private Estado status;
	private Equipe equipe;
	private Evento evento;
	private String codEvento;

	public Atendimento(int cod, String dataInicio, int duracao, Estado status, Equipe equipe, Evento evento) {
		this.cod = cod;
		this.dataInicio = dataInicio;
		this.duracao = duracao;
		this.status = status;
		this.equipe = equipe;
		this.evento = evento;
	}

	public Atendimento(int cod, String dataInicio, int duracao, Estado status, Evento evento) {
		this.cod = cod;
		this.dataInicio = dataInicio;
		this.duracao = duracao;
		this.status = status;
		this.evento = evento;
	}

	public Atendimento(int cod, String dataInicio, int duracao, Estado status, String codEvento) {
		this.cod = cod;
		this.dataInicio = dataInicio;
		this.duracao = duracao;
		this.status = status;
		this.codEvento = codEvento;
	}

	public int getCodigo() {
		return cod;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public int getDuracao() {
		return duracao;
	}

	public Estado getStatus() {
		return status;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public Evento getEvento() {
		return evento;
	}

	public String getCodEvento() {
		return codEvento;
	}

	public double calculaCusto() {
		double custo = calculaCustoDaEquipe() + calculaCustoEquipamentos() + calculaCustoDeDeslocamento();
		return custo;
	}

	public double calculaCustoDaEquipe() {
		double custoEquipe = duracao * 250 * equipe.getQuantidade();
		return custoEquipe;
	}

	public double calculaCustoEquipamentos(){
		double custoGeral = 0;
		for (Equipamento equipamento : equipe.getEquipamentos()) {
			custoGeral += equipamento.getCustoDia();
		}
		double custoEquipamentos = duracao * custoGeral;
		return custoEquipamentos;
	}

	public double calculaCustoDeDeslocamento(){
		double custoTotal = 0.0;
		double RaioTerra = 6371;
		double lat1Rad = Math.toRadians(evento.getLatitude());
		double lat2Rad = Math.toRadians(equipe.getLatitude());
		double deltaLat = Math.toRadians(equipe.getLatitude() - evento.getLatitude());
		double deltaLon = Math.toRadians(equipe.getLongitude() - evento.getLongitude());

		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1Rad) *
		    		Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distancia = 0.0;
		distancia = RaioTerra * c;

		if (distancia > 5.000){
			throw new IllegalArgumentException("Distancia excedida. Mais de 5.000km");
		}

		double custoEquipamentos = 0.0;
		for (Equipamento equipamento : equipe.getEquipamentos()) {
			custoEquipamentos += equipamento.getCustoDia();
		}

		custoTotal = distancia * (100 * equipe.getQuantidade() + custoEquipamentos);
		return custoTotal;
	}

	public String dadosAtendimento(){
		return "Codigo: "+getCodigo()+"; Data de Inicio: "+getDataInicio()+"; Duracao: "+getDuracao()+" ; Status: "+getStatus()
				+" ; Evento: "+getCodEvento();
	}

	public String getCodigoEvento() {
		return evento.getCodigo();
	}

	public String obterDadosFormatados() {
		return "Código: " + getCodigo() +
				", Data de Início: " + getDataInicio() +
				", Duração: " + getDuracao() +
				", Estado: " + getStatus() +
				", Código do Evento: " + getCodEvento();
	}
}


package dadosEventos;

import dadosAtendimento.Atendimento;
import ui.ACMERescue;

public class Evento {

    private String codigo;

    private String data;

    private double latitude;

    private ACMERescue acmeRescue;

    private double longitude;

    public Evento(String codigo, String data, double latitude, double longitude) {
        this.codigo = codigo;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getData() {
        return data;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String geraDescricao(){
        return "Codigo: "+getCodigo()+"; Data: "+getData()+"; Latitude "+getLatitude()+" ; Longitude"+getLongitude();
    }
}
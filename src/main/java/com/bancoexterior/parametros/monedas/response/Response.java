package com.bancoexterior.parametros.monedas.response;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private T Data;
    private String codigo;
	private String descripcion;
    private String Message = "";

    @Override
    public String toString() {
        return "Response{" +
                "Data=" + Data +
                ", codigo=" + codigo +
                ", descripcion=" + descripcion +
                ", Message='" + Message + '\'' +
                '}';
    }
}
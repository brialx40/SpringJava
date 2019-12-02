package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cuenta_registrada database table.
 * 
 */
@Entity
@Table(name="cuenta_registrada")
@NamedQuery(name="CuentaRegistrada.findAll", query="SELECT c FROM CuentaRegistrada c")
public class CuentaRegistrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cure_id")
	private Long cureId;

	private String activa;

	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="usu_creador")
	private String usuCreador;

	@Column(name="usu_modificador")
	private String usuModificador;

	//bi-directional many-to-one association to Cliente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="clie_id")
	private Cliente cliente;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cuen_id")
	private Cuenta cuenta;

	public CuentaRegistrada() {
	}

	public Long getCureId() {
		return this.cureId;
	}

	public void setCureId(Long cureId) {
		this.cureId = cureId;
	}

	public String getActiva() {
		return this.activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuCreador() {
		return this.usuCreador;
	}

	public void setUsuCreador(String usuCreador) {
		this.usuCreador = usuCreador;
	}

	public String getUsuModificador() {
		return this.usuModificador;
	}

	public void setUsuModificador(String usuModificador) {
		this.usuModificador = usuModificador;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cuenta getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

}
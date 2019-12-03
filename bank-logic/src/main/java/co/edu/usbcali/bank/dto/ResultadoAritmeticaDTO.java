package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

public class ResultadoAritmeticaDTO {
	
	private BigDecimal max;
	private BigDecimal min;
	private Double avg;
	private Long count;
	
	public ResultadoAritmeticaDTO() {
		super();
	}
	public ResultadoAritmeticaDTO(BigDecimal max, BigDecimal min, Double avg, Long count) {
		super();
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.count = count;
	}
	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}
	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	

}

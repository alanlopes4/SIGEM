package br.com.sigem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChaveProduto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private Long lote;

	public ChaveProduto(Long id, Long lote) {
		this.id = id;
		this.lote = lote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLote() {
		return lote;
	}

	public void setLote(Long lote) {
		this.lote = lote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChaveProduto))
			return false;
		ChaveProduto other = (ChaveProduto) obj;
		
		return id.equals(other.id) && lote.equals(other.lote);
	}

	
	
}

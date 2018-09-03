package br.com.sigem.util;

public abstract class VerificacaoSQLInjection {

	protected VerificacaoSQLInjection sucessor;
	
	public void setSucessor(VerificacaoSQLInjection sucessor) {
		this.sucessor = sucessor;
	}
	
	public abstract void processarRequisicao(String login, String senha);
	
}

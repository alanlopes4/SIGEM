package br.com.sigem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigem.model.Permissao;
import br.com.sigem.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	
	/**
	 * Retorna uma permissão de acordo com o nome passado como parâmetro
	 * @param nome
	 * @return
	 */
	public Permissao buscarPorNome(String nome) {
		return permissaoRepository.filtrar(nome);
		
	}

}

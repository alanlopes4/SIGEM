package br.com.sigem.controller;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sigem.security.Conexao;
import br.com.sigem.service.HistoricoPrecoService;
import br.com.sigem.util.GeradorRelatorio;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
@RequestMapping("/sigem/relatorios/historicoPreco")
public class HistoricoPrecoController {
	
	
	@Autowired
	private HistoricoPrecoService historicoPrecoService;
	
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/historicoPreco/index");
		mv.addObject("historicos", historicoPrecoService.listarTodos());
		return mv;
	}
	
	
	@PostMapping("/filtrar")
	public ModelAndView filtrar(@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		ModelAndView mv = new ModelAndView("relatorio/historicoPreco/index");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			mv.addObject("historicos", historicoPrecoService.filtrar(localDateInicio, localDateFim));
			return mv;
		}
		else {
			mv.addObject("historicos", historicoPrecoService.listarTodos());
			return mv;
		}
		
	}
	
	@PostMapping("/gerarPdf")
	public ModelAndView gerarPdf(@RequestParam("dataInicioModal") String dataInicio, @RequestParam("dataFimModal") String dataFim) {
		
		String nomeArquivo = "historico_preco.jasper";
		String caminho = new File("./").getAbsolutePath(); 
		caminho = caminho.substring(0, caminho.length() - 1);
		
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data_inicio, data_final;
		
		try {
			data_inicio = sdf.parse(dataInicio);
			data_final = sdf.parse(dataFim);
			params.put("DATA_INICIO", data_inicio);
			params.put("DATA_FIM", data_final);
			GeradorRelatorio geradorRelatorio = new GeradorRelatorio(nomeArquivo, params, Conexao.getConnection());
			geradorRelatorio.gerarPDFParaOutputStream(new SimpleOutputStreamExporterOutput(caminho+"histórico_de_preços.pdf"));
			
			return new ModelAndView("redirect:/sigem/relatorios/historicoPreco").addObject("gerado", true);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/sigem/relatorios/historicoPreco").addObject("error", true);
	}

	
}

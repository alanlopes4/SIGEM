package br.com.sigem.controller;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DecimalFormat;

import br.com.sigem.model.relatorio.EntradaProduto;
import br.com.sigem.model.relatorio.HistoricoRetiradaProduto;
import br.com.sigem.security.Conexao;
import br.com.sigem.service.EntradaProdutoService;
import br.com.sigem.service.HistoricoRetiradaProdutoService;
import br.com.sigem.util.GeradorRelatorio;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
@RequestMapping("/sigem/relatorios/retiradaProduto")
public class RetiradaProdutoController {
	
	@Autowired
	private HistoricoRetiradaProdutoService retiradaProdutoService;
	
	
	@GetMapping 
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("relatorio/retiradaProduto/index");
		
		List<HistoricoRetiradaProduto> retirados = retiradaProdutoService.listarTodos();
	    
		mv.addObject("retirados", retirados);
		
		return mv;
	}
	
	
	@PostMapping("/filtrar")
	public ModelAndView filtrar(@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		ModelAndView mv = new ModelAndView("relatorio/retiradaProduto/index");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			List<HistoricoRetiradaProduto> retirados = retiradaProdutoService.filtrar(localDateInicio, localDateFim);
			mv.addObject("retirados", retirados);
			
		}else {
			List<HistoricoRetiradaProduto> retirados = retiradaProdutoService.listarTodos();
			mv.addObject("retirados", retirados);
		}
		return mv;
	}
	
	@PostMapping("/gerarPdf")
	public ModelAndView gerarPdf(@RequestParam("dataInicioModal") String dataInicio, @RequestParam("dataFimModal") String dataFim) {
		
		String nomeArquivo = "retirada_produtos.jasper";
		String caminho = new File("./").getAbsolutePath(); 
		//System.err.println("CAMINHO GERADO:"+caminho);
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
			geradorRelatorio.gerarPDFParaOutputStream(new SimpleOutputStreamExporterOutput(caminho+"retirada_produtos.pdf"));
			
			return new ModelAndView("redirect:/sigem/relatorios/retiradaProduto").addObject("gerado", true);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/sigem/relatorios/retiradaProduto").addObject("error", true);
	}
	
	@PostMapping("/gerarGrafico")
	public ModelAndView gerarGrafico(@RequestParam("dataInicioModal") String dataInicio, @RequestParam("dataFimModal") String dataFim, @RequestParam("motivo") String motivo) {
		
		
		ModelAndView mv = new ModelAndView("relatorio/retiradaProduto/grafico");
		LocalDate localDateInicio, localDateFim;
		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			localDateInicio = LocalDate.parse(dataInicio, formatter);
			localDateFim = LocalDate.parse(dataFim, formatter);
			List<Object[]> dados = retiradaProdutoService.filtrar(localDateInicio, localDateFim,  motivo);
		
			mv.addObject("dados", dados);
			mv.addObject("motivo", motivo);
			mv.addObject("dataInicio", formatter.format(localDateInicio));
			mv.addObject("dataFim", formatter.format(localDateInicio));
			
		}else {
			return new ModelAndView("redirect:/sigem/relatorios/retiradaProduto").addObject("error", true);
		}
		
		
		return mv;
	}
	
	
}

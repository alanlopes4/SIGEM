package br.com.sigem.util;


import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import java.sql.Connection;

public class GeradorRelatorio {

	private String nomeArquivo;
	private Map<String, Object> parametros;
	private Connection connection;
	
	public GeradorRelatorio(String nomeArquivo, Map<String, Object> parametros, Connection connection) {
		
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
		this.connection = connection;
	}
	
	public void gerarPDFParaOutputStream(OutputStreamExporterOutput destinoSaida) {
			
		JasperPrint print;
		
		try {
			print = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.connection);

	        JRPdfExporter exporter = new JRPdfExporter();
	        ExporterInput exporterInput = new SimpleExporterInput(print);
	        exporter.setExporterInput(exporterInput);	 
	        OutputStreamExporterOutput exporterOutput = destinoSaida;
	        exporter.setExporterOutput(exporterOutput);
	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
			
			
		} catch (JRException e) {
			e.printStackTrace();
		} 

	}
	
}

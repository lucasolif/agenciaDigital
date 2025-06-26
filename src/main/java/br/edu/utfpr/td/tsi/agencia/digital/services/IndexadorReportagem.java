package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;


@Component
public class IndexadorReportagem {
	
    @Value("${solr.url}")
    private String solrUrl;

	public void indexar(List<Reportagem> reportagens) {
		Http2SolrClient solr = new Http2SolrClient.Builder(solrUrl).build();

		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		
		for (Reportagem report : reportagens) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", report.getId());
			document.addField("descricao", report.getDescricao());
			document.addField("titulo", report.getTitulo());
			documents.add(document);
		}

		try {
			solr.add(documents);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> procurar(String termo) {
		
		List<String> ids = new ArrayList<String>();
		Http2SolrClient solr = new Http2SolrClient.Builder(solrUrl).build();

	    String termoTratado = termo.replaceAll("([+\\-!(){}\\[\\]^\"~*?:\\\\]|&&|\\|\\|)", "\\\\$1");
	    String queryConsulta = String.format("titulo:*%s* OR descricao:*%s*", termoTratado, termoTratado);
		
		SolrQuery query = new SolrQuery();
		query.set("q", queryConsulta);
		
		QueryResponse response;
		
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String idReportagem = doc.getFieldValue("id").toString();
				ids.add(idReportagem);
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}

		solr.close();
		return ids;
		

	}

}

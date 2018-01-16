package cn.imust.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import cn.imust.dao.ProductDao;
import cn.imust.pojo.ProductModel;
import cn.imust.pojo.ResultModel;
@Repository
public class ProductDaoImpl implements ProductDao {

	public ResultModel getProduct(SolrQuery query) throws Exception {
		String url = "http://localhost:8080/solr";
		HttpSolrServer solrServer = new HttpSolrServer(url);
		
		QueryResponse response = solrServer.query(query);
		
		SolrDocumentList results = response.getResults();
		long total = results.getNumFound();
		
		ArrayList<ProductModel> products = new ArrayList<ProductModel>();
		for (SolrDocument solrDocument : results) {
			ProductModel model = new ProductModel();
			model.setPid(String.valueOf(solrDocument.get("id")));
			
			model.setPrice(Float.parseFloat(String.valueOf(solrDocument.get("product_price"))));
			model.setPicture(String.valueOf(solrDocument.get("product_picture")));
			model.setCatalog_name(String.valueOf(solrDocument.get("product_catalog_name")));
			
			//高亮显示
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			List<String> ls = map.get(solrDocument.get("id")).get("product_name");
			if(ls != null){
				model.setName(ls.get(0));
			}else{
				model.setName(String.valueOf(solrDocument.get("product_name")));
			}
			
			products.add(model);
		}
		
		ResultModel resultModel = new ResultModel();
		resultModel.setProductList(products);
		resultModel.setRecordCount(total);
		
		return resultModel;
	}

}

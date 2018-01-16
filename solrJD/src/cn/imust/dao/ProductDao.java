package cn.imust.dao;


import org.apache.solr.client.solrj.SolrQuery;

import cn.imust.pojo.ResultModel;

public interface ProductDao {
	
	public ResultModel getProduct(SolrQuery query) throws Exception;
	
}

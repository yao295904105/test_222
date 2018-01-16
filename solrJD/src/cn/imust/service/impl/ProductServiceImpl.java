package cn.imust.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.imust.dao.ProductDao;
import cn.imust.pojo.ResultModel;
import cn.imust.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Integer SIZE = 20;
	@Autowired
	private ProductDao productDao;
	
	public ResultModel getProducts(String queryString, String catalog_name, String price, Integer page,
			Integer sort) throws Exception {
		
		SolrQuery query = new SolrQuery();
		query.set("df", "product_keywords");
		if(StringUtils.isNotEmpty(queryString)){
			query.setQuery(queryString);
		}else{
			query.setQuery("*:*");
		}
		if(StringUtils.isNotEmpty(catalog_name)){
			query.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		if(StringUtils.isNotEmpty(price)){
			String[] split = price.split("-");
			query.addFilterQuery("product_price:["+split[0]+" TO "+split[1]+"]");
		}
		
		if(!"1".equals(String.valueOf(sort))){
			query.setSort("product_price", ORDER.asc);
		}else{
			query.setSort("product_price",ORDER.desc);
		}
		if(page == null){
			page = 1;
		}
		
		//设置开始
		query.setStart((page -1)*SIZE);
		//设置size
		query.setRows(SIZE);
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<span style = \"color:red\">");
		query.setHighlightSimplePost("</span>");
		
		ResultModel resultModel = productDao.getProduct(query);
		
		Integer pageCount = Integer.parseInt(String.valueOf(resultModel.getRecordCount()))/SIZE;
		if(Integer.parseInt(String.valueOf(resultModel.getRecordCount()))%SIZE > 0){
			pageCount++;
		}
		resultModel.setPageCount(pageCount);
	
		resultModel.setCurPage(page);
		
		return resultModel;
	}

}

package cn.imust.service;


import cn.imust.pojo.ResultModel;

public interface ProductService {

	public ResultModel getProducts(String queryString, String catalog_name, String price, Integer page,
			Integer sort)throws Exception;
}

package cn.imust.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.imust.pojo.ResultModel;
import cn.imust.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("list")
	public String toList(String queryString,String catalog_name,String price,Integer page,Integer sort,Model model) throws Exception{
		
		ResultModel productList = productService.getProducts(queryString,catalog_name,price,page,sort);
		model.addAttribute("result", productList);
		model.addAttribute("price", price);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("sort", sort);
		model.addAttribute("queryString", queryString);
		return "product_list";
	}
}

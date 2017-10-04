package kr.or.phonejob.ShopController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
생성일자 : 2017-10-04
작성자 : 이상원
문서정보 : shop Controller

*/

@Controller
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	//shop 페이지 오픈
	@RequestMapping(value="/goShop.do", method=RequestMethod.GET)
	public String goShop(){
		String url ="shop.shopMain";
		return url;
	}

	public String phoneDetail(){

		return null;
	}

}

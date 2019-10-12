package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enums.KEY_ENUM;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	//我是后台的Controller
	@Autowired
	private ItemService itemService;
	
	//查询商品信息
	@RequestMapping("/findItemById/{itemId}")
	@Cache_Find(keyType = KEY_ENUM.AUTO)
	public Item findItemById(@PathVariable Long itemId) {
		Item item = itemService.findItemById(itemId);
		return item;
	}
	
	//查询商品详情信息
	@RequestMapping("/findItemDescById/{itemId}")
	@Cache_Find(keyType = KEY_ENUM.AUTO)
	public ItemDesc	 findItemDescById(@PathVariable Long itemId) {
		
		return itemService.findItemDescById(itemId);
	}
	
}





























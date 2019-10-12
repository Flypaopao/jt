package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private HttpClientService httpClient;

	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById/"+ itemId;
		String itemJson = httpClient.doGet(url);
		Item result =  ObjectMapperUtil.toObject(itemJson, Item.class);
		return result;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+ itemId;
		String itemJson = httpClient.doGet(url);
		ItemDesc result =  ObjectMapperUtil.toObject(itemJson, ItemDesc.class);
		return result;
	}
	
}

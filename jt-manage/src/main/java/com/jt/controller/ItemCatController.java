package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enums.KEY_ENUM;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/queryItemName")
	public String queryItemName(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	
	/**
	 * 实现商品类目树
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@Cache_Find(key = "ITEM_CAT_",keyType = KEY_ENUM.AUTO)
	public List<EasyUI_Tree> findItemCatByParentId(
		@RequestParam(name = "id",defaultValue = "0") 
		Long parentId) {
		
		//该方法查询缓存
		//return itemCatService.findItemCatByCache(parentId);
		
		return itemCatService.findItemCatByParentId(parentId);
	}
	
	
}






















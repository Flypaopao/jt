package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enums.KEY_ENUM;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("query")
	@ResponseBody
	@Cache_Find(key = "ITEM_",keyType = KEY_ENUM.EMPTY)
	public EasyUI_Table name(Integer page,Integer rows) {
		EasyUI_Table table = itemService.findItemByPage(page, rows);
		return table;
	}
	
	/**
	 * 实现商品新增
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item , ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	/**
	 * 修改商品信息
	 * @param item
	 * @return
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	
	/**
	 * 批量删除商品
	 */
	@RequestMapping("/delete")
	public SysResult deleteItems(Long[] ids) {
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	
	/**
	 * 商品下架
	 */
	@RequestMapping("/instock")
	public SysResult itemInstock(Long[] ids) {
		int status = 2; //表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	
	/**
	 * 商品上架
	 */
	@RequestMapping("/reshelf")
	public SysResult itemReshelf(Long[] ids) {
		int status = 1; //表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	
	/**
	 * 回显商品描述
	 */
	@RequestMapping("/query/item/desc/{id}")
	public SysResult queryItemDesc(@PathVariable Integer id) {
		System.out.println("id:"+id);
		ItemDesc itemDesc = itemService.queryItemDesc(id);
		return SysResult.success(itemDesc);
	}
}














package com.jt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.EasyUI_Tree;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUI_Table findItemByPage(Integer page, Integer rows) {
		
		//1.获取总记录数
		int total = itemMapper.selectCount(null);
		
		//2.分页查询List集合
		int start = (page-1)*rows;
		List<Item> listItem = itemMapper.findItemByPage(start,rows);
		EasyUI_Table table = new EasyUI_Table(total,listItem);
		return table;
	}

	/**
	 * rollbackFor 指定异常类型回滚
	 * rollbackFor = IOException.class
	 * noRollbackFor = 指定异常不回滚
	 */
	@Transactional(rollbackFor = IOException.class)//添加事务控制
	@Override
	public void saveItem(Item item , ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//因为商品详情与商品id一致，但是item数据是主键自增
		//自增只有入库之后才能获取主键信息，问如何获取Id?
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		
	}

	@Transactional
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		UpdateWrapper<ItemDesc> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("item_id", item.getId());
		itemDescMapper.update(itemDesc, updateWrapper);
	}

	/**
	 * 1.自己手写sql
	 * delete from tb_item where id in (1,3,4)
	 * 2.使用mybatis-plus
	 */
	@Override
	public void deleteItems(Long[] ids) {
		//1.手写sql 复习mybatis用法
		//itemMapper.deleteItems(ids);
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
		
	}

	@Transactional
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status)
			.setUpdated(new Date());
		List<Long> longList = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.in("id", longList);
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public ItemDesc queryItemDesc(Integer id) {
		ItemDesc itemDesc = itemDescMapper.selectById(id);
		return itemDesc;
	}

	@Override
	public Item findItemById(Long itemId) {
		System.out.println("查询数据库");
		return itemMapper.selectById(itemId);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		System.out.println("查询数据库");
		return itemDescMapper.selectById(itemId);
	}

	
}

















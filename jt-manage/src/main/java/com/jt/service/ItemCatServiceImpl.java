package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUI_Tree;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired(required = false)
	private ItemCatMapper itemCatMapper;
	
	//@Autowired
	//private Jedis jedis;	//从哨兵池中动态获取
	//@Autowired
	//private ShardedJedis jedis;
	@Autowired
	private JedisCluster JedisCluster;
	
	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}

	/**
	 * List<EasyUI_Tree>	返回的是VO对象集合
	 * List<ItemCat>		返回ItemCat集合对象
	 */
	@Override
	public List<EasyUI_Tree> findItemCatByParentId(Long parentId){
		//SELECT id FROM tb_item_cat WHERE parent_id = 0
		List<EasyUI_Tree> treeList = new ArrayList<>();
		//1.获取数据库数据
		List<ItemCat> itemCatList = findItemCatList(parentId);
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			//一级二级菜单 closed 三级菜单 open
			String state = 
					itemCat.getIsParent()?"closed":"open";
			EasyUI_Tree tree = new EasyUI_Tree(id, text, state);			
			treeList.add(tree);
		}
		return treeList;
	}

	public List<ItemCat> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUI_Tree> findItemCatByCache(Long parentId) {
		List<EasyUI_Tree> treeList = new ArrayList<>();
		String key = "ITEM_CAT_"+parentId;
		System.out.println(key);
		String result = JedisCluster.get(key);
		if (StringUtils.isEmpty(result)) {
			//表示缓存中没有数据，应该查询数据库
			treeList = findItemCatByParentId(parentId);
			//将对象转化为json
			String json = 
					ObjectMapperUtil.toJSON(treeList);
			//将对象保存到缓存中
			JedisCluster.set(key, json);
			System.out.println("查询数据库");
		}else {
			treeList =ObjectMapperUtil.toObject(result, treeList.getClass());
			System.out.println("查询缓存");
		}
		return treeList;
	}
}
















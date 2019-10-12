package com.jt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemCatTest {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private ItemCatService itemCatService;
	@Test
	public void test1() {
		List<Item> findItemByPage = itemMapper.findItemByPage(0, 20);
		System.out.println(findItemByPage);
	}
	
	@Test
	public void test2() {
		//SELECT id FROM tb_item_cat WHERE parent_id = 0
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", 0);
		List<ItemCat> idList = itemCatMapper.selectList(queryWrapper);
		ItemCat[] array = {};
		ItemCat[] catList = idList.toArray(array);
		for (ItemCat itemCat : catList) {
			System.out.println(itemCat);
		}
	}
	
	@Test
	public void test3() {
		List<EasyUI_Tree> list = itemCatService.findItemCatByParentId(0l);
		System.out.println(list);
	}
}

















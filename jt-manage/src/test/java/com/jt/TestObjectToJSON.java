package com.jt;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

public class TestObjectToJSON {
	
	private ObjectMapper mapper =new ObjectMapper();
	
	//将对象转化为JSON串
	@Test
	public void toJSON() {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
				.setItemDesc("我是一个测试用例")
				.setCreated(new Date())
				.setUpdated(new Date());
		try {
			String json = 
					mapper.writeValueAsString(itemDesc);
			System.out.println(json);
			
			
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//将json转化为对象类型	反射机制(按照模板类转化)
			try {
				ItemDesc itemDesc2 = mapper.readValue(json, ItemDesc.class);
				System.out.println(itemDesc2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1() {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
				.setItemDesc("我是一个测试用例")
				.setCreated(new Date())
				.setUpdated(new Date());
		String json = ObjectMapperUtil.toJSON(itemDesc);
		System.out.println(json);
		
		ItemDesc object = ObjectMapperUtil.toObject(json, ItemDesc.class);
		System.out.println(object);
	}
}




















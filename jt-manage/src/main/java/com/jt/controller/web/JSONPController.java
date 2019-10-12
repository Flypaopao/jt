package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	
	/**
	 * 参数说明：
	 * function:法币回调函数名称
	 * value:代表服务器返回的数据
	 * 
	 */
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP(String callback) {
		
		ItemCat itemCat = new ItemCat();
		itemCat.setId(2000L).setName("JSONP简化写法!!");
		JSONPObject object = new JSONPObject(callback, itemCat);
		return object;
	}
	

	/**
	 * jsonp返回值结果，必须经过特殊格式封装
	 *     调用者::回调函数获取
	 *     数据返回::封装数据		callback(JSON串)
	 * http://manage.jt.com/web/testJSONP
	 * ?callback=jQuery111105321683162391135_1563264261330&_=1563264261331
	 */
	/*
	 * @RequestMapping("/web/testJSONP") public String testJSONP(String callback) {
	 * 
	 * ItemDesc itemDesc = new ItemDesc();
	 * itemDesc.setItemDesc("JSONP测试跨域").setItemId(12L); String json =
	 * ObjectMapperUtil.toJSON(itemDesc); return callback+"("+json+")"; }
	 */
}

package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item")
@Data
@Accessors(chain = true)
//如果get/set方法不全，添加该注解忽略转化
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends BasePojo{
	@TableId(type = IdType.AUTO)
	private Long id;  			//定义主键
	private String title;       //标题
	private String sellPoint; 	//卖点信息
	private Long price;			//商品价格  1.double有精度问题0.8888888..+0.11111112=0.999999
								//2.速度 int > long > double  将商品扩大100倍保存
	private Integer num;		//商品数量
	private String barcode;		//二维码
	private String image;		//商品图片信息
	private Long cid;			//商品分类信息
	private Integer status;		//商品状态	'默认值为1，可选值：1正常，2下架，3删除',
	
	//el表达式获取数据是获得对象的get方法
	public String[] getImages() {
		return image.split(",");
	}
	
	
	
	
	
	
	
}













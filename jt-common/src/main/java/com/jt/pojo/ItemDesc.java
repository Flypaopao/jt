package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "tb_item_desc")
public class ItemDesc extends BasePojo{
	//item表id与itemDesc表id一致的
	@TableId
	private Long itemId;
	private String itemDesc;
	@Override
	public String toString() {
		return "ItemDesc [itemId=" + itemId + ", itemDesc=" + itemDesc + ", getCreated()=" + getCreated()
				+ ", getUpdated()=" + getUpdated() + "]";
	}
	
	
}

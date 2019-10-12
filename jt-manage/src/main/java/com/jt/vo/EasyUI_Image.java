package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUI_Image {
	private Integer error = 0; 	//表示用户上传文件是否有错 0表示没错 1表示有错
	private String url;			//图片的虚拟路径
	private Integer width;		//宽度
	private Integer height;		//高度
	/*
	 * 多系统之间对象直接传递时必须序列化
	 * manage.jt.com	后台系统 EasyUI_Image~~序列化~~
	 */
	
	
}






















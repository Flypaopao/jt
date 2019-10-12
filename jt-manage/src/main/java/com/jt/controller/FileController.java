package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.service.FileTestService;
import com.jt.vo.EasyUI_Image;

@Controller  //跳转页面时使用
public class FileController {
	@Autowired
	private FileService fileService;
	@Autowired
	private FileTestService fileTestService;
	
	/**
	 * 1.确定url请求路径
	 * http://localhost:8091/file
	 * 2.获取用户提交参数
	 * fileImage
	 * 3.响应合适的页面
	 * 
	 * 业务要求：
	 * 	将文件上传到D:\1-JT\images\goodgoodstudy.jpg
	 * 
	 * 默认条件图片大小不要超过1M 
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取图片名称	
		String fileName = fileImage.getOriginalFilename();
		
		//判断文件夹是否存在
		File fileDir = new File("D:/1-JT/images");
		if (!fileDir.exists()) {
			//表示文件不存在
			fileDir.mkdirs();
		}
		
		File file = new File("D:/1-JT/images/"+fileName);
		//1.实现文件上传API
		fileImage.transferTo(file);
		//2.要求重定向到file.jsp
		return "redirect:/file.jsp";
	}
	
	/*
	 * 实现用户文件上传
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody	//将数据转化为Json
	public EasyUI_Image fileUpLoad(MultipartFile uploadFile) {
		return fileService.fileUpload(uploadFile);
	}

}















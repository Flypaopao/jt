package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUI_Image;

@Service
//加载配置文件，
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	
	/**
	 * 由于成员变量将路径写死 扩展不易 最好方式
	 * 应该写到配置文件中动态获取
	 *  YML properties
	 */
	@Value("${image.localDirPath}")
	private String localDirPath ; //定义本地磁盘路径
	@Value("${image.urlDirPath}")
	private String urlDirPath;
	
	/**
	 * 问题1:校验文件类型是否为图片  如何校验?
	 * 	 利用后缀校验
	 * 问题2:如何防止恶意文件上传  ?
	 * 	  将文件交给工具API校验从中获取宽高
	 * 问题3:众多图片如何保存?
	 * 	 分文件存储：按照yyyy/MM/dd
	 * 问题4：文件如果重名如何处理?
	 * 	 自定义UUID为文件名称
	 * 文件上传思路:
	 * 	1.获取用户文件名称进行校验
	 * 	2.校验文件名称是否为图片
	 * 	3.利用工具API读取用户提交数据
	 * 	4.以时间格式创建文件夹保存数据 yyyy/MM/dd
	 * 	5.判断文件夹是否存在 不存在 新建文件目录
	 * 	6.采用UUID为文件名称，防止文件重名 32位16进制数
	 */
	@Override
	public EasyUI_Image fileUpload(MultipartFile uploadFile) {
		EasyUI_Image ui_Image = new EasyUI_Image();
		//1.获取文件名称 abc.jpg
		String fileName = uploadFile.getOriginalFilename();
		//2.校验文件名称   
		fileName = fileName.toLowerCase(); //值将字符转换为小写
		if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			ui_Image.setError(1);
			return ui_Image;
		}
		
		//3.利用工具API读取用户提交数据
		try {
			BufferedImage bufferedImage = 
					ImageIO.read(uploadFile.getInputStream());
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			//如果有一项为0表示不是图片
			if (height==0 || width==0) {
				ui_Image.setError(1);
				return ui_Image;
			}
			//封装图片数据
			ui_Image.setHeight(height)
					.setWidth(width);
			
			//4.以时间格式创建文件夹 D:/1-JT/images/yyyy/MM/dd
			String datePathDir = 
			new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			
			String realDirPath = localDirPath + datePathDir;
			
			//5.判断文件夹是否存在 不存在 新建文件目录
			File dirFile = new File(realDirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			
			//6.采用UUID命名文件名称 替换中间的-
			String uuid = UUID.randomUUID()
							  .toString()
							  .replace("-", "");
			//截串 含头不含尾		abc.jpg
			String fileType = 
			fileName.substring(fileName.lastIndexOf("."));
			String realNmae = uuid + fileType;
			
			//7.文件上传
			String realFilePath = realDirPath + "/" + realNmae;
			
			//8.编辑虚拟路径数据返回
			//http://image.it.com/yyyy/MM/dd/
			String realUrlPath = urlDirPath + datePathDir + "/" + realNmae;
			ui_Image.setUrl(realUrlPath);
			
			uploadFile.transferTo(new File(realFilePath));
			System.out.println("文件上传成功！！！！！");
		} catch (Exception e) {
			e.printStackTrace();
			ui_Image.setError(1);	//对象转化时异常
			return ui_Image;
		}
		
		return ui_Image;
	}

	
}
































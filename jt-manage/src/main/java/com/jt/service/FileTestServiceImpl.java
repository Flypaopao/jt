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
@PropertySource("classpath:/properties/image.properties")
public class FileTestServiceImpl implements FileTestService{

	@Value("${image.localDirPath}")
	private String localDirPath;
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
		EasyUI_Image uiImage = new EasyUI_Image();
		String fielName = uploadFile.getOriginalFilename();
		fielName = fielName.toLowerCase();
		if (!fielName.matches("^.+\\.(img|png|jpg)$")) {
			uiImage.setError(1);
			return uiImage;
		}
		
		try {
			BufferedImage bufferedImage = 
			ImageIO.read(uploadFile.getInputStream());
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			if (height==0||width==0) {
				uiImage.setError(1);
				return uiImage;
			}
			uiImage.setHeight(height)
				   .setWidth(width);
			String fileDirPath = 
			new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String realDirPath = localDirPath + fileDirPath;
			File dirPath = new File(realDirPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String uuId = UUID.randomUUID()
							  .toString()
							  .replace("-", "");
			String fileType = fielName.substring(fielName.lastIndexOf("."));
			String realFileName = uuId + fileType;
			String realFilePath = realDirPath + "/" + realFileName;
			
			uiImage.setUrl("https://item.jd.com/100000116865.html");
			uploadFile.transferTo(new File(realFilePath));
			
		} catch (Exception e) {
			e.printStackTrace();
			uiImage.setError(1);
			return uiImage;
		}
		
		return uiImage;
	}

}























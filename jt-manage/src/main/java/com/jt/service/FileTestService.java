package com.jt.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUI_Image;

public interface FileTestService {
	
	EasyUI_Image fileUpload(MultipartFile uploadFile);
}

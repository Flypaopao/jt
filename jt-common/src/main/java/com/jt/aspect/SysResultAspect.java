package com.jt.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

//@ControllerAdvice		//针对Controller层生效
@RestControllerAdvice
public class SysResultAspect {
	/**
	 * 如果程序报错,则统一返回系统异常信息
	 * SysResult.fail()
	 */
	@ExceptionHandler(RuntimeException.class)
	public SysResult sysResultFail(Exception e) {
		System.out.println("~~~~~~~服务器异常:"+e.getMessage());
		return SysResult.fail();
	}
}

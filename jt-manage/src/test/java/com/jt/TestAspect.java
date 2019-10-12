package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.service.ItemCatService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAspect {
	@Autowired
	 private ApplicationContext ctx;
	 @Test
	 public void testSysUserService() {
		 ItemCatService itemCatService = 
		 ctx.getBean("itemCatServiceImpl"
				    ,ItemCatService.class);
		 //JDK proxy:com.sun.proxy.$Proxy101
		 //CGLIB:$$EnhancerBySpringCGLIB$$4606b6ef
		 System.out.println(itemCatService.getClass());
	 }
	
	
}

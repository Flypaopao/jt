package com.jt.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.service.ItemCatService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUI_Tree;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Aspect
@Service
@Slf4j
public class ItemCatAspect {

	@Autowired
	private JedisCluster JedisCluster;
	@Autowired
	private ItemCatService ItemCatService;
	
	//定义切入点
	@Pointcut("@annotation(com.jt.annotation.Cache)")
	public void doTest() {
	}
	
	//execution(* com.jt.service.ItemCatServiceImpl.findItemCatList(..))
	@Around("doTest()")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		try {
			log.info("start:"+System.currentTimeMillis());
			Object target = jp.getTarget().getClass().getName();
			System.out.println("调用者："+target);
			//通过jp.getArgs()获得参数
			Object[] args = jp.getArgs();
			Long parentId = Long.parseLong(""+args[0]);
			System.out.println("parentId"+parentId);
			List<EasyUI_Tree> treeList = new ArrayList<>();
			String key = "ITEM_CAT_"+parentId;
			System.out.println(key);
			String result = JedisCluster.get(key);
			
			if (StringUtils.isEmpty(result)) {
				//表示缓存中没有数据，应该查询数据库
				treeList = (List<EasyUI_Tree>) jp.proceed();
				//将对象转化为json
				String json = 
						ObjectMapperUtil.toJSON(treeList);
				//将对象保存到缓存中
				JedisCluster.set(key, json);
				System.out.println("查询数据库");
			}else {
				treeList =ObjectMapperUtil.toObject(result, treeList.getClass());
				System.out.println("查询缓存");
			}
			
			Object results = treeList;
			return results;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}

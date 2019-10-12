package com.jt;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.mapper.ItemDescMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	
	/**
	 * 1.spring整合redis入门案例
	 */
	@Test
	public void testRedis1() {
		String host = "192.168.161.129";
		int port =	6379;
		Jedis jedis = new Jedis(host, port);
		jedis.set("1903", "1903班下午好");
		System.out.println(jedis.get("1903"));
		
		//设定数据的超时时间
		jedis.expire("1903", 20);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1903key还能存活："+jedis.ttl("1903"));
	}
	
	/**
	 * 2.简化操作数据超时用法
	 */
	@Test
	public void testRedis2() {
		Jedis jedis = new Jedis("192.168.161.129", 6379);
		jedis.setex("abc", 100, "英文字母");
		System.out.println(jedis.get("abc"));
	}
	
	/**
	 * 3.锁机制用法
	 * 业务场景：
	 * 		小明：set("yue", "8点xxxx地点");
	 * 		小张：set("yue", "5点xxxx地点");
	 */
	@Test
	public void testRedis3() {
		Jedis jedis = new Jedis("192.168.161.129", 6379);
		//jedis.set("yue", "8点xxxx地点");
		//jedis.set("yue", "5点xxxx地点");	//更新操作
		Long flag1 = jedis.setnx("yue", "8点xxxx地点");
		Long flag2 = jedis.setnx("yue", "5点xxxx地点");
		System.out.println(flag1 + ":::"+ flag2);
		System.out.println("小丽约会时间："+jedis.get("yue"));
	}
	
	/**
	 * 避免死锁：添加key的超时时间
	 * 锁机制优化
	 */
	@Test
	public void testRedis4() {
		Jedis jedis = new Jedis("192.168.161.129", 6379);
		jedis.set("yue", "今晚8点", "NX", "EX", 20);
		int a = 1/0;
		jedis.del("yue");
		jedis.set("yue", "今晚5点", "NX", "EX", 20);
	}
	
	/**
	 * 测试队列栈
	 */
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.161.129", 6379);
		//1.当做队列
		jedis.lpush("list", "1","2","3","4");
		System.out.println("获取数据:"+jedis.rpop("list"));
		//2.当做栈
		jedis.lpush("list", "1","2","3","4");
		System.out.println("获取数据:"+jedis.lpop("list"));
	}
	
	/**
	 * 4.测试事务控制
	 */
	@Test
	public void testTx() {
		Jedis jedis = new Jedis("192.168.161.129", 6379);
		Transaction transaction = jedis.multi();	//1.开启事务
		try {
			transaction.set("aa", "aaa");
			transaction.set("cc", "bbb");
			//int a = 1/0;	//模拟报错
			transaction.exec();	//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
	}
	
	/**
	 * 5.Springboot整合redis实际操作代码
	 * 业务需求：
	 * 	查询itemDesc数据，之后缓存处理.
	 * 步骤：
	 * 1.先查询缓存中是否有itemDesc数据
	 * 	null	查询数据库	将数据保存到缓存中
	 *  !null	获取数据直接返回
	 * 问题：
	 * 	一般使用redis时都采用String类型操作
	 * 	但是从数据库获取的数据都是对象Object
	 * 	String ~~~~json~~~~object类型转化
	 */
	
	/*
	 * @Autowired private Jedis jedis;
	 * 
	 * @Autowired private ItemDescMapper itemDescMapper;
	 * 
	 * @Test public void testRedisItemDesc() { String key = "1000"; ItemDesc }
	 */
}




















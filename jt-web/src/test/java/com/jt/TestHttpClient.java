package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHttpClient {

	@Autowired
	private HttpClientService httpClient; 
	/**
	 * 编码思路：
	 * 	1.创建工具API对象
	 * 	2.定义远程url路径
	 * 	3.定义请求类型对象
	 * 	4.发起http请求，获取响应结果
	 * 	5.从响应对象中获取数据
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		CloseableHttpClient client = 
				HttpClients.createDefault();
		String url = "https://item.jd.com/1221882.html";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = 
				client.execute(get);
		//判断响应是否正确
		if (response.getStatusLine().getStatusCode()==200) {
			String result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
		} else {
			System.out.println("请求失败");
		}
		//从中获取响应数据	JSON
		
	}
	
	@Test
	public void test01() {
		String result = httpClient.doGet("https://item.jd.com/1221882.html");
		System.out.println(result);
	}
}



















package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Reference(timeout = 3000,check = false)
	private DubboCartService cartService;
	
	/**
	 * 当用户点击购物车按钮时，应该展现用户的购物记录
	 * 业务实现：
	 * 	查询用户购物车行为  userId 暂时写死
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model,HttpServletRequest request) {
//		User user = ObjectMapperUtil.toObject(request.getAttribute("JT_USER"), User.class);
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";	//跳转购物车页面
	}
	
	/**
	 * 如果rest风格接收参数时 与对象的属性名称一致则可以使用对象接收
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/delete/{ItemId}")
	public String deleteCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		
		return "redirect:/cart/show.html";	//应该采用重定向的方法获取数据
	}
	
	/**
	 * 该方法利用页面的表单提交获取参数
	 * 之后应该跳转购物车展现页面
	 * @param cart
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";	//转发
	}
	
	/**
	 * 修改
	 * @param cart
	 * @return
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.updateCartNum(cart);
		return SysResult.success();
	}
	
}




















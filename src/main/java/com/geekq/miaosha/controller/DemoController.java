package com.geekq.miaosha.controller;

import com.geekq.miaosha.redis.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geekq.miaosha.domain.User;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.result.CodeMsg;
import com.geekq.miaosha.result.Result;
import com.geekq.miaosha.service.UserService;

@Controller
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

	 	@RequestMapping("/")
	    @ResponseBody
	    String home() {
	        return "Hello World!";
	    }
	 	//1.rest api json输出 2.页面
	 	@RequestMapping("/hello")
	    @ResponseBody
	    public Result<String> hello() {
	 		return Result.success("hello,imooc");
	       // return new Result(0, "success", "hello,imooc");
	    }

	 	@RequestMapping("/helloError")
	    @ResponseBody
	    public Result<String> helloError() {
	 		return Result.error(CodeMsg.SERVER_ERROR);
	 		//return new Result(500102, "XXX");
	    }

	 	@RequestMapping("/thymeleaf")
	    public String  thymeleaf(Model model) {

	 		model.addAttribute("name", "Joshua");
	 		return "hello";
	    }

	 	@RequestMapping("/db/get")
	 	@ResponseBody
	    public Result<User>  dbGet() {
	 		User user = userService.getById(1);
	 		return  Result.success(user);
	    }

	 	@RequestMapping("/db/tx")
	 	@ResponseBody
	    public Result<Boolean>  dbTx() {
	 		userService.tx();
	 		return  Result.success(true);
	    }

	 	@RequestMapping("/redis/get")
	    @ResponseBody
	    public Result<User> redisGet() {
	    	User  user  = redisService.get(UserKey.getById, ""+1, User.class);
	        return Result.success(user);
	    }

	    @RequestMapping("/redis/set")
	    @ResponseBody
	    public Result<Boolean> redisSet() {
	    	User user  = new User();
	    	user.setId(1);
	    	user.setName("1111");
	    	redisService.set(UserKey.getById, ""+1, user);//UserKey:id1
	        return Result.success(true);
	    }

}

package cn.jiayao.myjwt.modular.controller;

import cn.jiayao.myjwt.jwts.data.JwtClaims;
import cn.jiayao.myjwt.modular.service.LoginService;
import cn.jiayao.myjwt.modular.tools.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类 名: LoginController
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/4/30 : 15:40
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param customerId
     * @return
     */
    @GetMapping(value = "/login")
    public Json login(String customerId) {
        try {
            return Json.newInstance(loginService.login(customerId));
        } catch (Exception e) {
            log.error("登录失败，错误信息{}", e.getMessage());
            return Json.CODE_500;
        }
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/findCustomerById")
    public Json findCustomerById(HttpServletRequest request) {
        try {
            String customerId = ((JwtClaims) request.getAttribute("claims")).get("id").toString();
            return Json.newInstance(loginService.findCustomerById(customerId));
        } catch (Exception e) {
            log.error("登录失败，错误信息{}", e.getMessage());
            return Json.CODE_500;
        }
    }

    /**
     * 测试登录状态下和未登录状态下的请求
     * @param request
     * @return
     */
    @GetMapping(value = "/test")
    public Json test(HttpServletRequest request){
        String customerId = null;
        Object claims = request.getAttribute("claims");
        if (claims != null){
            customerId =  ((JwtClaims)claims).get("id").toString();
        }
        if (customerId == null){
            return Json.newInstance("我在未登录的状态下请求进来了。。。。。");
        }else{
            return Json.newInstance("我在登录的状态下请求进来了。。。。。我的id=" + customerId);
        }
    }

}

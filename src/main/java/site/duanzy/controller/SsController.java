package site.duanzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.duanzy.service.SsService;

/**
 * @author leo
 * @date 2018/9/10 15:09
 */
@RestController
@RequestMapping("ss")
public class SsController {

    @Autowired
    private SsService service;

    /**
     * 列出所有的账号信息
     * @return
     */
    @RequestMapping("list")
    public Object listAccount(){
        return service.getAllAccount();
    }



}

package com.cn.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class quickController {

    @RequestMapping("/do")
    @ResponseBody
    public String quick (){
        return "nihao helloClient";
    }
}

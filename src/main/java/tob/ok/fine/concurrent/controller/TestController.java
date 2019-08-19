package tob.ok.fine.concurrent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/18
 */

@Slf4j
@RestController
public class TestController {


    @GetMapping("/test")
    public String test() {
        return "test";
    }


}

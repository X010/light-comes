package com.light.outside.comes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by b3st9u on 16/10/30.
 */
@Controller
@RequestMapping("my")
public class MineController {
    @RequestMapping("mine.action")
    public String mine(Map<String, Object> data) {
        return "mine";
    }

}

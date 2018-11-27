package com.feng.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 此controller用作页面间的调转
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public ModelAndView toIndex() {
        return new ModelAndView("index");
    }

    @RequestMapping("/chat/chat")
    public ModelAndView toChat() {
        return new ModelAndView("chat/chat");
    }

}

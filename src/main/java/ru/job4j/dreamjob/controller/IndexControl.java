package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@ThreadSafe
@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

package com.bzy.springtemplate.about;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MyRestController {

    @GetMapping("about")
    @Transactional
    public AboutRepresentation about() {
        return new AboutRepresentation();
    }

}

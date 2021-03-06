package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class IndexController
{
    @RequestMapping(path = { "", "/", "index", "index.html" })
    public String index()
    {
        return "index";
    }

    @RequestMapping(path = { "/oups" })
    public String oupsHandler()
    {
        return "notimplemented";
    }
}
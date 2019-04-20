package ca.marcos.vibesweb.controller;

import ca.marcos.vibesweb.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private QuotesService quotesService;

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("quotes", quotesService.getQuotesList());
        return "quotes";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}

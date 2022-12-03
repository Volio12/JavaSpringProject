package com.example.projekt.controllers;

import com.example.projekt.dao.ILaureateRepository;
import com.example.projekt.entities.Laureate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/laureates")
public class LaureateController {

    @Autowired
    ILaureateRepository laureateRepository;

    @GetMapping
    public String displayLaureates(HttpServletRequest request, Model model){
        int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("laureates",laureateRepository.findAll(PageRequest.of(page,size)));
        return "laureates/laureates";
    }

    @GetMapping("/newLaureate")
    public String displayLaureateForm(Model model){
        Laureate laureate = new Laureate();
        model.addAttribute("laureate", laureate);
        return "laureates/new-laureate";
    }
    @PostMapping("/save")
    public String createLaureate(Laureate laureate, Model model){
        laureateRepository.save(laureate);
        return  "redirect:/laureates/newLaureate";
    }

    @GetMapping("/editForm")
    public String displayEditLaureateForm(@RequestParam Long laureateId, Model model){
        Laureate laureate = laureateRepository.findById(laureateId).get();
        model.addAttribute("laureate",laureate);
        return "/laureates/editLaureate";
    }

    @PostMapping("/update")
    public String updateLaureate(@ModelAttribute Laureate laureate, @RequestParam Long laureateId){
        laureate.setLaureateId(laureateId);
        laureateRepository.save(laureate);
        return "redirect:/laureates";
    }

    @GetMapping("/delete")
    public String deleteLaureate(@RequestParam Long laureateId){
        System.out.println(laureateId);
        laureateRepository.deleteById(laureateId);
        return "redirect:/laureates";
    }

}

package com.fc.controller;

import com.fc.pojo.Product;
import com.fc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    //测试：http://localhost:8080/show?id=1

    @Autowired
    private ProductService productService;

    @GetMapping("/show")
    public String select(Integer id, Model model) {

        Product product = productService.findProductById(id);
        model.addAttribute("product", product);

        return "show";
    }
}

package com.wil.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wil.entity.Book;
import com.wil.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Created by wil on 2017/11/4.
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String list(@RequestParam(name = "p", required = false, defaultValue = "1") Integer pageNo,
                       @RequestParam String bookName,
                       @RequestParam String authorName,
                       @RequestParam Integer typeId,
                       Model model) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("bookName", bookName);
        map.put("authorName", authorName);
        map.put("typeId", typeId);
        PageInfo<Book> pageInfo = bookService.findByPageNo(pageNo, map);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("authorList", bookService.findAuthorList());
        model.addAttribute("typeList", bookService.findAllType());

        return "book/list";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("typeList", bookService.findAllType());
        return "book/new";
    }

    @PostMapping("/new")
    public String newBook(Book book, RedirectAttributes redirectAttributes) {
        bookService.save(book);
        redirectAttributes.addFlashAttribute("message", "添加成功");
        return "redirect:/book";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("typeList", bookService.findAllType());
        model.addAttribute("book", bookService.findById(id));
        return "book/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String edit(Book book, RedirectAttributes redirectAttributes) {
        bookService.edit(book);
        redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/book" + book.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        bookService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/book";
    }

}

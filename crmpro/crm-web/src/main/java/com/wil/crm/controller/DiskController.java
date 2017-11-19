package com.wil.crm.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wil.crm.entity.Disk;
import com.wil.crm.service.DiskService;
import com.wil.web.result.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
@Controller
@RequestMapping("/disk")
public class DiskController extends BaseController {

    @Autowired
    private DiskService diskService;

    @GetMapping
    public String home(@RequestParam(name = "_", required = false, defaultValue = "0") Integer pId,
                       Model model) {
        List<Disk> diskList = diskService.findDiskListByPId(pId);
        model.addAttribute("diskList", diskList);
        model.addAttribute("pId", pId);
        return "disk/home";
    }


    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveFolder(Disk disk) {
        diskService.saveNewFolder(disk);
        return new AjaxResult().success();
    }

    @GetMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request) {

        try {
            Part part = request.getPart("file");
            String saveName = request.getParameter("name");
            File file = new File("d:/upload", saveName);
            OutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = part.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "disk/home";

    }






}

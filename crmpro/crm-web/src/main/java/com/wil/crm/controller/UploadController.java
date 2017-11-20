package com.wil.crm.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wil on 2017/11/20.
 */
@Controller
public class UploadController {

    @GetMapping("/upload")
    public String upload(Model model) {
        String accessKey = "Z0GOHuyEPgJFyDq_Tf01webmcdN2kR46OtVqSBJ8";
        String secretKey = "NDfKH0zRppdwKTcU88ccg2BowErBTQnzgvmXTK8Z";
        String bucket = "java24crm";

        Auth auth = Auth.create(accessKey, secretKey);
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{\"key\":\"${key}\"}");
        String upToken = auth.uploadToken(bucket);
        model.addAttribute("upToken", upToken);
        return "upload";
    }

}

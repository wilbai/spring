package com.wil.controller;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by wil on 2017/11/20.
 */
@Controller
public class UploadController {

    /**
     * 客户端上传
     * @param model
     * @return
     */
    @GetMapping("/upload")
    public String upload(Model model) {
        String accessKey = "Z0GOHuyEPgJFyDq_Tf01webmcdN2kR46OtVqSBJ8";
        String secretKey = "NDfKH0zRppdwKTcU88ccg2BowErBTQnzgvmXTK8Z";
        String bucket = "java24crm";

        Auth auth = Auth.create(accessKey, secretKey);
        StringMap stringMap = new StringMap();
        //stringMap.put("returnBody","{\"key\":\"${key}\"}");
        stringMap.put("returnUrl", "http://localhost:8080/upload/callback");
        String upToken = auth.uploadToken(bucket,null,3600,stringMap);
        model.addAttribute("upToken", upToken);
        model.addAttribute("pid", "1001");
        return "upload";
    }

    @GetMapping("/upload/callback")
    public String uploadCallback(String upload_ret) {

        String jsonString = new String(Base64.getDecoder().decode(upload_ret));
        System.out.println(jsonString);
        return "upload";
    }

    /**
     * 服务器上传图片
     * @return
     */
    @GetMapping("/upload/local")
    public String uploadLocal() {
        return "upload_local";
    }

    @PostMapping("/upload/local")
    public String uploadLocal(MultipartFile file) throws IOException {
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        String accessKey = "Z0GOHuyEPgJFyDq_Tf01webmcdN2kR46OtVqSBJ8";
        String secretKey = "NDfKH0zRppdwKTcU88ccg2BowErBTQnzgvmXTK8Z";
        String bucket = "java24crm";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        byte[] bytes = file.getBytes();
        Response response = uploadManager.put(bytes, null, upToken);
        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        System.out.println("key -> " + defaultPutRet.key);

        return "redirect:/upload/local";
    }

    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response) throws IOException {

        String domain = "http://ozp3uwn5y.bkt.clouddn.com";
        String encodeFileName = URLEncoder.encode(name, "UTF-8");
        String finalUrl = String.format("%s/%s",domain, encodeFileName);
        System.out.println(finalUrl);

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(finalUrl).openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        OutputStream outputStream = response.getOutputStream();

        outputStream.flush();
        outputStream.close();
        inputStream.close();



    }

}

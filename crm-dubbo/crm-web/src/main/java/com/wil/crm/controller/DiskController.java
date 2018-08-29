package com.wil.crm.controller;

import com.wil.crm.controller.exception.NotFoundException;
import com.wil.crm.controller.files.FileStore;
import com.wil.crm.entity.Disk;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.service.DiskService;
import com.wil.web.result.AjaxResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
@Controller
@RequestMapping("/disk")
public class DiskController extends BaseController {

    @Autowired
    @Qualifier("qiNiuFileStoreImpl")
    private FileStore fileStore;


    @Autowired
    private DiskService diskService;

    @GetMapping
    public String home(@RequestParam(name = "_", required = false, defaultValue = "0") Integer pId,
                       Model model) {
        if(pId != 0) {
            Disk disk = diskService.findById(pId);
            model.addAttribute("disk", disk);
        }

        List<Disk> diskList = diskService.findDiskListByPId(pId);
        model.addAttribute("diskList", diskList);
        return "disk/home";
    }


    /**
     * 新建文件夹
     * @param disk
     * @return
     */
    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveFolder(Disk disk) {
        diskService.saveNewFolder(disk);
        List<Disk> diskList = diskService.findDiskListByPId(disk.getpId());
        return new AjaxResult().successWithData(diskList);
    }

    /**
     * 上传文件
     * @param pId
     * @param accountId
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(Integer pId, Integer accountId,
                             @RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()) {
            return new AjaxResult().error("文件为空");
        }

        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        String fileName = file.getOriginalFilename();

        saveNewFile(inputStream, size, fileName, pId, accountId);

        List<Disk> diskList = diskService.findDiskListByPId(pId);
        return new AjaxResult().successWithData(diskList);

    }

    public void saveNewFile(InputStream inputStream, long size, String fileName, Integer pId, Integer accountId) throws ServiceException {

        Disk disk = new Disk();
        disk.setCreateTime(new Date());
        disk.setType(Disk.TYPE_FILE);
        disk.setAccountId(accountId);
        disk.setFileSize(FileUtils.byteCountToDisplaySize(size));
        disk.setByteSize(Double.longBitsToDouble(size));
        disk.setpId(pId);
        disk.setName(fileName);
        disk.setDownloadCount(0);

        try {
            String saveName = fileStore.saveFile(inputStream, fileName);
            disk.setSaveName(saveName);
            diskService.saveNewDisk(disk);
        } catch (IOException ex) {
            throw new ServiceException(ex, "文件上传异常");
        }


    }



    /**
     * 下载文件
     * @param id
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("_") Integer id,
                               @RequestParam(required = false, defaultValue = "") String fileName,
                               HttpServletResponse response) {

        try {
            OutputStream outputStream = response.getOutputStream();
            //获得输入流，在controller中设置响应报文的信息
            InputStream inputStream = getDownloadInputStream(id);

            if(StringUtils.isNotEmpty(fileName)) {
                response.setContentType("application/octet-stream");
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
            }

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new NotFoundException();
        }

    }

    public InputStream getDownloadInputStream(Integer id) throws IOException {

        Disk disk = diskService.findById(id);
        if(disk == null || disk.getType().equals(Disk.TYPE_DIR)) {
            throw new ServiceException("文件不存在或已被删除");
        }

        disk.setDownloadCount(disk.getDownloadCount()+1);

        diskService.updateDisk(disk);

        byte[] bytes = fileStore.getFile(disk.getSaveName());
        return new ByteArrayInputStream(bytes);

    }






}

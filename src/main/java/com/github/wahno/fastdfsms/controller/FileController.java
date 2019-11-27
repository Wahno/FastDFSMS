package com.github.wahno.fastdfsms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.wahno.fastdfsms.entity.dto.DownloadFile;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.facade.FileFacade;
import com.github.wahno.fastdfsms.util.DownloadUtil;
import com.github.wahno.fastdfsms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
@Controller
public class FileController {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    Result result;

    @Autowired
    FileFacade fileFacade;

    @PostMapping("/{owner}")
    public String upload(@RequestParam("file") MultipartFile file,
                         @PathVariable("owner") String owner) throws FileException{
        return result.successData(fileFacade.upload(file,owner));
    }

    @DeleteMapping("/{owner}")
    public String delete(@PathVariable("owner") String owner,
                         @RequestParam("id") String id) throws FileException {
        fileFacade.deleteFile(id);
        return result.successMsg();
    }

    @GetMapping("/{owner}")
    public void download(@PathVariable("owner") String owner,
                         @RequestParam("id") String id) throws FileException, IOException {
        DownloadUtil.attachmentDownload(response,fileFacade.download(id));
    }

    @GetMapping("/{owner}/zip")
    public void downloadZip(@PathVariable("owner") String owner,
                            @RequestParam("ids") String ids,
                            @RequestParam("file_name")String fileName) throws IOException, FileException {
        JSONArray idArray = JSON.parseArray(ids);
        List<DownloadFile> downloadFileList = fileFacade.download(idArray.toJavaList(String.class));
        DownloadUtil.attachmentDownloadZip(response,downloadFileList,fileName);
    }

    @GetMapping("/{owner}/zip/stream")
    public void downloadStreamZip(@PathVariable("owner") String owner,
                                  @RequestParam("ids") String ids,
                                  @RequestParam("file_name")String fileName) throws IOException, FileException {
        JSONArray idArray = JSON.parseArray(ids);
        fileFacade.streamAttachmentDownload(response,idArray.toJavaList(String.class),fileName);
    }
}

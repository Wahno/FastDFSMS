package com.github.wahno.fastdfsms.controller;

import com.github.wahno.fastdfsms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    protected Result result;
}

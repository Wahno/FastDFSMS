package com.github.wahno.fastdfsms;

import com.alibaba.fastjson.JSON;
import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.repository.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileInfoTest {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Test
    public void addFileInfo() {

        FileInfo fileInfo = fileInfoRepository.save(FileInfo.builder().fileGroup("test").filePath("test").build());
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        if(log.isDebugEnabled()){
            log.debug(JSON.toJSONString(fileInfo));
            for (FileInfo temp: fileInfoList){
                log.debug(JSON.toJSONString(temp));
            }
        }
        fileInfoRepository.deleteById(fileInfo.getId());

    }
}
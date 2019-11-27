package com.github.wahno.fastdfsms.service.impl;

import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.exception.FileExceptionEnum;
import com.github.wahno.fastdfsms.repository.FileInfoRepository;
import com.github.wahno.fastdfsms.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    FileInfoRepository fileInfoRepository;

    @Override
    public List<FileInfo> findAllFileInfo() {
        return fileInfoRepository.findAll();
    }

    @Override
    public FileInfo save(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }

    @Override
    public FileInfo find(Long id) throws FileException {
        return fileInfoRepository.findById(id)
                .orElseThrow(()->new FileException(FileExceptionEnum.FILE_EMPTY));
    }

    @Override
    public void delete(Long id) {
        fileInfoRepository.deleteById(id);
    }
}

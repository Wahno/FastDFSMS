package com.github.wahno.fastdfsms.repository;

import com.github.wahno.fastdfsms.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public interface FileInfoRepository extends JpaRepository<FileInfo,Long> {
}

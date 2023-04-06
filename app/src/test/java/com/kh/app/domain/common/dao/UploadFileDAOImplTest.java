package com.kh.app.domain.common.dao;

import com.kh.app.domain.entity.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UploadFileDAOImplTest {

    @Autowired
    private UploadFileDAO uploadFileDAO;//타입

    @Test
    @DisplayName("단건 첨부")
    void addFile() {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCode("F010301");
        uploadFile.setRid(10L);
        uploadFile.setStore_filename(UUID.randomUUID()+".png");
        uploadFile.setUpload_filename("배경이미지"); //사용자가 지정한 파일명
        uploadFile.setFsize("100");
        uploadFile.setFtype("image/png");
        Long fid = uploadFileDAO.addFile(uploadFile);

        Assertions.assertThat(fid).isGreaterThan(0L);
    }

    @Test
    @DisplayName("여러건 첨부")
    void addFiles() {

        List<UploadFile> files = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setCode("F010301");
            uploadFile.setRid(10L);
            uploadFile.setStore_filename(UUID.randomUUID() + ".png");
            uploadFile.setUpload_filename("배경이미지"); //사용자가 지정한 파일명
            uploadFile.setFsize("100");
            uploadFile.setFtype("image/png");
            files.add(uploadFile);
        }
        uploadFileDAO.addFiles(files);
        List<UploadFile> list = uploadFileDAO.findFilesByCodeWithRid("F010302", 10L);

        Assertions.assertThat(list.size()).isEqualTo(5);
    }
}
package com.kh.app.domain.common.svc;

import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MultipartFileToUploadFile {

    public UploadFile convert(MultipartFile mf, AttachFileType attachFileType){
        if(mf.isEmpty()) return null;
        //값을 넣어준다.
        UploadFile uploadFile = new UploadFile();
        //setter 메소드를 이용해서, inner 타입으로 AttachFileType." 값 " 으로 표현한다.
//        AttachFileType aa = AttachFileType.F010301; 값을 이런식으로 가져온다.
        uploadFile.setCode(attachFileType.name());
//    uploadFile.setRid(rid);
        uploadFile.setUpload_filename(mf.getOriginalFilename());
        //값을 너어준다.
        uploadFile.setStore_filename(createStoreFilename(mf.getOriginalFilename()));
        //getSize -> 사이즈를 만들어준다.
        uploadFile.setFsize(String.valueOf(mf.getSize()));
        uploadFile.setFtype(mf.getContentType());
        return uploadFile;
    }

    public List<UploadFile> convert(List<MultipartFile> mfs, AttachFileType attachFileType) {
        if(mfs.size() < 1) return null;
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile mf : mfs) {
            if(mf.isEmpty()) continue;
            uploadFiles.add(convert(mf,attachFileType));
        }
        return uploadFiles;
    }

    //임의파일명 생성
    private String createStoreFilename(String originalFile) {
        StringBuffer storeFileName = new StringBuffer();
        storeFileName.append(UUID.randomUUID().toString())
                .append(".")
                .append(extractExt(originalFile)); // xxx-yyy-zzz-ttt..
        return storeFileName.toString();
    }

    //확장자 추출
    private String extractExt(String originalFile) {
        int posOfExt =originalFile.lastIndexOf(".");
        String ext = originalFile.substring(posOfExt + 1);
        return ext;
    }
}

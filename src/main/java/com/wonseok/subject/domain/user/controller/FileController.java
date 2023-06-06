package com.wonseok.subject.domain.user.controller;

import com.wonseok.subject.domain.user.dto.file.MultiUploadFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@PreAuthorize("permitAll()")
public class FileController {

    @PostMapping("/ws/file/upload")
    public void uploadFile(@RequestPart(name = "data") MultiUploadFileDto data,
                           @RequestPart(name = "file", required = false) List<MultipartFile> fileList) throws IOException {
        System.out.println("uploadFile IN");

        log.info("data ={}", data.getName());
        String uploadFolder = "/Users/ws/Desktop/files/upload";
        File uploadPath = new File(uploadFolder);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        for (MultipartFile mf : fileList) {
            String originFileName = mf.getOriginalFilename(); // 원본 파일명
            long fileSize = mf.getSize();  // 파일 사이즈

            System.out.println("originFileName : " + originFileName);
            System.out.println("fileSize : " + fileSize);


            try {
                File file = new File(uploadPath + "/" + originFileName);
                mf.transferTo(file);

            } catch (IOException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
        }

        System.out.println("파일 업로드 성공");

    }

    @GetMapping(value = "/ws/file/{fileId}")
    public ResponseEntity<FileSystemResource> download(@PathVariable String fileId, HttpServletRequest request) throws IOException {

        // DB에서 해당 파일의 ID 값에 해당하는 파일 경로 가져옴

        //
        String fileName = "나비_1.jpeg";
        //String fileName = "테스트.pdf";
        //String fileName = "테스트.zip";

        String filePath = "/Users/ws/Desktop/files/upload/" + fileName;

        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        String originalFileName = fileSystemResource.getFilename();
        //String encodeFileName = fileNameEncoder(fileSystemResource.getFilename(), request);
        long contentLength = fileSystemResource.contentLength();


        ContentDisposition contentDisposition = ContentDisposition
                                                .attachment()
                                                .filename(originalFileName, StandardCharsets.UTF_8)
                                                .build();


        String cacheControl = "no-cache, no-store, must-revalidate";
        String pragma = "no-cache";
        String expires = "0";

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CACHE_CONTROL,cacheControl)
                .header(HttpHeaders.PRAGMA,pragma)
                .header(HttpHeaders.EXPIRES,expires)
                .contentLength(contentLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileSystemResource);
    }


    // user-agent에 따른 파일 인코딩
    public String fileNameEncoder(String fileName, HttpServletRequest request) {

        String requestHeader = request.getHeader("User-Agent");
        String encodedFileName = "";
        String originalFileName = getOriginalFileName(URLDecoder.decode(fileName, StandardCharsets.UTF_8));

        if (requestHeader.contains("Edge")) {
            encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        } else if (requestHeader.contains("MSIE") || requestHeader.contains("Trident")) {
            encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        } else if (requestHeader.contains("Chrome")) {
            encodedFileName = new String(originalFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (requestHeader.contains("Opera")) {
            encodedFileName = new String(originalFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (requestHeader.contains("Firefox")) {
            encodedFileName = new String(originalFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return encodedFileName;
    }

    // 파일명에서 확장자 제거한 이름 가져오기
    public String getOriginalFileName(String fileName) {
        // 인덱스 찾기 * 아래 나옴

        String[] split = fileName.split("\\.");

        return split[0];
    }
}

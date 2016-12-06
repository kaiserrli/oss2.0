package com.ebupt.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ebupt.oss.domain.UploadBean;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by 凯 on 2016/11/24.
 */
@RestController
@RequestMapping("/OSS")
public class OSSController {

    @RequestMapping(value = "/delivery/upload", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public UploadBean upload(MultipartFile file) {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String fileName = file.getOriginalFilename();
        String url = "https://123.56.26.82:8443/OSS/delivery/download?uuid=" + uuid + "&" + "fileName=" + fileName;

        String prefix = uuid.substring(0, 15);
        String suffix = uuid.substring(16);

        try {

            FileUtils.writeByteArrayToFile(new File("/home/oss/repertory/delivery/" + prefix + "/"
                    + suffix + "/" + fileName), file.getBytes());

            /*
            FileUtils.writeByteArrayToFile(new File("/Users/lzuo/IdeaProjects/oss/src/main/resources/repertory/delivery/" + prefix + "/"
                    + suffix + "/" + fileName), file.getBytes());
            */
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadBean uploadBean = new UploadBean(url, uuid);

        return uploadBean;
    }

    @RequestMapping(value = "/delivery/download", method = RequestMethod.GET)
    public void download(String uuid, String fileName, HttpServletResponse response) {
        String prefix = uuid.substring(0, 15);
        String suffix = uuid.substring(16);

        String filePath = "/home/oss/repertory/delivery/" + prefix + "/" + suffix + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setContentLength((int) file.length());

            try {
                InputStream fileInputStream = new FileInputStream(filePath);
                IOUtils.copy(fileInputStream, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendError(204, "the file is not exit");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("lzuo")
    String index() {
        return "lzuo";
    }

}

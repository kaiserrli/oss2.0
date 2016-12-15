package com.ebupt.oss;

import java.io.*;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebupt.oss.domain.UploadBean;

/**
 * Created by 凯 on 2016/11/24.
 */
@RestController
@RequestMapping("/OSS")
public class OSSController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/delivery/upload", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public UploadBean upload(MultipartFile file, HttpServletRequest request) {
        logger.info("文件上传访问记录:" + "请求端的url：" + request.getRequestURL().toString());
        UploadBean uploadBean = new UploadBean("-1", "-1");
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            String url = "https://123.56.26.82:8443/OSS/delivery/download?uuid=" + uuid;

            String prefix = uuid.substring(0, 1);
            String suffix = uuid.substring(2, 4);

            FileUtils.writeByteArrayToFile(new File("/home/oss/repertory/delivery/" + prefix + "/"
                        + suffix + "/" + uuid), file.getBytes());

            /*
            FileUtils.writeByteArrayToFile(new File("/Users/lzuo/IdeaProjects/oss/src/main/resources/repertory/delivery/" + prefix + "/"
                    + suffix + "/" + fileName), file.getBytes());
            */

            logger.info("本次文件上传产生的uuid为：" + uuid + ", 返回的url为：" + url);

            uploadBean.setUrl(url);
            uploadBean.setUuid(uuid);

        } catch (IOException e) {
            logger.warn("出错原因:" + e.getCause() + ", 错误信息：" + e.getMessage());
        } finally {
            return uploadBean;
        }
    }

    @RequestMapping(value = "/delivery/download", method = RequestMethod.GET)
    public void download(String uuid, HttpServletResponse response) {
        String prefix = uuid.substring(0, 1);
        String suffix = uuid.substring(2, 4);

        String filePath = "/home/oss/repertory/delivery/" + prefix + "/" + suffix + "/" + uuid;
        File file = new File(filePath);

        try {
            if (file.exists()) {
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                response.setHeader("Content-Encoding", "gzip");
                response.setContentLength((int) file.length());

                InputStream fileInputStream = new FileInputStream(filePath);
                IOUtils.copy(fileInputStream, response.getOutputStream());

            } else{
                response.sendError(SC_NOT_FOUND, "NOT FOUND");
            }
        } catch (IOException e) {
            logger.warn("错误原因：" + e.getCause() + " 错误信息:" + e.getMessage());
        }
    }

    @RequestMapping("lzuo")
    String index() {
        logger.info("第一次日志记录");
        logger.warn("bugg");
        return "lzuo";
    }

}

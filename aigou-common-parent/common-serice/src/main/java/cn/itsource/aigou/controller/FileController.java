package cn.itsource.aigou.controller;

import cn.itsource.aigou.util.FastDfsApiOpr;
import cn.itsource.basic.util.AjaxResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {


    @PostMapping("/file")
    public AjaxResult upload(MultipartFile file){
        try {
            byte[] fileBytes = file.getBytes();
            String filename = file.getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf(".");
            String extName = filename.substring(lastIndexOf + 1);
            String file_id = FastDfsApiOpr.upload(fileBytes, extName);
            return AjaxResult.me().setSuccess(true).setMessage("上传成功").setRestObj(file_id);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败");
        }
    }

    @DeleteMapping("file")
    public AjaxResult delete(String FileId){
        System.out.println(FileId);
        try {
            //FileId:/group1/M00/00/01/rBAEgF2m5aGAX9-0AABeeCe9Y3M718.jpg
            String substring = FileId.substring(1);
            //substring:group1/M00/00/01/rBAEgF2m5aGAX9-0AABeeCe9Y3M718.jpg
            System.out.println("substring:" + substring);
            int index = substring.indexOf("/");
            String groupName = substring.substring(0,index);
            String fileName = substring.substring(index + 1);
            //groupName:group1     fileName:M00/00/01/rBAEgF2m5aGAX9-0AABeeCe9Y3M718.jpg
            FastDfsApiOpr.delete(groupName,fileName);
            return AjaxResult.me().setRestObj(true).setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setRestObj(true).setMessage("删除失败");
        }

    }

}

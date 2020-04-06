package com.newsys.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class UpUtils {

    public static void upfile(MultipartFile file, HttpServletRequest request) {
        String orgName = file.getOriginalFilename();
        if (orgName != null) {
            String suffix = orgName.substring(orgName.lastIndexOf(".") + 1);

            Date date = new Date();
            String uuid = UUID.randomUUID()+"";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = simpleDateFormat.format(date);

            String realPath = "";
            realPath = request.getSession().getServletContext().getRealPath("/static/images/newsinfo/");
            File filedemo = new File(realPath + dateStr+uuid+"."+suffix);
			System.out.println(filedemo);
            if (!filedemo.exists()) {
                filedemo.getParentFile().mkdirs();
            }
            try {
               /* BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(realPath + orgName)));
                bos.write(file.getBytes());
                bos.flush();
                bos.close();*/
                file.transferTo(filedemo);
			/*FileOutputStream fos = new FileOutputStream(realPath+orgName,true);
			fos.write(file.getBytes());
			fos.flush();
			fos.close();*/
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

}

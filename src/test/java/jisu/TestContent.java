package jisu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.T;

import com.cms.CommonAttribute;
import com.cms.util.OSSUtils;

public class TestContent {
    
    public static String fileUpload(File file){
        String newFileName = UUID.randomUUID().toString()+".png";
        String url = "/"+CommonAttribute.BASE_UPLOAD_PATH+"/"+newFileName;
        //oss存储
        OSSUtils.upload(url, file, "image/png");
        url = "https://jrecms.oss-cn-hangzhou.aliyuncs.com" +url;
        return url;
    }
    

    public static void main(String[] args) {
        // TODO Auto-generated method stub
            
            File images = new File("E:\\我的代码\\图片\\创业\\image");
            File [] imagesFiles = images.listFiles();
            List<File> fileList = Arrays.asList(imagesFiles);;
            Collections.sort(fileList,new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    // TODO Auto-generated method stub
                    String name1 = o1.getName().substring(0, o1.getName().indexOf("."));
                    String name2 = o2.getName().substring(0, o2.getName().indexOf("."));
                    return Integer.parseInt(name1)-Integer.parseInt(name2);
                }
            });
            String contentStr = "";
            for(File imageFile : fileList){
                String imageUrl = fileUpload(imageFile);
                String imageName = imageFile.getName();
                contentStr+="<p><strong>"+imageName+"</strong></p>";
                contentStr+="<p><img src=\""+imageUrl+"\" /><br /></p>";
            }
            System.out.println(contentStr);
    }

}

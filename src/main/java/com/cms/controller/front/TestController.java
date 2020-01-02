package com.cms.controller.front;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cms.CommonAttribute;
import com.cms.entity.Content;
import com.cms.routes.RouteMapping;
import com.cms.util.OSSUtils;
import com.jfinal.core.NotAction;
import com.jfinal.plugin.activerecord.Db;

@RouteMapping(url = "/test")
public class TestController extends BaseController {
	
    public void qianduan() throws IOException{
        File dao = new File("E:\\我的代码\\htmlsource");
        File [] daos = dao.listFiles();
        
        for(File file : daos){
            if(file.isDirectory()){
                
                File [] files = file.listFiles();
                for(File f : files){
                    File preview = new File(f.getPath()+"/preview.jpg");
                    if(preview.exists()){
                        
                        File descriptionFile = new File(f.getPath()+"/description.txt");
                        if(descriptionFile.exists()){
                            
                            String description = "http://html.jrecms.com/"+file.getName()+"/"+f.getName()+"/index.html";
                            
                            Content newcontent = new Content().dao().findFirst("select * from cms_content where description=?",description);
                            if(newcontent!=null){
                                continue;
                            }
                            String title = FileUtils.readFileToString(descriptionFile);
                            Content content = new Content();
                            content.setImage("http://html.jrecms.com/"+file.getName()+"/"+f.getName()+"/preview.jpg");
                            content.setDescription(description);
                            content.setCreateDate(new Date());
                            content.setModifyDate(new Date());
                            content.setTitle(title);
                            content.setHits(0l);
                            content.save();
                        }
                    }
                }
            }
        }
    }
    
    public void dao(){
        File dao = new File("c:/dao");
        File [] daos = dao.listFiles();
        Content content = new Content();
        for(File file : daos){
            String title = file.getName();
            File preview = new File(file.getPath()+"/preview.png");
            
            content.setImage(fileUpload(preview));
            content.setCreateDate(new Date());
            content.setModifyDate(new Date());
            content.setTitle(title);
            content.setHits(0l);
            
            File images = new File(file.getPath()+"/images");
            File [] imagesFiles = images.listFiles();
            String contentStr = "";
            for(File imageFile : imagesFiles){
                String imageUrl = fileUpload(imageFile);
                String imageName = imageFile.getName();
                contentStr+="<p><strong>"+imageName+"</strong></p>";
                contentStr+="<p><img src=\""+imageUrl+"\" /><br /></p>";
            }
            
            content.setIntroduction(contentStr);
            content.save();
        }
    }
    
    
    @NotAction
    public String fileUpload(File file){
        String newFileName = UUID.randomUUID().toString()+".png";
        String url = "/"+CommonAttribute.BASE_UPLOAD_PATH+"/"+newFileName;
        //oss存储
        OSSUtils.upload(url, file, "image/png");
        url = "https://jrecms.oss-cn-hangzhou.aliyuncs.com" +url;
        return url;
    }
    
    /**
     * 首页
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public void index() throws MalformedURLException, IOException {
        List<Content> contents = new Content().dao().find("select * from cms_content");
        for(Content content : contents){
            String image = content.getImage();
            String introduction= content.getIntroduction();
            if(StringUtils.isNotBlank(introduction)){
                Document document = Jsoup.parse(introduction);
                Elements elements = document.getElementsByTag("img");
                if(elements!=null && elements.size()>0){
                    for(Element ele : elements){
                        String src = ele.attr("src");
                        ele.attr("src", "https://jrecms.oss-cn-hangzhou.aliyuncs.com/upload/"+FilenameUtils.getName(src));
                        filechange(src);
                        
                    }
                }
                content.setIntroduction(document.html());
            }
            
            if(StringUtils.isNotBlank(image)){
                filechange(image);
                content.setImage("https://jrecms.oss-cn-hangzhou.aliyuncs.com/upload/"+FilenameUtils.getName(image));
            }
            content.update();
            
            
        }
    }
    
    @NotAction
    public void filechange(String image) throws MalformedURLException, IOException{
        File file = new File("E:\\我的代码\\备份图片\\"+FilenameUtils.getName(image));
        if(file.exists()){
            return;
        }
        FileUtils.copyURLToFile(new URL(image.replace("http://wwwimage.kuaifankeji.com", "https://kuaifankeji.oss-cn-hangzhou.aliyuncs.com")), file);
    }
}

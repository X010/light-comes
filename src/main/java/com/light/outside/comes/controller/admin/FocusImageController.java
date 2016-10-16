package com.light.outside.comes.controller.admin;

import com.google.common.base.Strings;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.FileUtil;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/15.
 */
@Controller
@RequestMapping("admin")
public class FocusImageController {
    @Autowired
    private FocusImageService focusImageService;

    @RequestMapping("focus_manage.action")
    public String focus_manage(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        int column = RequestTools.RequestInt(request, "column", 1);
        boolean isCreate = true;
        List<FocusImageModel> focusImageModels = focusImageService.queryFocusImageByColumn(column);
        if (focusImageModels != null && focusImageModels.size() > 0) {
            int i = 0;
            for (FocusImageModel focusImageModel : focusImageModels) {
                data.put("focusImage" + i, focusImageModel);
                i++;
            }
            isCreate = false;
        }
        data.put("isCreate", isCreate);
        data.put("column", column);
        return "admin/focus_image_manage";
    }

    @RequestMapping("create_focus_image_save.action")
    public String create_focus_save(FocusImageModel focusImageModel,
                                    @RequestParam("focus_image") MultipartFile file,
                                    @RequestParam("focus_image1") MultipartFile file2,
                                    @RequestParam("focus_image2") MultipartFile file3,
                                    HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> data) {
        int column = RequestTools.RequestInt(request, "column", 0);
        if (file != null&&!Strings.isNullOrEmpty(file.getOriginalFilename())) {
            String file_path = FileUtil.saveFile(file);
            if (!Strings.isNullOrEmpty(file_path)) {
                focusImageModel.setImage(file_path);
                focusImageModel.setCreate_time(new Date());
                focusImageModel.setStatus(1);
                focusImageService.addFocusImage(focusImageModel);
                data.put("focusImage0", focusImageModel);
            }
        }
        if (file2 != null&&!Strings.isNullOrEmpty(file2.getOriginalFilename())) {
            FocusImageModel focusImageModel1 = new FocusImageModel();
            String file_path = FileUtil.saveFile(file2);
            if (!Strings.isNullOrEmpty(file_path)) {
                String title = RequestTools.RequestString(request, "title1", "");
                String link = RequestTools.RequestString(request, "link1", "");
                focusImageModel1.setImage(file_path);
                focusImageModel1.setCreate_time(new Date());
                focusImageModel1.setColumn(column);
                focusImageModel1.setTitle(title);
                focusImageModel1.setLink(link);
                focusImageModel1.setStatus(1);
                focusImageService.addFocusImage(focusImageModel1);
                data.put("focusImage1", focusImageModel1);
            }
        }
        if (file3 != null&&!Strings.isNullOrEmpty(file3.getOriginalFilename())) {
            FocusImageModel focusImageModel1 = new FocusImageModel();
            String file_path = FileUtil.saveFile(file3);
            if (!Strings.isNullOrEmpty(file_path)) {
                String title = RequestTools.RequestString(request, "title2", "");
                String link = RequestTools.RequestString(request, "link2", "");
                focusImageModel.setCreate_time(new Date());
                focusImageModel1.setImage(file_path);
                focusImageModel1.setColumn(column);
                focusImageModel1.setTitle(title);
                focusImageModel1.setLink(link);
                focusImageModel1.setStatus(1);
                focusImageService.addFocusImage(focusImageModel1);
                data.put("focusImage2", focusImageModel1);
            }
        }
        data.put("column", column);
        return "admin/focus_image_manage";
    }

    @RequestMapping("update_focus_image_save.action")
    public String update_focus_save(@RequestParam("focus_image") MultipartFile file,
                                    @RequestParam("focus_image1") MultipartFile file2,
                                    @RequestParam("focus_image2") MultipartFile file3,
                                    FocusImageModel focusImageModel,
                                    HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> data) {
        int column = RequestTools.RequestInt(request, "column", 0);
        if (file != null && !Strings.isNullOrEmpty(file.getOriginalFilename())) {
            String file_path = FileUtil.saveFile(file);
            focusImageModel.setImage(file_path);
        }
        focusImageModel.setCreate_time(new Date());
        focusImageModel.setStatus(1);
        if(focusImageModel.getId()>0) {
            focusImageService.updateFocusImage(focusImageModel);
        }else{
            focusImageService.addFocusImage(focusImageModel);
        }
        data.put("focusImage0", focusImageModel);

        FocusImageModel focusImageModel1 = buildParameter(1, request);
        if (file2 != null && !Strings.isNullOrEmpty(file2.getOriginalFilename())) {
            String file_path1 = FileUtil.saveFile(file2);
            if(!Strings.isNullOrEmpty(file_path1)) {
                focusImageModel1.setImage(file_path1);
            }
        }
        focusImageModel1.setColumn(column);
        if(focusImageModel1.getId()>0) {
            focusImageService.updateFocusImage(focusImageModel1);
        }else{
            focusImageService.addFocusImage(focusImageModel1);
        }
        data.put("focusImage1", focusImageModel1);


        FocusImageModel focusImageModel2 = buildParameter(2, request);
        if (file3 != null && !Strings.isNullOrEmpty(file3.getOriginalFilename())) {
            String file_path2 = FileUtil.saveFile(file3);
            if(!Strings.isNullOrEmpty(file_path2)) {
                focusImageModel2.setImage(file_path2);
            }
        }
        focusImageModel2.setColumn(column);
        if(focusImageModel2.getId()>0) {
            focusImageService.updateFocusImage(focusImageModel2);
        }else{
            focusImageService.addFocusImage(focusImageModel2);
        }

        data.put("focusImage2", focusImageModel2);
        data.put("column", column);
        data.put("isCreate",false);
        //return "admin/focus_image_manage";
        //request.setAttribute("column",column);
        //return focus_manage(data,request,response);
        return "redirect:/admin/focus_manage.action?isCreate=false&column="+column;
    }

    /**
     * 封装参数
     *
     * @param index
     * @param request
     * @return
     */
    FocusImageModel buildParameter(int index, HttpServletRequest request) {
        FocusImageModel focusImageModel = new FocusImageModel();
        int id = RequestTools.RequestInt(request, "id" + index, 0);
        String title = RequestTools.RequestString(request, "title" + index, "");
        String link = RequestTools.RequestString(request, "link" + index, "");
        String image = RequestTools.RequestString(request, "image" + index, "");
        focusImageModel.setImage(image);
        focusImageModel.setCreate_time(new Date());
        focusImageModel.setId(id);
        focusImageModel.setTitle(title);
        focusImageModel.setLink(link);
        focusImageModel.setStatus(1);
        return focusImageModel;
    }

    @RequestMapping("query_focus_image.action")
    @ResponseBody
    public String query_focus_image(HttpServletRequest request, HttpServletResponse response) {
        int column = RequestTools.RequestInt(request, "column", 0);
        List<FocusImageModel> focusImageModelList = focusImageService.queryFocusImageByColumn(column);
        return JsonTools.jsonSer(focusImageModelList);
    }


}

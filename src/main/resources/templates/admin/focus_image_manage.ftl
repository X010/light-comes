<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box box-primary">
                    <form id="create_focus_form" name="create_focus_form" action="<#if isCreate> create_focus_image_save.action</#if>update_focus_image_save.action"
                          method="post" enctype="multipart/form-data">
                        <div class="box-header with-border">
                            <h3 class="box-title">焦点图管理</h3>

                            <div class="form-group">
                                <label>选择栏目</label>
                                <select id="column" name="column" class="form-control">
                                    <option value="1" <#if column==1>selected</#if>>抽奖</option>
                                    <option value="2" <#if column==2>selected</#if>>砍价</option>
                                    <option value="3" <#if column==3>selected</#if>>拍卖</option>
                                    <option value="4" <#if column==4>selected</#if>>约饭</option>
                                </select>
                            </div>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">标题1</label>
                                <input type="hidden" class="form-control" name="image" id="image" value="${focusImage0.image!}">
                                <input type="hidden" class="form-control" name="id" id="id" value="${focusImage0.id!}">
                                <input type="text" class="form-control" name="title" id="title" placeholder="标题1"
                                       value="${focusImage0.title!}">
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="focus_image">焦点图1</label>

                                <div class="controls">
                                    <input class="input-file uniform_on" id="focus_image" name="focus_image"
                                           type="file">

                                    <div id="image-div" class="overflow">
                                    <#if focusImage0.image??>
                                        <img id="focus_image_preview" name="focus_image_preview"
                                             src="${focusImage0.image}" width="200" height="100">
                                    </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="链接地址1"></label>
                                <input type="text" name="link" class="form-control" id="link"
                                       placeholder="链接地址1" value="${focusImage0.link}">
                            </div>
                            <!-------2------->
                            <div class="form-group">
                                <label for="title">标题2</label>
                                <input type="hidden" class="form-control" name="id1" id="id1"
                                       value="${focusImage1.id!}">
                                <input type="hidden" class="form-control" name="image1" id="image1" value="${focusImage1.image!}">
                                <input type="text" class="form-control" name="title1" id="title1" value="${focusImage1.title}" placeholder="标题">
                            </div>

                            <div class="control-group">
                                <label class="control-label" for="focus_image1">焦点图2</label>

                                <div class="controls">
                                    <input class="input-file uniform_on" id="focus_image1" name="focus_image1"
                                           type="file">

                                    <div id="image-div1" class="overflow">
                                    <#if focusImage1.image??>
                                        <img id="focus_image_preview1" name="focus_image_preview1"
                                             src="${focusImage1.image}" width="200" height="100">
                                    </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="link1"></label>
                                <input type="text" name="link1" class="form-control" id="link1"
                                       value="${focusImage1.link!}"
                                       placeholder="链接地址2">
                            </div>
                            <!-------3------->
                            <div class="form-group">
                                <label for="title">标题3</label>
                                <input type="hidden" class="form-control" name="id2" id="id2" value="${focusImage2.id!}">
                                <input type="hidden" class="form-control" name="image2" id="image2" value="${focusImage2.image!}">
                                <input type="text" class="form-control" name="title2" id="title2" value="${focusImage2.title!}" placeholder="标题3">
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="focus_image2">焦点图3</label>
                                <div class="controls">
                                    <input class="input-file uniform_on" id="focus_image2" name="focus_image2"
                                           value="${focusImage2.image}"
                                           type="file">

                                    <div id="image-div2" class="overflow">
                                    <#if focusImage2.image??>
                                        <img id="focus_image_preview2" name="focus_image_preview2"
                                             src="${focusImage2.image}" width="200" height="100">
                                    </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="link2"></label>
                                <input type="text" name="link2" class="form-control" id="link2"
                                       value="${focusImage2.link!}"
                                       placeholder="链接地址3">
                            </div>
                        <#--<div id="image-more" class="overflow"></div>-->
                        <#--<span class="help-block text-right"><a href="javascript:void(0);"-->
                        <#--onclick="addMoreImage(this);">添加更多图片</a></span>-->
                        </div>

                        <div class="box-footer">
                            <div class="pull-right">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>保存
                                </button>
                            </div>
                            <button type="reset" class="btn btn-default"><i class="fa fa-times"></i>取消</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $("#column").change(function () {
        var id = $(this).children('option:selected').val();
        $.ajax({
            type: "post",
            url: "query_focus_image.action",
            data: "column=" + id,
            success: function (result) {
                if (result == null || result == '[]') {
                    $("#create_focus_form").attr('action',"create_focus_image_save.action");
                    clearForm();

                } else {
                    var r = jQuery.parseJSON(result);
                    $("#create_focus_form").attr('action',"update_focus_image_save.action");
                    clearForm();
                    for (var i in r) {
                        var image=r[i];
                        if(i==0) {
                            $("#id").val(image.id);
                            $("#title").val(image.title);
                            $("#link").val(image.link);
                            $("#image").val(image.image);
                            $("#focus_image_preview").remove();
                            $("#image-div").append('<img id="focus_image_preview" name="focus_image_preview" src="'+image.image+'" width="200" height="100">');
                        }else{
                            $("#id"+i).val(image.id);
                            $("#title"+i).val(image.title);
                            $("#link"+i).val(image.link);
                            $("#image"+i).val(image.image);
                            $("#image-focus_image_preview"+i).remove();
                            $("#image-div"+i).append('<img id="focus_image_preview'+i+'" name="focus_image_preview'+i+'" src="'+image.image+'" width="200" height="100">');
                        }
                        i++;
                        //$("#focus_image_preview").attr('src', image.image);
                    }
                }
//                $("#introduce").val(r.introduce);
//                $("#crowd").val(r.crowd);
//                $("#video_url").val(r.videoUrl);
//                datas(id);
//                if (r.courseAttr.length > 0) {
//                    var attrs = r.courseAttr.split("#");
//                    $("#course_attr").empty();
//                    for (index in attrs) {
//                        $("#course_attr").append("<option selected value='" + attrs[index] + "'>" + attrs[index] + "</option>").trigger("chosen:updated");;
//                    }
//                }
//                var attrs = r.courseAttr.split("#");
//                $("#course_attr").empty();
//                myDropzoneVideoPreview.on("init",function() {
//                    this.emit("initimage", r.videoPreview); //初始化图片
//                });
//                myDropzoneImages.on("init",function() {
//                    this.emit("initimage", r.images); //初始化图片
//                });
//
//                for (index in attrs) {
//                    $("#course_attr").append("<option selected value='" + attrs[index] + "'>" + attrs[index] + "</option>").trigger("chosen:updated");;
//                }
            }
        });
    });

    //清空form数据
    function clearForm(){
        $("#id").val("0");
        $("#id1").val("");
        $("#id2").val("");
        $("#title").val("");
        $("#title1").val("");
        $("#title2").val("");
        $("#title").val("");
        $("#link").val("");
        $("#link1").val("");
        $("#link2").val("");
        $("#image").val("");
        $("#image1").val("");
        $("#image2").val("");
        $("#focus_image_preview").remove();
        $("#focus_image_preview1").remove();
        $("#focus_image_preview2").remove();
    }

    $(function () {
        document.title = "焦点图上传";
        $("#estate").addClass("active");
        setNav("焦点图", "创建焦点图");
    });

    /**
     * 增加更多图片
     */
    function addMoreImage() {
        var obj = $(".remove-this");
        var index = obj.length;
        if (obj.length < 2) {
            $("#image-more").append('<div class="remove-this"><div class="form-group"> <label for="title">标题</label> <input type="text" class="form-control" name="title" id="title" placeholder="标题"> </div> <div class="control-group"> <label class="control-label" for="focus_image">焦点图</label> <div class="controls"> <input class="input-file uniform_on" id="focus_image" name="focus_image" type="file"> </div> </div> <div class="form-group"> <label for="链接地址"></label> <input type="text" name="link" class="form-control" id="link"placeholder="链接地址"> </div>' +
            '<a href="javascript:void(0);" class="btn btn-sm btn-default col-sm-2" onclick="removeImage(this);">删除</a></div></div>');
        } else {
            alert("最多添加3张焦点图");
            return;
        }
    }

    //移除图片信息
    function removeImage(obj) {
        $(obj).parent(".remove-this").remove();
    }
</script>
<#include "in_footer.ftl">
$(function () {
    var heigth = $(window).height();
    $('#js-aside').height(heigth - 50);
    $('#js-right-content').height(heigth - 50);

    $('.js-collapse').on('hidden.bs.collapse', function () {
        var i = $(this).parent().find('span.pull-right').find('i');
        i.removeClass('fa-angle-down');
        i.addClass('fa-angle-right');
        $(this).parent().find('.panel-heading').removeClass('header-active');
    }).on('show.bs.collapse', function () {
        var i = $(this).parent().find('span.pull-right').find('i');
        i.removeClass('fa-angle-right');
        i.addClass('fa-angle-down');
        $(this).parent().find('.panel-heading').addClass('header-active');
    })

    $('#init').trigger('click');
});

function goTo(url, target) {
    $('.menu-a').removeClass('white');
    $(target).addClass('white');
    setTimeout(function () {
        $('#js-message').find('.alert').alert('close');
    }, 2000)
    $.get(url, {}, function (data) {
        $('#js-right-content').html(data);
        var myRules = {
            rules: {
                name: {
                    required: true
                },
                model: {
                    required: true
                },
                material: {
                    required: true
                },
                size: {
                    required: true
                },
                img: {
                    required: true
                },
                detail: {
                    required: true
                }
            }
        };
        $("#js-new-product").validate($.extend(myRules, custom));
        var fileUploadRules = {
            rules: {
                name: {
                    required: true
                },
                videoDesc: {
                    required: true
                },
                videoLink: {
                    required: true
                },
                pdfFile: {
                    required: true
                }
            }
        };
        $("#js-file-upload").validate($.extend(fileUploadRules, custom));
        $('#summernote').summernote({
            lang: 'zh-CN',
            callbacks: {
                onImageUpload: function (files) {
                    sendFile(files[0], $(this));
                }
            },
            height: 300,
            minHeight: null,
            maxHeight: null,
            focus: true
        });
        var newsRules = {
            rules: {
                title: {
                    required: true
                },
            }
        };
        $("#js-add-news").validate($.extend(newsRules, custom));
    }, 'html');
}

function goToPage(target) {
    $.get($(target).attr('href'), {}, function (data) {
        $('#js-right-content').html(data);
    }, 'html');
    event.preventDefault();
}

function upoad(target) {
    var name = target.files[0].name;
    $(target).parent().parent().find('.js-filename').text(name);
}

function updateProductUploadImg(target) {
    var name = target.files[0].name;
    $(target).parent().parent().find('.js-filename').val(name);
}

function updateProduct(target) {
    $.get($(target).attr('href'), {}, function (data) {
        $('#js-right-content').html(data);
    }, 'html');
    event.preventDefault();
}

function displayDiv(target) {
    var choiceLevel = $(target).val();
    if (choiceLevel == 2) {
        $("#product_one_level").show();
    } else {
        $("#product_one_level").hide();
    }
}

function addFileInput() {
    var componet = '<div class="form-group extend">' +
        '<label class="col-sm-3 control-label"></label>' +
        '<div class="col-sm-9 need-validate"><span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span><input type="file" name="detailFiles" multiple="multiple" onchange="upoad(this)"/></span><button type="button" class="btn btn-warning" onclick="cancel(this)" style="margin-left: 5px">取消</button><span class="js-filename"></span></div>' +
        '</div>';
    $('#js-file-input-div').append(componet);
}

function cancel(target) {
    $(target).parent().parent().remove();
}

function back() {
    window.location.href = "javascript:history.go(-1)";
}

function createNews() {
    var markupStr = $('#summernote').summernote('code');
    var title = $('#js-news-title').val();
    if ($("#js-add-news").valid()) {
        $.post('/admin/news', {title: title, content: markupStr}, function (data) {
            $('#js-right-content').html(data);
        });
    }
}

function sendFile(file, editor) {
    var data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        url: "/admin/upload-news-picture",
        cache: false,
        processData: false,
        contentType: false,
        success: function (url) {
            console.info(url)
            editor.summernote('insertImage', url);
        }
    });
}
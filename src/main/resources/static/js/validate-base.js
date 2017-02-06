jQuery.extend(jQuery.validator.messages, {
    required: "该项不能为空",
    email: "请填写正确格式的邮箱"
});

var custom = {
    focusCleanup: false,
    errorElement: 'span',
    highlight: function (element) {
        $(element).parents('.form-group').removeClass('success').addClass('error');
    },
    success: function (element) {
        $(element).parents('.form-group').removeClass('error').addClass('success');
        $(element).parents('.need-validate:not(:has(.clean))').find('input').after('<div class="clean"></div>');
        $(element).remove();
    },
    errorPlacement: function (error, element) {
        error.appendTo(element.parents('.need-validate'));
    }
};
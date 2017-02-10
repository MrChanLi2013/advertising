$(function () {
    $('.dropdown').hover(function () {
        if (!$(this).hasClass('open')) {
            $('.dropdown-toggle', this).trigger('click');
        }
    }, function () {
        $(this).removeClass('open');
    });


    $("#js-order-btn").click(function () {
        $('html, body').animate({
            scrollTop: $("#order-info").offset().top - 150
        }, 1000);
    });

    $("#js-create-order").click(function () {
        var myRules = {
            rules: {
                name: {
                    required: true
                },
                companyName: {
                    required: true
                },
                phone: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                // province: {
                //     required: true
                // },
                // city: {
                //     required: true
                // },
                area: {
                    required: true
                }
            },
            messages: {
                // province: {
                //     required: "请选择省份"
                // },
                // city: {
                //     required: "请选择市区"
                // },
                area: {
                    required: '请选择所属地区'
                }
            }
        };
        $("#js-new-order").validate($.extend(myRules, custom));
        var valid = $("#js-new-order").valid();
        if (valid) {
            $.post('/ad/order', $('#js-new-order').serialize(), function (data) {
                if (data == 1) {
                    alert("订购成功");
                } else {
                    alert("订购失败")
                }
            }, 'json');
        }
    });


    var links = window.location.pathname.split('/');
    var secondaryUrl = links[2];
    $('#js-menu').find('li.dropdown').removeClass('active');
    $('#js-' + secondaryUrl).addClass('active');

    //product js

    $('.radio-button').click(function () {
        var id = $(this).parent().next().val();
        if ($(this).hasClass('off')) {
            $(this).removeClass('off');
            $(this).addClass('on');
            $.post('/ad/product-list/detail', {id: id}, function (data) {
                $('#js-product-detail').append($(data)[5].innerHTML);
                $('.img-zoom').zoom();
            }, 'html');
            return;
        }
        if ($(this).hasClass('on')) {
            $('#product-' + id).remove();
            $(this).removeClass('on');
            $(this).addClass('off');
            return;
        }
    });

    $('#js-reset').click(function () {
        $('#js-product-detail').html('');
        $('.radio-button').removeClass('on');
        $('.radio-button').addClass('off');
    });

    $('.member-img').hover(function () {
        var info = $(this).next();
        info.css({
            width: $(this).parent().width(),
            height: $(this).parent().height()
        })
        info.fadeIn();
        $(this).hide();
    }, function () {

    })

    $('.info-content').hover(function () {

    }, function () {
        var img = $(this).parent().find('.member-img');
        img.show();
        $(this).hide();
    })
});

function forwardTo(id) {
    $('html, body').animate({
        scrollTop: $("#" + id).offset().top - 150
    }, 1000);
}
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
                meaterial: {
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
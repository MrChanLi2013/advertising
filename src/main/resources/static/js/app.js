$(function () {
    $('.dropdown').hover(function () {
        if (!$(this).hasClass('open')) {
            $('.dropdown-toggle', this).trigger('click');
        }
    }, function () {
        $(this).removeClass('open');
    });

    var links = window.location.pathname.split('/');
    var secondaryUrl = links[2];
    $('#js-menu').find('li.dropdown').removeClass('active');
    $('#js-' + secondaryUrl).addClass('active');

    //product js

    $(".radio-botton").click(function () {
        $.post('/ad/product-list/detail', {id: 1}, function (data) {
            $('#js-product-detail').append($(data)[5].innerHTML);
        }, 'html');

        if ($(this).hasClass('off')) {
            $(this).removeClass('off');
            $(this).addClass('on');
            return;
        }
        if ($(this).hasClass('on')) {
            $(this).removeClass('on');
            $(this).addClass('off');
            return;
        }
    })
});
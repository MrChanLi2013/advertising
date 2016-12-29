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


    $(".radio-botton").click(function () {
        if ($(this).hasClass('off')) {
            $(this).removeClass('off');
            $(this).addClass('on');
            return;
        }
        if ($(this).hasClass('on')){
            $(this).removeClass('on');
            $(this).addClass('off');
            return;
        }
    })
});
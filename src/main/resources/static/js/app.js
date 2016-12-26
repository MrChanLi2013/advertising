$(function () {
    $('.dropdown').hover(function () {
        $('.dropdown-toggle', this).trigger('click');
    });

    var links = window.location.pathname.split('/');
    var secondaryUrl = links[2];
    $('#js-menu').find('li.dropdown').removeClass('active');
    $('#js-' + secondaryUrl).addClass('active');
});
$(document).ready(() => {
    $('.forget').click(() => {
        $('.popup').toggleClass('show')
    })
    $(".checkbox").change(function () {
        $(this).setAttribute("value", this.checked ?
            "on" : "off")
    });
})
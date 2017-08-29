$(document).ready(() => {
    var inputHolder = $("#shorten_input");
    var resultHolder = $("#shorten_result > input");
    var shortenButton = $("#shorten_button");

    $(shortenButton).click(() => {
        var url = inputHolder.val();
        $.post("shorten", { "url": url }, (result) => {
            resultHolder.val(result);
        }, "text");
    });

    setInterval(() => update(), 15 * 1000);
});


function updateClicks(value) {
    $("#totalClicks").html(value);
}

function updateUrls(value) {
    $("#totalUrls").html(value);
}

function update() {
    var self = this;
    var url = "global/";
    $.post(url + "clicks", (value) => {
        self.updateClicks(value);
    });
    $.post(url + "urls", (value) => {
        self.updateUrls(value);
    });
}
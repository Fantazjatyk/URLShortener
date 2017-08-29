$(document).ready(function () {
    var inputHolder = $("#shorten_input");
    var resultHolder = $("#shorten_result > input");
    var shortenButton = $("#shorten_button");
    $(shortenButton).click(function () {
        var url = inputHolder.val();
        $.post("shorten", { "url": url }, function (result) {
            resultHolder.val(result);
        }, "text");
    });
    setInterval(function () { return update(); }, 15 * 1000);
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
    $.post(url + "clicks", function (value) {
        self.updateClicks(value);
    });
    $.post(url + "urls", function (value) {
        self.updateUrls(value);
    });
}
//# sourceMappingURL=main.js.map
function bindAll() {
    var all = $(".click-record");
    for (var i = 0; i < all.length; i++) {
        var el = all[i];
        bind(el);
    }
}
function bind(ele) {
    $(ele).find("button").off().on("click", function () {
        $(ele).find(".click-details").toggle();
    });
}
$(document).ready(function () {
    bindAll();
});
bindAll();
//# sourceMappingURL=h.js.map
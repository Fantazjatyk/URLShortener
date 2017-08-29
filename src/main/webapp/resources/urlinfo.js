var SiteLoader = (function () {
    function SiteLoader() {
    }
    SiteLoader.prototype.setSwapper = function (swapper) {
        this.swapper = swapper;
    };
    SiteLoader.prototype.load = function (adress, where) {
        $("#" + where).load(document.location.toString() + "/" + adress);
    };
    return SiteLoader;
}());
$(document).ready(function () {
    var loader = new SiteLoader();
    $("#overview").off().click(function () {
        loader.load("overview", "swap");
    });
    $("#history").off().click(function () {
        loader.load("history", "swap");
    });
    $("#overview").click();
});
//# sourceMappingURL=urlinfo.js.map
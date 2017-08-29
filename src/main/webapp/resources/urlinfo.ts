
class SiteLoader {
    swapper;

    setSwapper(swapper: string) {
        this.swapper = swapper;
    }

    load(adress: string, where: string) {
        $("#" + where).load(document.location.toString() + "/" + adress);
    }
}


$(document).ready(() => {
    var loader = new SiteLoader();

    $("#overview").off().click(() => {
        loader.load("overview", "swap");
    });
    $("#history").off().click(() => {
        loader.load("history", "swap");
    });

    $("#overview").click();
});

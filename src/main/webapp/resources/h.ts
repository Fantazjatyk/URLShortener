        function bindAll() {
            var all = $(".click-record");

            for (let i = 0; i < all.length; i++) {
                var el = all[i];
                bind(el);
            }
        }

        function bind(ele) {
            $(ele).find("button").off().on("click", () => {
                $(ele).find(".click-details").toggle();
            });
        }

        $(document).ready(() => {
            bindAll();
        });
        bindAll();
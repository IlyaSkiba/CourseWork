StudentTheoretic = function () {
    var call = 0;
    return {
        bindFocusing: function () {

            var blurFunc = function (e) {
                $(".Question").css("display", "none");
                PrimeFaces.ab(
                    {
                        formId: getHiddenObjects().cheaterForm,
                        source: getHiddenObjects().cheaterAttr,
                        process: getHiddenObjects().cheaterAttr,
                        update: getHiddenObjects().updateVal,
                        event: "action",
                        oncomplete: cheaterReminder.show()

                    }
                );
            };
            // Is this a version of IE?
            if ($.browser.msie) {
                $(document).on('blur', blurFunc);
            } else {
                $(window).on('blur', blurFunc);
            }
        }
    }
}
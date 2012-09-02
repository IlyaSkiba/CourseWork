StudentTheoretic = function () {
    var call = 0;
    return {
        bindFocusing:function () {

            var blurFunc = function () {
                $(".Question").css("display", "none");
                PrimeFaces.ab(
                    {
                        formId:getHiddenObjects().cheaterForm,
                        source:getHiddenObjects().cheaterAttr,
                        process:getHiddenObjects().cheaterAttr,
                        update:getHiddenObjects().updateVal,
                        event:"action",
                        oncomplete:cheaterReminder.show()

                    }
                );
            };
            // Is this a version of IE?
            if ($.browser.msie) {
                $(window).bind('blur', blurFunc);
            } else {
                $(document).bind('blur', blurFunc);
            }
        }
    }
}
StudentTheoretic = function () {
    var call = 0;
    return {
        bindFocusing:function () {
            window.document.onblur = function () {
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
        }
    }
}
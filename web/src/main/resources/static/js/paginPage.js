$(document).ready(function () {
    changePageAndSize();
    changePageAndSizeDoc();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (evt) {
        // window.location.replace("/cabinet?&sizePart=" + this.value);
        window.location.search = jQuery.query.set("sizePart", this.value);
    });
}

function changePageAndSizeDoc() {
    $('#pageSizeSelectDoc').change(function (evt) {
        // window.location.replace("/cabinet?&sizeDoc=" + this.value);
        window.location.search = jQuery.query.set("sizeDoc", this.value);
    });
}

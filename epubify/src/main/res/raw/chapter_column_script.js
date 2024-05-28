var columnWidth;
var columnGap = 0;

function initColumns() {
    // Get the body element
    var body = document.getElementsByTagName('body')[0];

    // Get the height and width of the window (WebView)
    var windowHeight = window.innerHeight;
    var windowWidth = window.innerWidth;

    // Get the full height of the body content
    var contentHeight = body.offsetHeight;

    // Calculate the number of pages based on the content height and window height
    var pageCount = Math.floor(contentHeight / windowHeight) + 1;

    // Calculate the new width of the body to accommodate all pages side by side
    var newBodyWidth = pageCount * windowWidth;

    // Set the height and width of the body
    body.style.height = windowHeight + 'px';
    body.style.width = newBodyWidth + 'px';

    // Set the number of columns to match the number of pages
    columnWidth = windowWidth;
    body.style.webkitColumnCount = pageCount;
    body.style.webkitColumnWidth = columnWidth;
    body.style.webkitColumnHeight = windowHeight;
    body.style.webkitColumnGap = columnGap + 'px';
    body.style.padding = '0px';
    body.style.margin = '0px';

    // Scroll to the first page
    return pageCount;
}

function scrollToPage(page) {
    var position = page * (columnWidth);
    window.WebViewBridge.animateScrollToPosition(window.devicePixelRatio, position);
}

initColumns();
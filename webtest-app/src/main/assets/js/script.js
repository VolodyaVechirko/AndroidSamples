
// Android bridge
function androidToast(msg) {
   AndroidJsBridge.showToast(msg);
}

function androidLog(msg) {
   AndroidJsBridge.log(msg);
}
//

// On page load
function onLoadPage() {
    var title = document.title
    var url = new URL(window.location.href);
    var param1 = url.searchParams.get("param1");
    var param2 = url.searchParams.get("param2");

    androidLog("title: " + title);
    androidLog("param1: " + param1);
    androidLog("param2: " + param2);

    var text = "title: " + title + ";<br>param1: " + param1 + ";<br>param2: " + param2;
    document.getElementById("initData").innerHTML = text;
}
//

function testAndroidToast() {
    var msg = document.getElementById("inputText").value;
    androidToast(msg);
}

function testAlert() {
    alert("Test alert message!");
}

function square(x) {
    return x * x
}

function setDataMsg(param) {
    document.getElementById("dataByFunc").innerHTML = "Data from app: " + param;
}

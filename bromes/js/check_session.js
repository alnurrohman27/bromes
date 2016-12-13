$(document).ready(function () {
	var url_now = window.location.href.toString().split(window.location.host)[1];
    if(!sessionStorage.name && url_now != "/bromes/#/login"){
        window.location.reload();
    }
});
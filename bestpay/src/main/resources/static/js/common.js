function serializeObject(obj) {
    var o = {};
    var a = obj.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return JSON.stringify(o);

}

function randomString(len) {
    len = len || 32;
    var $chars = '1234567890';
    var maxPos = $chars.length;
    var ret = '';
    for (var i = 0; i < len; i++) {
        ret += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return ret;
}

/**************************************时间格式化处理************************************/
Date.prototype.format = function dateFtt(format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

function getReqSeq() {
    var prefix="REQ";
    var date = new Date().format('yyyyMMdd');
    return prefix+date + randomString(6);
}
function getExtOrderSeq() {
    var prefix="EXT";
    var date = new Date().format('yyyyMMdd');
    return prefix+date + randomString(6);

}

function basePath(){
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos)+"/";
    return localhostPath;
};

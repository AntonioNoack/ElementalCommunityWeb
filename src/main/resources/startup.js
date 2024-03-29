var captchaContainer = document.getElementById("captchaContainer");

captchaContainer.style.display = "none";

function cloneView(func, ctx, attr){
    return new func(ctx, attr)
}

function download(name, type, content){
    window.takeScreenShot = 0;
    var link = document.getElementById("download");
    var binStr = content, len = binStr.length, arr = new Uint8Array(len)
    for (var i=0;i<len;i++) {arr[i] = binStr.charCodeAt(i)}
    link.href = (URL.createObjectURL || webkitURL.createObjectURL)(new Blob([arr], {type:type}))
    link.download = name
    link.click()
}

var hasCaptcha = false;
function captchaCallback(){
    hasCaptcha = true;
	// will not be called, when loading too fast xD
}

var tl = window.TouchList;
if(tl){
    tl.prototype.toString = function(){
        var s = "TL"+this.length;
        for(var i=0;i<this.length;i++){
            s += this.item(i).toString() + ",";
        }
        return s + ((this.originalEvent == this) ? "" : this.originalEvent);
    }
    var t = window.Touch;
	t.prototype.toString = function(){
		var s = "Ts"+this.screenX+",c"+this.clientX+",p"+this.pageX;
		return s;
	}
}

function askCaptcha(callback, onError){
    captchaContainer.style.display = "";
    var container = document.getElementById("captchaDiv");
    grecaptcha.render(container, {
		sitekey: "6LcLqyYTAAAAADunaMHzxaaT1skxFNkHj7UvbTNe",
		callback: function(answer){
			console.log(answer);
			captchaContainer.style.display = "none";
			callback("web::"+answer);
		}
    })
}

var lastAsked = 0
function askForFile(callback) {
	var input = document.createElement('input')
	input.type = 'file'
	var asked = Date.now();
	if(Math.abs(asked-lastAsked) < 1000) return;
	console.log(asked - lastAsked, callback)
	lastAsked = asked;
	input.onchange = e => { 
		var file = e.target.files[0]
		var reader = new FileReader()
		reader.onload = readerEvent => {
			var content = readerEvent.target.result
			console.log(content)
			callback(content)
			lastAsked = Date.now();
		}
		reader.readAsText(file,'UTF-8')
	}
	input.click()
}

window.onerror = function(err,source,line){
    document.getElementById("err").innerText += err+"["+source+":"+line+"]"
}

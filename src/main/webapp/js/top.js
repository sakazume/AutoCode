function AsynScript(){
	var parm;


	AsynScript.prototype.ajax = function(parm) {

		//非同期
		if(!parm.async) {
			parm.async = false;
		}

		if(!parm.type) {
			parm.type ="POST";
		}

		//json固定
		if(!parm.dataType ) {
			parm.dataType  = "json";
		}

		//エラー処理
		if(!parm.error) {
			parm.error = function(xmlHttpRequest, textStatus, errorThrown) {
				alert("非同期実行エラー");
			}
		}

		$.ajax(parm);
	}
}


$(document).ready(function(){
	$(".tabbable li").on("click",function() {

		var url = $("#url").val();

		var asyn = new AsynScript();
		parm = {
				success: function(data){
					$(".details").html(data);
				},
				type:"POST",
				url:"bundleGet",
				dataType:"text",
				data:{"url": url }

		};
		asyn.ajax(parm);
	});

});

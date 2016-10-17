var MyConsole = function(console) {
	var obj = function() {
	}
	obj.print = function(content) {
		console.append("<li>" + content + "</li>");
	}

	obj.clear = function() {
		console.empty();
	}
	return obj;
}

$(document).ready(function() {
	(function(){
		var myConsole = MyConsole($("#ws-console"));
	    var oldLog = console.log;
	    console.log = function (message) {
	        // DO MESSAGE HERE.
	        myConsole.print(message);
	        oldLog.apply(console, arguments);
	    };
	})();

	var client;

	$("#ws-connect").on("click", function() {

		if (client) {
			console.log("请先关闭现有的链接");
			return;
		}

		// 建立STOMP Over WebSocket链接
		// 1. 检查和验证输入的参数
		var address = $("#ws-address").val();
		if (!address || address.length === 0) {
			console.log("请输入WebSocket链接地址！");
			return;
		}

		var topic = $("#ws-stomp-topic").val();
		if (!topic || topic.length === 0) {
			console.log("请输入监听的Topic地址！");
			return;
		}

		// 2. 建立链接
		console.log("开始建立链接，url：" + address);
		client = Stomp.client(address);
		client.connect({/*no header is required for test*/}, function() {
			console.log("链接建立成功！");
			// 链接存在期间，将所有接收到的消息打印出来
			console.log("注册监听器，Topic：" + topic);
			var subscription = client.subscribe(topic, function(message) {
				console.log("接受消息：\n" + message);
			});
			console.log("注册监听器完毕");
		}, function(errorMsg) {
			console.log("链接建立失败，errorMsg：" + errorMsg);
		});
	});

	$("#ws-disconnect").on("click", function() {
		if (!client) {
			return;
		}

		console.log("正在断开链接...");
		client.disconnect(function() {
			console.log("链接已断开");
			client = null;
		});
	});

	$("#ws-send").on("click", function() {
		if (!client) {
			console.log("请先建立链接");
			return;
		}

		var message = $("#ws-send-content").val();
		if (!message || message.length === 0) {
			console.log("请输入消息内容");
			return;
		}

		var destination = $("#ws-stomp-destination").val();
		if (!destination || destination.length === 0) {
			console.log("请输入STOMP目的地址");
			return;
		}

		console.log("发送消息，destination: " + destination + ", message: " + message);
		client.send(destination, {}, message);
	});

	$("#ws-address").val("ws://" + window.location.host + "/portfolio");
	$("#ws-stomp-topic").val("/topic/greeting");
	$("#ws-send-content").val("1");
	$("#ws-stomp-destination").val("/app/greeting");
	
});
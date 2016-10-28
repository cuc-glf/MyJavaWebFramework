define(['domReady', 'app/mainModuleConfig', 'app/mainService'], function(doc, mainModule, serviceModule) {

    mainModule.controller(
        'registerCtrl', ['service', '$http', function(service) {
            var self = this;
            this.onRegisterClick = function() {
                console.log("username: " + self.username + ", phone: " + self.phone);
                var result = service.register();
                console.log("result:");
                console.log(result);
            };
        }]
    );

    angular.bootstrap(document, ['main']);


});
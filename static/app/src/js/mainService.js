define(['domReady'], function() {
    var mod = angular.module('mainService', [])
        .factory('service', function($http) {
            var register = function() {
                return {
                    code: 0,
                    info: '注册成功'
                }
            };
            return {
                'register': register
            };
        });
    return mod;
});
define(["app/requirejs/b"], function(b) {
    var a = function() {

    };

    a.helloworld = function() {
        b.helloworld();
    };

    return a;
});
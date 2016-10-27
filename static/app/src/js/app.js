requirejs(['app/requirejs/a', 'app/requirejs/b', 'domReady'], function(a, b, dom) {
    console.log("a.helloworld calling...");
    a.helloworld();
    console.log("b.helloworld calling...");
    b.helloworld();
});
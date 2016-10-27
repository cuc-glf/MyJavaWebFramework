module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            files: ['Gruntfile.js', 'app/src/**/*.js']
        },
        "bower": {
            "install": {
                "options": {
                    "targetDir": "./app/lib",
                    "layout": "byType",
                    "install": true,
                    "verbose": false,
                    "cleanTargetDir": true
                }
            }
        },
        requirejs: {
            compile: {
                options: {
                    baseUrl: './app',
                    dir: './build',
                    optimize: 'none',
                    mainConfigFile: 'app/src/config.js',
                    modules: [
                        {
                            name: 'app/app'
                        }
                    ]
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-requirejs');

    grunt.registerTask('default', ['jshint', 'requirejs']);

};
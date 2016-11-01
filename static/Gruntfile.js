module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            files: [
                'Gruntfile.js',
                'app/src/js/**/*.js',
                '!app/src/js/jsx/**/*.js'
            ]
        },
        "bower": {
            "install": {
                "options": {
                    "targetDir": "./app/src/lib",
                    "layout": "byType",
                    "install": true,
                    "verbose": false,
                    "cleanTargetDir": true
                }
            }
        },
        babel: {
            options: {
                //sourceMap: true,
                plugins: ['transform-react-jsx'], // npm install babel-plugin-transform-react-jsx
                presets: ['es2015', 'react'] // npm install babel-preset-es2015 babel-preset-react
            },
            jsx: {
                files: [
                    {
                        expand: true,
                        cwd: 'app/src',
                        src: ['jsx/**/*.js'],
                        dest: 'app/src/js',
                        ext: '.js',
                        extDot: 'first'
                    }
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-babel');

    grunt.registerTask('default', ['babel', 'jshint']);

};
(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('storageImg', storageImg);

    function storageImg() {
        var directive = {
            restrict: 'AE',
            scope: {
                picid: '='
            },
            replace: true,
            template: '<img></img>',
            link: linkFunc,

        };

        return directive;

        function linkFunc(scope, element, attrs) {
            scope.$watch('picid', function (picid) {
                element.attr('src', 'api/storage?path=' + encodeURIComponent(picid));
            });
        }
    }
})();

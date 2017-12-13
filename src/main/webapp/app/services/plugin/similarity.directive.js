(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('similarity', similarity);

    similarity.$inject = [];

    function similarity() {
        var directive = {
            restrict: 'AE',
            replace: true,
            template: '<span></span>',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            scope.$watch('score', function () {
                var score = parseFloat(attrs.score);
                if (!!score) {
                    element.text((Math.round(score * 10000) / 100) + '%');
                }
            });
        }
    }
})();

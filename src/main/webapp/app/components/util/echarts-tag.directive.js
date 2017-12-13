(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('echartsTag', echartsTag);

    echartsTag.$inject = [];

    function echartsTag() {
        var directive = {
            restrict: 'AE',
            replace: true,
            template: '<div></div>',
            scope: {
                option: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            scope.$watch('option', function () {
                try {
                    var option = JSON.parse(attrs.option);
                    var chart = echarts.init(element.get()[0], 'shine');
                    chart.setOption(option);
                } catch (e) {
                }
            });
        }
    }
})();

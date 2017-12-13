(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('workRecordCarPlate', workRecordCarPlate);

    function workRecordCarPlate() {
        var directive = {
            restrict: 'AE',
            replace: true,
            template: '<div class="plate-color"></div>',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            scope.$watch('carplate', function () {
                element.addClass(carTargetIdColor(attrs.carplate));
                element.append(getPlate(attrs.carplate));
            });
        }
    }
    /**
     * 输入中文targetId 得到class 用于动态更改css样式
     * */
    function carTargetIdColor(targetId) {
        var plateColor =targetId.substr(targetId.length-1,targetId.length);
        if ('蓝'==plateColor)return 'plate-color-blue';
        if ('黄'==plateColor)return 'plate-color-yellow';
        if ('绿'==plateColor)return 'plate-color-green';
        if ('黑'==plateColor)return 'plate-color-black';
        if ('白'==plateColor)return 'plate-color-white';
        return 'plate-color';
    }

    /**
     * 输入中文targetId 得到车牌号
     * */
    function getPlate(targetId) {
        return targetId.substr(0,targetId.length-2);
    }
})();

(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('carDanger', carDanger);

    carDanger.$inject = ['$translate'];

    function carDanger($translate) {
        var directive = {
            restrict: 'AE',
            scope: {
                data: '=data'
            },
            replace: true,
            template: '<div>'+
            '<div class="flow-left"  ng-if="data.attention">'+
            '<span class="label danger label-danger" data-translate="glxssSecurityApp.workRecord.label.attention">警示</span>'+
            '</div>'+
            '<div class="flow-left"  ng-if="data.report">'+
            '<span class="label danger label-warning" data-translate="glxssSecurityApp.workRecord.label.report">已处理</span>'+
            '</div>'+
            '<div class="flow-left" ng-if="data.danger1">'+
            '<span class="label danger label-danger" data-translate="glxssSecurityApp.workRecord.label.danger1">假套牌</span>'+
            '</div>'+
            '<div class="flow-left" ng-if="data.danger2">'+
            '<span class="label danger label-danger" data-translate="glxssSecurityApp.workRecord.label.danger2">违章</span>'+
            '</div>'+
            '<div class="flow-left" ng-if="data.danger3">'+
            '<span class="label danger label-danger" data-translate="glxssSecurityApp.workRecord.label.danger3">其他违法</span>'+
            '</div>'+
            '<div class="flow-left" ng-if="!data.attention && !data.report && !data.danger1 && !data.danger2 && !data.danger3">'+
            '<span class="label danger nothing" data-translate="glxssSecurityApp.workRecord.label.nothing">无</span>'+
            '</div>'+
            '</div>'
        };

        return directive;
    }
})();


(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('personDanger', personDanger);

    personDanger.$inject = ['$translate'];

    function personDanger($translate) {
        var directive = {
            restrict: 'AE',
            replace: true,
            scope: {
                danger: '=danger'
            },
            template:
            '<div>'+
                '<div class="flow-left"  ng-if="danger >= 4">'+
                    '<span class="label danger label-danger" data-translate="glxssSecurityApp.workRecordFace.dangerLevel4"></span>'+
                '</div>'+
                '<div class="flow-left"  ng-if="danger == 3">'+
                    '<span class="label danger label-warning" data-translate="glxssSecurityApp.workRecordFace.dangerLevel3"></span>'+
                '</div>'+
                '<div class="flow-left"  ng-if="danger == 2">'+
                    '<span class="label danger label-primary" data-translate="glxssSecurityApp.workRecordFace.dangerLevel2"></span>'+
                '</div>'+
                '<div class="flow-left"  ng-if="danger == 1">'+
                    '<span class="label danger label-info" data-translate="glxssSecurityApp.workRecordFace.dangerLevel1"></span>'+
                '</div>'+
            '</div>',
        };

        return directive;
    }
})();

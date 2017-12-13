(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('personInfo', personInfo);

    personInfo.$inject = ['$translate'];

    function personInfo($translate) {
        var directive = {
            restrict: 'AE',
            scope: {
                person:'=person',
                workRecordId: '=workRecordId'
            },
            replace: true,
            template: '<div>'+
            '<a ui-sref="work-record-glxss-car-person({pid:person.id, workRecordId:workRecordId})" id="personInfo" ng-if="person">'+
                '<div>{{person.name}}</div>'+
                '<div>{{person.personId}}</div>'+
            '</a>'
        };
        return directive;
    }
})();


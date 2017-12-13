(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('WorkRecordGlxssDetailController', WorkRecordGlxssDetailController);

    WorkRecordGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkRecord', 'User'];

    function WorkRecordGlxssDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkRecord, User) {
        var vm = this;

        vm.workRecord = entity;
        vm.previousState = previousState.name;
        vm.workRecordsType = entity.type;

        vm.getTemplateUrl = function() {
            switch (vm.workRecordsType) {
                case 1: // 车牌识别
                    return "app/entities/work-record/work-record-car-plate-glxss-detail.html";
                default:
                    return "app/entities/work-record/work-record-glxss-detail.html";
            }
        };

        var unsubscribe = $rootScope.$on('glxssSecurityApp:workRecordUpdate', function(event, result) {
            vm.workRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

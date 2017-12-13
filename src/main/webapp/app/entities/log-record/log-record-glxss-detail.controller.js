(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LogRecordGlxssDetailController', LogRecordGlxssDetailController);

    LogRecordGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'LogRecord', 'workRecordEntity', 'User', 'Base64'];

    function LogRecordGlxssDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, LogRecord, workRecordEntity, User, Base64) {
        var vm = this;

        vm.logRecord = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.workRecordId = workRecordEntity.id;

        if(entity.text){
            vm.logRecord.textBase=window.decodeURIComponent(Base64.decode(entity.text));
        }

        var unsubscribe = $rootScope.$on('glxssSecurityApp:logRecordUpdate', function(event, result) {
            vm.logRecord = result;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();

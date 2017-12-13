(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('SystemConfigGlxssDetailController', SystemConfigGlxssDetailController);

    SystemConfigGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SystemConfig'];

    function SystemConfigGlxssDetailController($scope, $rootScope, $stateParams, previousState, entity, SystemConfig) {
        var vm = this;

        vm.systemConfig = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('glxssSecurityApp:systemConfigUpdate', function(event, result) {
            vm.systemConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

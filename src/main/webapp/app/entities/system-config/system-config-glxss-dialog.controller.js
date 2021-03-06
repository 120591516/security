(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('SystemConfigGlxssDialogController', SystemConfigGlxssDialogController);

    SystemConfigGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SystemConfig'];

    function SystemConfigGlxssDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SystemConfig) {
        var vm = this;

        vm.systemConfig = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.systemConfig.id !== null) {
                SystemConfig.update(vm.systemConfig, onSaveSuccess, onSaveError);
            } else {
                SystemConfig.save(vm.systemConfig, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:systemConfigUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

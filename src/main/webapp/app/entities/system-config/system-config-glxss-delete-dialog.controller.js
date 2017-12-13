(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('SystemConfigGlxssDeleteController',SystemConfigGlxssDeleteController);

    SystemConfigGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'SystemConfig'];

    function SystemConfigGlxssDeleteController($uibModalInstance, entity, SystemConfig) {
        var vm = this;

        vm.systemConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SystemConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

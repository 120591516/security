(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceGlxssDeleteController',LocalFaceGlxssDeleteController);

    LocalFaceGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalFace'];

    function LocalFaceGlxssDeleteController($uibModalInstance, entity, LocalFace) {
        var vm = this;

        vm.localFace = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalFace.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

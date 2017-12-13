(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('ShareLocalFaceSetGlxssDeleteController',ShareLocalFaceSetGlxssDeleteController);

    ShareLocalFaceSetGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalFaceSet'];

    function ShareLocalFaceSetGlxssDeleteController($uibModalInstance, entity, LocalFaceSet) {
        var vm = this;

        vm.localFaceSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalFaceSet.shareDelete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

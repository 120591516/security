(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceSetGlxssDeleteController',LocalFaceSetGlxssDeleteController);

    LocalFaceSetGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalFaceSet'];

    function LocalFaceSetGlxssDeleteController($uibModalInstance, entity, LocalFaceSet) {
        var vm = this;

        vm.localFaceSet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalFaceSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

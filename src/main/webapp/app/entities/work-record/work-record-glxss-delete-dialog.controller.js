(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('WorkRecordGlxssDeleteController',WorkRecordGlxssDeleteController);

    WorkRecordGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkRecord'];

    function WorkRecordGlxssDeleteController($uibModalInstance, entity, WorkRecord) {
        var vm = this;

        vm.workRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

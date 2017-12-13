(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LogRecordGlxssDeleteController',LogRecordGlxssDeleteController);

    LogRecordGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'LogRecord'];

    function LogRecordGlxssDeleteController($uibModalInstance, entity, LogRecord) {
        var vm = this;

        vm.logRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LogRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

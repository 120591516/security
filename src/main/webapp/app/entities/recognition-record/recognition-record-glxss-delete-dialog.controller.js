(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('RecognitionRecordGlxssDeleteController',RecognitionRecordGlxssDeleteController);

    RecognitionRecordGlxssDeleteController.$inject = ['$uibModalInstance', 'entity', 'RecognitionRecord'];

    function RecognitionRecordGlxssDeleteController($uibModalInstance, entity, RecognitionRecord) {
        var vm = this;

        vm.recognitionRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RecognitionRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();



(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceSetShareGlxssDialogController', LocalFaceSetShareGlxssDialogController);

    LocalFaceSetShareGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'LocalFaceSet'];

    function LocalFaceSetShareGlxssDialogController($timeout, $scope, $stateParams, $uibModalInstance, LocalFaceSet ) {
        var vm = this;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.name="";
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.datePickerOpenStatus.createdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function save () {
            vm.isSaving = true;
            if (vm.name !== null) {
                LocalFaceSet.shareSave({ id : $stateParams.id, userLogin : vm.name}
                , onSaveSuccess, onSaveError);
            } else {
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:localFaceSetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }
})();


(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceSetGlxssDialogController', LocalFaceSetGlxssDialogController);

    LocalFaceSetGlxssDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'LocalFaceSet', 'User'];

    function LocalFaceSetGlxssDialogController ($timeout, $scope, $uibModalInstance, entity, LocalFaceSet, User) {
        var vm = this;

        vm.localFaceSet = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.localFaceSet.id !== null) {
                LocalFaceSet.update(vm.localFaceSet, onSaveSuccess, onSaveError);
            } else {
                LocalFaceSet.save(vm.localFaceSet, onSaveSuccess, onSaveError);
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

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

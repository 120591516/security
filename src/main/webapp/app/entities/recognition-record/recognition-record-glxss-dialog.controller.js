(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('RecognitionRecordGlxssDialogController', RecognitionRecordGlxssDialogController);

    RecognitionRecordGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'RecognitionRecord', 'User', 'WorkRecord'];

    function RecognitionRecordGlxssDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, RecognitionRecord, User, WorkRecord) {
        var vm = this;

        vm.recognitionRecord = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.workrecords = WorkRecord.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.recognitionRecord.id !== null) {
                RecognitionRecord.update(vm.recognitionRecord, onSaveSuccess, onSaveError);
            } else {
                RecognitionRecord.save(vm.recognitionRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:recognitionRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setInfo = function ($file, recognitionRecord) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        recognitionRecord.info = base64Data;
                        recognitionRecord.infoContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

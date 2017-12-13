(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LogRecordGlxssDialogController', LogRecordGlxssDialogController);

    LogRecordGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'LogRecord', 'workRecordEntity', 'User', 'Base64'];

    function LogRecordGlxssDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, LogRecord, workRecordEntity, User, Base64) {
        var vm = this;

        vm.logRecord = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        // vm.workrecords = WorkRecord.query();
        vm.users = User.query();
        vm.workRecord = workRecordEntity;
        vm.workRecordId = workRecordEntity.id;
        vm.lastModifiedByLogin = workRecordEntity.lastModifiedByLogin;
        vm.lastModifiedById = workRecordEntity.lastModifiedById;
        if(entity.text){
            vm.logRecord.textarea = decode(entity.text);
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.logRecord.id !== null) {
                vm.logRecord.text = vm.logRecord.textarea;
                vm.logRecord.text = encode(vm.logRecord.text);
                LogRecord.update(vm.logRecord, onSaveSuccess, onSaveError);
            } else {
                vm.logRecord.type = 1;//目前只用文字在
                vm.logRecord.text = vm.logRecord.textarea;
                if(!!vm.logRecord.text){
                    vm.logRecord.text = encode(vm.logRecord.text);
                }
                vm.logRecord.workRecordId = vm.workRecordId;
                vm.logRecord.createdByLogin = vm.lastModifiedByLogin;
                vm.logRecord.createdById = vm.lastModifiedById;
                LogRecord.save(vm.logRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:logRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function encode (data) {
          return Base64.encode(window.encodeURIComponent(data));
        }

        function decode (data) {
            return window.decodeURIComponent(Base64.decode(data));
        }
    }
})();

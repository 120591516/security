

(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('WorkRecordGlxssDialogController', WorkRecordGlxssDialogController);

    WorkRecordGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
        'WorkRecord', 'User', '$translate', 'Person', 'CarPlate'];

    function WorkRecordGlxssDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity,
                                             WorkRecord, User, $translate, Person, CarPlate) {
        var vm = this;

        vm.workRecord = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.workRecordType = entity.type;
        vm.warnigs=[];
        vm.personWarning = {};

        vm.getTemplateUrl = function () {
            switch (vm.workRecordType) {
                case 1: // 车牌识别
                    return "app/entities/work-record/work-record-car-plate-glxss-dialog.html";
                default:
                    return "app/entities/work-record/work-record-face-glxss-dialog.html";
            }
        };

        vm.personWarnings = [
            "",
            $translate.instant("glxssSecurityApp.workRecordFace.dangerLevel1"),
            $translate.instant("glxssSecurityApp.workRecordFace.dangerLevel2"),
            $translate.instant("glxssSecurityApp.workRecordFace.dangerLevel3"),
            $translate.instant("glxssSecurityApp.workRecordFace.dangerLevel4")
        ];

        vm.plateWarnings = [
            "",
            $translate.instant("glxssSecurityApp.workRecord.label.danger1"),
            $translate.instant("glxssSecurityApp.workRecord.label.danger2"),
            $translate.instant("glxssSecurityApp.workRecord.label.danger3")
        ];

        selected();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.workRecordType == 1) {
                vm.workRecord.danger1 = 0;
                vm.workRecord.danger2 = 0;
                vm.workRecord.danger3 = 0;
                for (var i = 0; i < vm.warnigs.length; i++) {
                    if (vm.warnigs[i] == "") {
                        vm.workRecord.danger1 = 0;
                        vm.workRecord.danger2 = 0;
                        vm.workRecord.danger3 = 0;
                        break;
                    }
                    if (vm.warnigs[i] == $translate.instant(vm.plateWarnings[1])) {
                        vm.workRecord.danger1 = 1;
                        continue;
                    }
                    if (vm.warnigs[i] == $translate.instant(vm.plateWarnings[2])) {
                        vm.workRecord.danger2 = 1;
                        continue;
                    }
                    if (vm.warnigs[i] == $translate.instant(vm.plateWarnings[3])) {
                        vm.workRecord.danger3 = 1;
                    }
                }
            } else {
                for (var i = 0; i < vm.personWarnings.length; i++) {
                    if (vm.personWarning == $translate.instant(vm.personWarnings[i])) {
                        vm.workRecord.danger1 = i;
                        break;
                    }
                }
            }
            if (vm.workRecord.id !== null) {
                WorkRecord.update(vm.workRecord, onSaveSuccess, onSaveError);
            } else {
                WorkRecord.save(vm.workRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            if (result.type == 2 && result.targetId != null) {
                result.person = Person.get({id: result.targetId});
            }
            if (result.type == 1 && result.targetId != null) {
                result.carPlate = CarPlate.get({id: result.targetId});
            }

            $scope.$emit('glxssSecurityApp:workRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastRecognizedDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function selected() {
            if (vm.workRecordType == 1) {
                if(vm.workRecord.danger1!=null&&vm.workRecord.danger1>0){vm.warnigs.push($translate.instant(vm.plateWarnings[1]))}
                if(vm.workRecord.danger2!=null&&vm.workRecord.danger2>0){vm.warnigs.push($translate.instant(vm.plateWarnings[2]))}
                if(vm.workRecord.danger3!=null&&vm.workRecord.danger3>0){vm.warnigs.push($translate.instant(vm.plateWarnings[3]))}
            }else {
                if(vm.workRecord.danger1!=null&&vm.workRecord.danger1>0){vm.personWarning = $translate.instant(vm.personWarnings[vm.workRecord.danger1])}
            }
        }
    }
})();


(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceGlxssDialogController', LocalFaceGlxssDialogController);

    LocalFaceGlxssDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
                                              'LocalFace', 'LocalFaceSet', 'Upload', 'AuthServerProvider', '$translate'];

    function LocalFaceGlxssDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity,
                                             LocalFace, LocalFaceSet, Upload, AuthServerProvider, $translate) {
        var vm = this;

        vm.localFace = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.localfacesets = LocalFaceSet.query();
        vm.localFace.faceSetId = $stateParams.faceSetId;

        vm.gender = null;
        vm.genders = [
            {name: $translate.instant("global.genders.male"), id: 1},
            {name: $translate.instant("global.genders.female"), id: 2}
        ];
        vm.genders.forEach(function(gender) {
            if (gender.id == vm.localFace.gender) {
                vm.gender = gender;
            }
        });

        vm.upload = function(file) {
            if (!file) {
                return;
            }
            Upload.upload({
                url: '/api/local-face-sets/' + $stateParams.faceSetId + '/local-faces/images',
                headers: {'Authorization': 'Bearer ' + AuthServerProvider.getToken()},
                data: { file: file }
            },onSaveSuccess, onSaveError).then(function(resp) {
                vm.localFace.image = resp.data.path;
            }, function(resp) {

            });
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gender) {
                vm.localFace.gender = vm.gender.id;
            } else {
                vm.localFace.gender = null;
            }
            if (vm.localFace.id !== null) {
                LocalFace.update(vm.localFace, onSaveSuccess, onSaveError);
            } else {
                LocalFace.save(vm.localFace, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:localFaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

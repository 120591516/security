(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'AlertService', 'entity', 'User', 'JhiLanguageService', '$translate'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, AlertService, entity, User, JhiLanguageService, $translate) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;


        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            if(vm.user.password !==null){
                if (vm.user.password !== vm.user.confirmPassword) {
                    AlertService.error( $translate.instant("userManagement.password.error"));
                    return;
                }
            }
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();

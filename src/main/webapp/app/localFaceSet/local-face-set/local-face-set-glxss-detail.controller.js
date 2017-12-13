(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceSetGlxssDetailController', LocalFaceSetGlxssDetailController);

    LocalFaceSetGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocalFaceSet', 'AlertService', '$translate'];

    function LocalFaceSetGlxssDetailController($scope, $rootScope, $stateParams, previousState, entity, LocalFaceSet, AlertService, $translate) {
        var vm = this;
        vm.name = "";
        vm.save = save;
        vm.localFaceSet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('glxssSecurityApp:localFaceSetUpdate', function(event, result) {
            vm.localFaceSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function save () {
            vm.isSaving = true;
            if (vm.name !== null && vm.name != "") {
                LocalFaceSet.shareSave({ id : $stateParams.id, userLogin : vm.name}
                    , onSaveSuccess, onSaveError);
            } else {
                vm.isSaving = false;
                AlertService.error( $translate.instant("glxssSecurityApp.share.error.notNull"));
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $scope.$emit('glxssSecurityApp:localFaceSetUpdate', result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();

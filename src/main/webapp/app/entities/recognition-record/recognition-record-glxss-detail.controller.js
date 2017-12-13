(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('RecognitionRecordGlxssDetailController', RecognitionRecordGlxssDetailController);

    RecognitionRecordGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState',
            'DataUtils', 'entity', 'RecognitionRecord', 'User', 'WorkRecord', 'Base64', 'Person'];

    function RecognitionRecordGlxssDetailController($scope, $rootScope, $stateParams, previousState, DataUtils,
            entity, RecognitionRecord, User, WorkRecord, Base64, Person) {
        var vm = this;

        vm.recognitionRecord = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.type = entity.type;

        try {
            vm.info = JSON.parse(Base64.decode(vm.recognitionRecord.info));
            if (vm.type == 2) {
                // 人脸记录
                vm.info.forEach(function (item) {
                    item.person = Person.get({id: item.personInfoId});
                });
            }
        } catch(e) {
        }

        vm.getTemplateUrl = function() {
            switch (vm.type) {
                case 2: // 人脸识别
                    return "app/entities/recognition-record/recognition-record-face-glxss-detail.html";
                default:
                    return "app/entities/recognition-record/recognition-record-glxss-detail.html";
            }
        };

        var unsubscribe = $rootScope.$on('glxssSecurityApp:recognitionRecordUpdate', function(event, result) {
            vm.recognitionRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

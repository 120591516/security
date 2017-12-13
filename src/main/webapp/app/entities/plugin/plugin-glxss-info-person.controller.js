(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('PluginGlxssPersonInfoController', PluginGlxssPersonInfoController);

    PluginGlxssPersonInfoController.$inject = ['previousState', 'entity'];

    function PluginGlxssPersonInfoController(previousState, entity) {
        var vm = this;
        vm.ownerPerson = entity;
        vm.carInfo = entity;
        vm.previousState = previousState.name;
        vm.workRecordsType = entity.type;
        vm.workRecordId = previousState.params.id;
        vm.recognitionRecordId = previousState.params.rid;
    }
})();



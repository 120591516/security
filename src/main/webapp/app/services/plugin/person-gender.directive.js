(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('personGender', personGender);

    personGender.$inject = ['$translate'];

    function personGender($translate) {
        var directive = {
            restrict: 'AE',
            replace: true,
            scope: {
                gender: '=gender'
            },
            template:
                '<span>' +
                    '<span ng-if="gender == 1" data-translate="global.genders.male"></span>' +
                    '<span ng-if="gender == 2" data-translate="global.genders.female"></span>' +
                    '<span ng-if="gender != 1 && gender != 2" data-translate="global.genders.unknown"></span>' +
                '</span>'
        };

        return directive;
    }
})();

(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .directive('personImg', personImg);

    function personImg() {
        var directive = {
            restrict: 'AE',
            scope: {
                person: '=person',
                workRecordId: '=workRecordId',
                status: '=status',
                nostatus: '=nostatus'
            },
            replace: true,
            template:
                '<div>'+
                '<div ng-if="!nostatus">' +
                    '<div ng-if="status == -1">' +
                        '<span data-translate="glxssSecurityApp.workRecordFace.noMatch"></span>'+
                    '</div>'+
                    '<div ng-if="status == 0 || status == null">' +
                        '<span data-translate="glxssSecurityApp.workRecordFace.collectOnly"></span>'+
                    '</div>'+
                '</div>'+
                '<a ui-sref="work-record-glxss-car-person({pid:person.id, workRecordId:workRecordId})">'+
                    '<div ng-if="nostatus || status == 1">' +
                        '<img class="pic-lg person-img"></img>'+
                    '</div>'+
                '</a>'+
                '</div>',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            scope.$watch('picid', function () {
                element.find('img').attr('src', 'api/person-image?path=' + encodeURIComponent(attrs.picid));
            });
        }
    }
})();

(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info">' +
                    '{{"global.page.showing" | translate }}{{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
                    '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
                    '{{"global.page.of" | translate }}{{$ctrl.queryCount}}{{"global.page.items" | translate }}.' +
                '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('glxssSecurityApp')
        .component('jhiItemCount', jhiItemCount);
})();

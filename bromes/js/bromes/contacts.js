function datatables_contacts($scope,DTOptionsBuilder,$http,SweetAlert,$uibModal){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {
                extend: 'copy',                 
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                } 
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                } 
            },
            {
                extend: 'excel', 
                title: 'Daftar Departemen ',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {
                extend: 'pdf', 
                title: 'Daftar Departemen',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {
                extend: 'print',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                },
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);

    var data;
    
    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }

    $scope.refresh_table = function(){
        data = $.param({
            table:"contacts"
        });

        $http.post("php/search.php", data, config).
            success(function(data, status, headers, config) {
                $scope.list_contacts = data;
            });
    }
    $scope.refresh_table();

}


angular
    .module('bromes')
    .controller('datatables_contacts', datatables_contacts);
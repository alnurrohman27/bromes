function datatables_events($scope,DTOptionsBuilder,$http,SweetAlert,$uibModal){

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

    $scope.name = null;
    $scope.date = null;
    $scope.note = null;

    var data;
    
    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }

    $scope.refresh_table = function(){
        data = $.param({
            table:"events"
        });

        $http.post("php/search.php", data, config).
            success(function(data, status, headers, config) {
                $scope.list_events = data;
            });
    }
    $scope.refresh_table();

    $scope.insert_event = function(){
        data = $.param({
            table:"events",
            name:$scope.name,
            date:$scope.date,
            note:$scope.note
        });
        $http.post("php/insert.php", data, config).
            success(function(data, status, headers, config) {
                console.log(data);
            });
        setTimeout(function(){  $scope.refresh_table(); }, 200);
    }

    $scope.delete = function (no) {
        SweetAlert.swal({
                title: "Apakah anda yakin?",
                text: "Anda tidak akan bisa mengembalikan data yang sudah terhapus!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Ya, silahkan!",
                cancelButtonText: "Tidak, jangan lakukan!",
                closeOnConfirm: false,
                closeOnCancel: false },
            function (isConfirm) {
                if (isConfirm) {
                    data = $.param({
                        table:"events",
                        key:no
                    });
                    $http.post("php/delete.php", data, config).
                        success(function(data, status, headers, config) {
                            if(data == 1){
                                SweetAlert.swal("Terhapus!", "Data berhasil dihapus.", "success");
                                console.log("Hapus Kontak dengan No: "+no);
                            }
                            else{
                                SweetAlert.swal("Gagal", "Data akun gagal dihapus.", "error");
                            }
                        });
                    setTimeout(function(){  $scope.refresh_table(); }, 200);
                } 
                else {
                    SweetAlert.swal("Dibatalkan", "Data akun aman :)", "error");
                }
            });
    }

    $scope.reset_form = function(){
        $scope.name = null;
        $scope.date = null;
        $scope.note = null;
    }

    $scope.status = function (id,name){
        $scope.id = id;
        $scope.name = name;
        var modalInstance = $uibModal.open({
            templateUrl: 'views/bromes/modal/m_events.html',
            scope: $scope,
            controller: modal_events
        });
    }

}
function modal_events($scope,$http,$uibModalInstance){
    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }
    var data;
    $scope.refresh_status= function(){
        data = $.param({
            table:"send_events", 
            id:$scope.id
        });

        $http.post("php/search.php", data, config).
            success(function(data, status, headers, config) {
                $scope.list_send_events = data;
            });
    }
    $scope.refresh_status();
    $scope.cancel = function () {
        $scope.reset_form();
        $uibModalInstance.dismiss('cancel');
    };
}


angular
    .module('bromes')
    .controller('datatables_events', datatables_events)
    .controller('modal_events,', modal_events);
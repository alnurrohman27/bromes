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

    $scope.no = null;
    $scope.name = null;
    $scope.email = null;
    $scope.address = null;
    $scope.selected_privileges = null;

    var data = $.param({
            table:"privileges"
        });
    
    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }

    $http.post("php/search.php", data, config).
            success(function(data, status, headers, config) {
                $scope.list_privileges = data;
            });

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

    $scope.insert_contact = function(){
        data = $.param({
            table:"contacts",
            no:$scope.no,
            name:$scope.name,
            email:$scope.email,
            address:$scope.address,
            privileges:$scope.selected_privileges

        });
        $http.post("php/insert.php", data, config).
            success(function(data, status, headers, config) {
                console.log(data);
            });
        setTimeout(function(){  $scope.refresh_table(); }, 200);
    }

    $scope.reset_form = function (){
        $scope.no = null;
        $scope.name = null;
        $scope.email = null;
        $scope.address = null;
        $scope.selected_privileges = null;
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
                        table:"contacts",
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
    $scope.open_modal = function (no,name,email,address,privileges){
        $scope.no = no;
        $scope.old_no = no;
        $scope.name = name;
        $scope.email = email;
        $scope.address = address;
        $scope.selected_privileges = privileges;
        var modalInstance = $uibModal.open({
            templateUrl: 'views/bromes/modal/m_contacts.html',
            scope: $scope,
            controller: modal_contacts
        });
    }

}
function modal_contacts($scope,$http,$uibModalInstance){
    $scope.update_contact = function () {
        var data = $.param({
            table:"contacts",
            no:$scope.no,
            old_no:$scope.old_no,
            name:$scope.name,
            email:$scope.email,
            address:$scope.address,
            privileges:$scope.selected_privileges
        });
    
        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }

        $http.post("php/update.php", data, config).
            success(function(data, status, headers, config) {
                if(data==0){
                    $scope.message = "Gagal update kontak";
                }
                else{
                    console.log(data);
                    $scope.refresh_table();
                    $uibModalInstance.close();
                }
            }).
            error(function(data, status, headers, config) {
                $scope.message = "Gagal update kontak";
            });
    };

    $scope.cancel = function () {
        $scope.reset_form();
        $uibModalInstance.dismiss('cancel');
    };
}


angular
    .module('bromes')
    .controller('datatables_contacts', datatables_contacts)
    .controller('modal_contacts,', modal_contacts);
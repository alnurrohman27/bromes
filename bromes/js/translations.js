function config($translateProvider) {

    $translateProvider
        .translations('en', {

            // Define all menu elements
            DASHBOARD: 'Dashboard',
            CONTACTS: 'Contact',
            EVENTS: 'Event',
            MESSAGES: 'Message',
            LOG: 'Log',

            // Define some custom text
            LANGUAGE: 'Language',
            LOGOUT: 'Logout',
            WELCOME: 'Welcome',
            MESSAGEINFO: 'You have 42 messages and 6 notifications.',
            SEARCH: 'Search for something...',
            COPYRIGHT: "Copyright",
            FOOTER: "Created By Adian L. Nurrohman & Ignatius Benedict",
            DEMO: 'Internationalization (sometimes shortened to \"I18N , meaning \"I - eighteen letters -N\") is the process of planning and implementing products and services so that they can easily be adapted to specific local languages and cultures, a process called localization . The internationalization process is sometimes called translation or localization enablement .',
            ADD: 'Add'
        })

        .translations('idn', {

            // Define all menu elements
            DASHBOARD: 'Beranda',
            CONTACTS: 'Kontak',
            EVENTS: 'Acara',
            MESSAGES: 'Pesan',
            LOG: 'Log',

            // Define some custom text
            LANGUAGE: 'Bahasa',
            LOGOUT: 'Keluar',
            WELCOME: 'Selamat Datang',
            MESSAGEINFO: 'Kamu memiliki 42 pesan dan 6 notifikasi.',
            SEARCH: 'Mencari sesuatu...',
            COPYRIGHT: "Hak Cipta",
            FOOTER: "Dibuat oleh FP9",
            DEMO: 'Internasionalisasi (terkadang disingkat menjadi \"I18N , artiunya \"I - delapan huruf -N\") merupakan proses untuk persiapan dan implementasi produk dan servis agar bisa menyesuaikan budaya.',
            ADD: 'Tambah'
        });

    $translateProvider.preferredLanguage('idn');

}

angular
    .module('bromes')
    .config(config)

# Movie Catalogue - TMDB

**Movie Catalogue** adalah aplikasi katalog film dan serial TV yang mengambil data dari [TMDB API](https://www.themoviedb.org/documentation/api).

Aplikasi ini mendemonstrasikan implementasi praktik terbaik dalam pengembangan Android modern, termasuk Clean Architecture, Reactive Programming, dan Offline-first capabilities.

## 🚀 Fitur
- **Movie & TV Shows:** Menampilkan daftar film dan serial TV populer menggunakan Paging Library.
- **Detail View:** Informasi mendalam tentang setiap film atau serial (deskripsi, rating, tanggal rilis).
- **Favorite System:** Simpan film atau serial favorit Anda untuk akses cepat.
- **Offline Support:** Data yang sudah dimuat akan disimpan secara lokal di database Room.
- **Sharing:** Bagikan informasi film ke aplikasi lain.

## 🏗️ Arsitektur & Pola Desain
Aplikasi ini menggunakan standar **MVVM (Model-View-ViewModel)** dengan **Clean Architecture principles**:
- **Domain Layer:** Berisi UseCase dan Abstraksi Repository (Pure Kotlin).
- **Data Layer:** Implementasi Repository, Remote Data Source (Retrofit), dan Local Data Source (Room).
- **Presentation Layer:** UI (Fragments/Activities) dan ViewModels.

## 🛠️ Teknologi yang Digunakan
- **Bahasa:** [Kotlin](https://kotlinlang.org/)
- **Android Jetpack:**
    - **Room:** Database lokal untuk persistent data.
    - **Paging Library:** Untuk memuat data dalam potongan besar (pagination).
    - **ViewModel & LiveData:** Mengelola data UI secara lifecycle-aware.
    - **View Binding:** Interaksi yang aman dengan elemen UI.
- **Networking:** [Retrofit2](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) (Logging Interceptor).
- **Dependency Injection:** Manual Injection (Injection Pattern).
- **Image Loading:** [Glide](https://github.com/bumptech/glide).
- **Testing:**
    - **JUnit & Mockito:** Untuk Unit Testing pada ViewModel dan Repository.
    - **Espresso:** Untuk Instrumental UI Testing.
    - **Idling Resource:** Sinkronisasi testing dengan background task.

## ⚙️ Cara Menjalankan
1. Clone repositori ini.
2. Dapatkan API KEY dari [TMDB](https://www.themoviedb.org/documentation/api).
3. Masukkan API KEY Anda di file `ApiConfig` atau sesuai konfigurasi di project.
4. Build dan jalankan aplikasi di Android Studio.

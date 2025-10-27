# Mini ERP Java Swing + MySQL

Bu proje, **Java Swing** ile geliştirilmiş basit bir **ERP uygulaması**dır. Personel yönetimi üzerine kuruludur ve **MySQL veritabanı** ile çalışır. CRUD işlemleri (Create, Read, Update, Delete) desteklenir.
---

## 📂 Dosya Yapısı

```
ERP-MySQL/
├─ src/
│  ├─ database/
│  │   └─ DBConnection.java      # Veritabanı bağlantısı
│  ├─ model/
│  │   └─ Personel.java          # Personel model sınıfı
│  ├─ dao/
│  │   └─ PersonelDAO.java       # Veritabanı işlemleri
│  ├─ service/
│  │   └─ PersonelService.java   # DAO ile UI arasındaki servis katmanı
│  ├─ ui/
│  │   ├─ ERPFrame.java          # Ana uygulama frame
│  │   ├─ PersonelPanel.java     # Personel listesi paneli
│  │   └─ PersonelForm.java      # Personel ekleme/düzenleme formu
│  └─ Main.java                  # Program entry point
├─ lib/
│  └─ mysql-connector-java-<version>.jar  # JDBC driver
├─ README.md                     # Proje açıklaması
```
```sql
CREATE DATABASE erpdb;

USE erpdb;

CREATE TABLE personel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ad VARCHAR(50) NOT NULL,
    soyad VARCHAR(50) NOT NULL,
    pozisyon VARCHAR(50),
    maas DECIMAL(10,2),
    giris_tarihi DATE
);
```
---
3. Kullanıcı oluştur (root yerine önerilir):

```sql
CREATE USER 'erpuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON erpdb.* TO 'erpuser'@'localhost';
FLUSH PRIVILEGES;
```

---
## ⚙️ Java Projesi Kurulumu

1. IntelliJ IDEA’da yeni Java Project oluşturun.
2. `src` ve `lib` dizinlerini oluşturun.
3. `lib/` içerisine **mysql-connector-java-x.x.x.jar** dosyasını ekleyin.
4. IntelliJ’de **Project Structure → Libraries → Add JAR** ile JDBC driver ekleyin.

## 🖥️ UI Katmanı

* **ERPFrame.java** → Ana pencere, menü ve personel panelini içerir.
* **PersonelPanel.java** → Personel listesini JTable ile gösterir, ekleme/düzenleme/silme butonları içerir.
* **PersonelForm.java** → Personel ekleme/düzenleme formu.

---
## ⚠️ Notlar

* JDBC URL’de **useSSL=false&serverTimezone=UTC** parametrelerini unutmayın.
* Eğer MySQL uzak sunucu ise `localhost` yerine IP yazın.
* MySQL kullanıcı yetkilerini kontrol edin.


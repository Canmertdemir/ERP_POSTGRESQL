
# 🏢 ERP PostgreSQL - Personel Yönetim Sistemi

Modern ve kullanıcı dostu Enterprise Resource Planning (ERP) uygulaması - Java Swing & PostgreSQL

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Teknolojiler](#️-teknolojiler)
- [Proje Yapısı](#-proje-yapısı)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [Veritabanı Şeması](#-veritabanı-şeması)
- [Yapılandırma](#-yapılandırma)
- [Ekran Görüntüleri](#-ekran-görüntüleri)
- [Sorun Giderme](#-sorun-giderme)
- [Katkıda Bulunma](#-katkıda-bulunma)
- [Lisans](#-lisans)

## ✨ Özellikler

### 🎯 Ana Özellikler
- ✅ **CRUD İşlemleri**: Tam fonksiyonel Create, Read, Update, Delete operasyonları
- 🔄 **Gerçek Zamanlı Güncelleme**: Otomatik veri yenileme ve senkronizasyon
- 🎨 **Modern UI/UX**: Kullanıcı dostu Swing arayüzü
- 💾 **PostgreSQL Entegrasyonu**: Güvenli ve hızlı veritabanı bağlantısı
- 📊 **Tablo Görünümü**: Düzenlenebilir ve filtrelenebilir personel listesi
- 🔒 **Veri Doğrulama**: Form validasyonu ve hata kontrolü
- 💰 **Para Birimi Formatı**: Türk Lirası (₺) ile otomatik formatlama
- 📅 **Tarih Yönetimi**: Türkçe tarih formatı desteği

### 🛡️ Güvenlik ve Performans
- Connection pooling hazır altyapısı
- SQL injection koruması (PreparedStatement)
- Güvenli şifre yönetimi (config dosyası)
- Hata yakalama ve loglama

## 🛠️ Teknolojiler

| Teknoloji | Versiyon | Açıklama |
|-----------|----------|----------|
| **Java** | 11+ | Ana programlama dili |
| **PostgreSQL** | 12+ | İlişkisel veritabanı |
| **JDBC** | 42.7.3 | Veritabanı bağlantı driver'ı |
| **Swing** | Built-in | GUI framework |
| **Maven/Gradle** | Opsiyonel | Bağımlılık yönetimi |

### Kullanılan Kütüphaneler
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

## 📁 Proje Yapısı

```
ERP_POSTGRESQL/
├── 📂 src/
│   ├── 📂 config/
│   │   ├── 📄 db.properties          # Veritabanı yapılandırması
│   │   └── 📄 DBConnection.java      # Bağlantı yöneticisi
│   │
│   ├── 📂 dao/                       # Data Access Objects
│   │   └── 📄 PersonelDAO.java       # CRUD operasyonları
│   │
│   ├── 📂 model/                     # Veri modelleri
│   │   └── 📄 Personel.java          # Personel entity sınıfı
│   │
│   ├── 📂 service/                   # İş mantığı katmanı
│   │   └── 📄 PersonelService.java   # Servis katmanı
│   │
│   ├── 📂 ui/                        # Kullanıcı arayüzü
│   │   ├── 📄 ERPFrame.java          # Ana pencere (Main UI)
│   │   ├── 📄 PersonelForm.java      # Ekleme/Düzenleme formu
│   │   └── 📄 PersonelPanel.java     # Panel komponenti
│   │
│   └── 📂 util/                      # Yardımcı sınıflar
│       ├── 📄 ConfigReader.java      # Config okuyucu
│       └── 📄 Main.java              # Uygulama giriş noktası
│
├── 📂 database/
│   └── 📄 schema.sql                 # Veritabanı şeması
│
├── 📄 .gitignore                     # Git ignore kuralları
├── 📄 README.md                      # Bu dosya
├── 📄 pom.xml                        # Maven yapılandırması
└── 📄 LICENSE                        # Lisans dosyası
```

## 🚀 Kurulum

### 1️⃣ Gereksinimler

Sisteminizde aşağıdaki yazılımlar kurulu olmalıdır:

- ☕ **Java JDK 11** veya üzeri
  ```bash
  java -version
  ```

- 🐘 **PostgreSQL 12+**
  ```bash
  psql --version
  ```

- 📦 **Maven** (Opsiyonel)
  ```bash
  mvn -version
  ```

### 2️⃣ Projeyi Klonlama

```bash
# Repository'i klonla
git clone https://github.com/Canmertdemir/ERP_POSTGRESQL.git

# Proje dizinine gir
cd ERP_POSTGRESQL
```

### 3️⃣ Veritabanı Kurulumu

#### PostgreSQL'e Bağlan
```bash
# Windows
psql -U postgres

# Linux/Mac
sudo -u postgres psql
```

#### Veritabanı ve Tabloları Oluştur

```sql
-- 1. Veritabanı oluştur
CREATE DATABASE "ERPDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Turkish_Turkey.1254'
    LC_CTYPE = 'Turkish_Turkey.1254'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- 2. ERPDB'ye bağlan
\c ERPDB

-- 3. Personel tablosu oluştur
CREATE TABLE personel (
    id SERIAL PRIMARY KEY,
    ad VARCHAR(50) NOT NULL,
    soyad VARCHAR(50) NOT NULL,
    pozisyon VARCHAR(100),
    maas NUMERIC(15, 2) CHECK (maas >= 0),
    giris_tarihi DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_giris_tarihi CHECK (giris_tarihi <= CURRENT_DATE)
);

-- 4. İndeksler oluştur
CREATE INDEX idx_personel_ad_soyad ON personel(ad, soyad);
CREATE INDEX idx_personel_pozisyon ON personel(pozisyon);

-- 5. Otomatik güncelleme trigger'ı
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_personel_updated_at 
    BEFORE UPDATE ON personel 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- 6. Örnek veriler ekle
INSERT INTO personel (ad, soyad, pozisyon, maas, giris_tarihi) VALUES
('Ahmet', 'Yılmaz', 'Yazılım Geliştirici', 15000.00, '2023-01-15'),
('Ayşe', 'Kaya', 'Proje Yöneticisi', 20000.00, '2022-06-10'),
('Mehmet', 'Demir', 'Sistem Analisti', 18000.00, '2023-03-20'),
('Fatma', 'Şahin', 'İnsan Kaynakları Uzmanı', 12000.00, '2022-11-05'),
('Ali', 'Çelik', 'Muhasebe Müdürü', 22000.00, '2021-08-15'),
('Zeynep', 'Arslan', 'Pazarlama Uzmanı', 13500.00, '2023-02-28'),
('Mustafa', 'Öztürk', 'Satış Temsilcisi', 11000.00, '2023-05-12'),
('Elif', 'Kurt', 'Veri Analisti', 16000.00, '2022-09-18');

-- 7. Verileri kontrol et
SELECT * FROM personel ORDER BY id;
```

### 4️⃣ Yapılandırma

`config/db.properties` dosyasını oluşturun:

```properties
# PostgreSQL Veritabanı Ayarları
db.host=localhost
db.port=5432
db.name=ERPDB
db.username=postgres
db.password=YOUR_PASSWORD_HERE

# Bağlantı Havuzu Ayarları
db.pool.minSize=5
db.pool.maxSize=20
db.pool.timeout=30

# Genel Ayarlar
app.name=ERP System
app.version=1.0
app.debug=true
```

⚠️ **ÖNEMLİ**: `YOUR_PASSWORD_HERE` kısmını kendi PostgreSQL şifrenizle değiştirin!

### 5️⃣ PostgreSQL JDBC Driver Kurulumu

#### Maven ile:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

#### Manuel Kurulum:
1. [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/) sayfasından `.jar` dosyasını indirin
2. Projenizin `lib/` klasörüne kopyalayın
3. IDE'nizde classpath'e ekleyin

### 6️⃣ Uygulamayı Çalıştırma

#### IDE ile:
```java
// util/Main.java veya ui/ERPFrame.java'yı çalıştırın
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        ERPFrame frame = new ERPFrame();
        frame.setVisible(true);
    });
}
```

#### Terminal/Komut Satırı:
```bash
# Derleme
javac -d bin -cp "lib/*" src/**/*.java

# Çalıştırma
java -cp "bin:lib/*" ui.ERPFrame

# Maven ile
mvn clean compile
mvn exec:java -Dexec.mainClass="ui.ERPFrame"
```

## 🎮 Kullanım

### Ana Ekran İşlemleri

#### 1. **Personel Listesini Görüntüleme**
- Uygulama açıldığında tüm personel otomatik yüklenir
- Tablo üzerinde sıralama yapabilirsiniz
- Her satır: ID, Ad, Soyad, Pozisyon, Maaş, Giriş Tarihi

#### 2. **Yeni Personel Ekleme** ➕
1. "**➕ Yeni Personel**" butonuna tıklayın
2. Formu doldurun:
   - **Ad** (Zorunlu)
   - **Soyad** (Zorunlu)
   - **Pozisyon** (Opsiyonel)
   - **Maaş** (Opsiyonel, sayısal)
   - **Giriş Tarihi** (YYYY-MM-DD formatında)
3. "**💾 Kaydet**" butonuna tıklayın

#### 3. **Personel Düzenleme** ✏️
1. Tablodan bir personel seçin
2. "**✏️ Düzenle**" butonuna tıklayın
3. Değişiklikleri yapın
4. "**💾 Kaydet**" ile onaylayın

#### 4. **Personel Silme** 🗑️
1. Tablodan bir personel seçin
2. "**🗑️ Sil**" butonuna tıklayın
3. Onay mesajını kabul edin

#### 5. **Verileri Yenileme** 🔄
- "**🔄 Yenile**" butonuna tıklayarak listeyi güncelleyin

### Tarih Formatları

| Kullanım Yeri | Format | Örnek |
|---------------|--------|-------|
| Form Girişi | `YYYY-MM-DD` | 2024-01-15 |
| Tablo Gösterimi | `dd.MM.yyyy` | 15.01.2024 |

### Para Birimi Formatı

Maaş değerleri otomatik olarak formatlanır:
- **Giriş**: `15000` veya `15000.50`
- **Gösterim**: `15,000.50 ₺`

## 🗄️ Veritabanı Şeması

### Personel Tablosu

| Kolon | Tip | Açıklama | Kısıtlamalar |
|-------|-----|----------|--------------|
| `id` | SERIAL | Birincil anahtar | PRIMARY KEY, AUTO_INCREMENT |
| `ad` | VARCHAR(50) | Personel adı | NOT NULL |
| `soyad` | VARCHAR(50) | Personel soyadı | NOT NULL |
| `pozisyon` | VARCHAR(100) | İş pozisyonu | NULL |
| `maas` | NUMERIC(15,2) | Maaş bilgisi | CHECK (maas >= 0) |
| `giris_tarihi` | DATE | İşe giriş tarihi | NOT NULL, DEFAULT CURRENT_DATE |
| `created_at` | TIMESTAMP | Kayıt oluşturma | DEFAULT CURRENT_TIMESTAMP |
| `updated_at` | TIMESTAMP | Son güncelleme | AUTO UPDATE |

### İlişkiler ve İndeksler

```sql
-- Hızlı arama için indeksler
CREATE INDEX idx_personel_ad_soyad ON personel(ad, soyad);
CREATE INDEX idx_personel_pozisyon ON personel(pozisyon);

-- Otomatik güncelleme trigger'ı
CREATE TRIGGER update_personel_updated_at 
    BEFORE UPDATE ON personel 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();
```

## ⚙️ Yapılandırma

### config/db.properties

```properties
# Veritabanı Bağlantı Ayarları
db.host=localhost                    # PostgreSQL sunucu adresi
db.port=5432                         # PostgreSQL port numarası
db.name=ERPDB                        # Veritabanı adı
db.username=postgres                 # Kullanıcı adı
db.password=your_password            # Şifre

# Connection Pool Ayarları
db.pool.minSize=5                    # Minimum bağlantı sayısı
db.pool.maxSize=20                   # Maksimum bağlantı sayısı
db.pool.timeout=30                   # Timeout (saniye)

# Uygulama Ayarları
app.name=ERP System
app.version=1.0
app.debug=true                       # Debug modu
```

### ConfigReader Kullanımı

```java
// Değer okuma
String host = ConfigReader.get("db.host");
String host = ConfigReader.get("db.host", "localhost"); // Varsayılan değer ile

// Integer değer
int timeout = ConfigReader.getInt("db.pool.timeout", 10);

// Boolean değer
boolean debug = ConfigReader.getBoolean("app.debug", false);

// Tüm config'i görüntüle
ConfigReader.printAll();
```

## 📸 Ekran Görüntüleri

### Ana Ekran
```
┌─────────────────────────────────────────────────┐
│        PERSONEL YÖNETİM SİSTEMİ                 │
├─────────────────────────────────────────────────┤
│ ID │ Ad     │ Soyad  │ Pozisyon    │ Maaş      │
├────┼────────┼────────┼─────────────┼───────────┤
│ 1  │ Ahmet  │ Yılmaz │ Developer   │ 15,000 ₺  │
│ 2  │ Ayşe   │ Kaya   │ Manager     │ 20,000 ₺  │
└────┴────────┴────────┴─────────────┴───────────┘
  [🔄 Yenile] [➕ Yeni] [✏️ Düzenle] [🗑️ Sil]
```

## 🐛 Sorun Giderme

### Yaygın Hatalar ve Çözümleri

#### 1. Veritabanı Bağlantı Hatası
```
FATAL: database "ERPDB" does not exist
```
**Çözüm**: Veritabanını oluşturun:
```sql
CREATE DATABASE "ERPDB";
```

#### 2. Driver Bulunamadı
```
PostgreSQL JDBC Driver bulunamadı!
```
**Çözüm**: PostgreSQL JDBC driver'ı classpath'e ekleyin

#### 3. Kimlik Doğrulama Hatası
```
FATAL: password authentication failed
```
**Çözüm**: 
- `db.properties` dosyasındaki şifreyi kontrol edin
- PostgreSQL'de kullanıcı yetkilerini kontrol edin

#### 4. Numeric Overflow
```
ERROR: numeric field overflow
```
**Çözüm**: Maaş alanını genişletin:
```sql
ALTER TABLE personel ALTER COLUMN maas TYPE NUMERIC(15, 2);
```

#### 5. Port Kullanımda
```
Port 5432 is already in use
```
**Çözüm**: PostgreSQL servisini yeniden başlatın:
```bash
# Windows
net stop postgresql-x64-12
net start postgresql-x64-12

# Linux
sudo systemctl restart postgresql
```

### Loglama

Uygulama konsola detaylı loglar yazar:
```
✓ PostgreSQL JDBC Driver yüklendi!
✓ ERPDB veritabanına bağlantı başarılı!
✓ Personel listesi yüklendi. Toplam: 8
```

## 🤝 Katkıda Bulunma

Projeye katkıda bulunmak isterseniz:

1. **Fork** edin
2. Feature branch oluşturun:
   ```bash
   git checkout -b feature/yeni-ozellik
   ```
3. Değişikliklerinizi commit edin:
   ```bash
   git commit -m 'Yeni özellik: ...'
   ```
4. Branch'inizi push edin:
   ```bash
   git push origin feature/yeni-ozellik
   ```
5. **Pull Request** açın

### Geliştirme Kuralları
-  Kod standartlarına uyun
-  Değişikliklerinizi test edin
-  Commit mesajlarını anlamlı yazın
-  Dokümantasyonu güncelleyin

## 🗺️ Yol Haritası

### v1.0 (Mevcut) 
- [x] Personel CRUD işlemleri
- [x] PostgreSQL entegrasyonu
- [x] Modern Swing UI
- [x] Veri doğrulama

### v1.1 (Planlanan) 🔜
- [ ] Departman yönetimi
- [ ] Kullanıcı kimlik doğrulama
- [ ] Rol bazlı yetkilendirme
- [ ] Gelişmiş arama ve filtreleme

### v2.0 (Gelecek) 🚀
- [ ] Raporlama modülü
- [ ] Excel export/import
- [ ] Dashboard ve istatistikler
- [ ] REST API desteği
- [ ] Multi-language support


## Geliştirici

**Öğr. Gör. Canmert Demir**

-  GitHub: [@Canmertdemir](https://github.com/Canmertdemir)
-  Email: canmert.demir@rumeli.edu.tr
## Kaynaklar

- [PostgreSQL Dokumentasyonu](https://www.postgresql.org/docs/)
- [Java JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

---

**Son Güncelleme**: 2024
**Versiyon**: 1.0.0
**Durum**: 🟢 Aktif Geliştirme

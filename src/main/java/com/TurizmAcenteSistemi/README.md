# Turizm Acente Sistemi
Patika Turizm Acentesi işlerini dijital ortamdan yürütebileceği bir yapıya ihtiyaç duymaktadır. Patika ile bu dijital altyapı için anlaştığınızı varsayın.

Patika Turizm Acentesinin çalışma mantığı şu şekildedir : 

Oteller Patika Turizm Acentesine belirli oda rezerv ederler ve bu odaları acentenin satmasını beklerler. Acente bu odaları satarken üstüne kendi hizmet bedelini yüzde veya sabit bir fiyat koyarak müşterilerine satar.

Acente bu işleri kağıt üzerinde ve manuel olarak yapmanın zaman kaybı olduğunu düşünür, işini kolaylaştıracak bir yazılıma ihtiyaç duymaktadır.

Acentenin yazılımdan beklentisi :

## Otel Yönetimi

Acente anlaşmalı olduğu otelleri, konum bilgileri ve diğer özellikleri ile birlikte sistemden yönetmeli. Otel eklenirken Otel Adı, Adres, E-posta, Telefon, Yıldız, Tesis Özellikleri, Pansiyon tipleri gibi diğer tanımlamalar yapılır.

### Sistemde olması beklenen Pansiyon Tipleri :
- Ultra Herşey Dahil
- Herşey Dahil
- Oda Kahvaltı
- Tam Pansiyon
- Yarım Pansiyon
- Sadece Yatak
- Alkol Hariç Full credit
### Sistemde olması beklenen Tesis Özellikleri :
- Ücretsiz Otopark
- Ücretsiz WiFi
- Yüzme Havuzu
- Fitness Center
- Hotel Concierge
- SPA
- 7/24 Oda Servisi

## Oda Yönetimi
Acente otellerden rezerv ettiği odaları sisteme ekler ve bu odalar üzerinden fiyatlandırma sağlar. Otellerde genellikle Single, Double, Suit vb. gibi belirli oda tipleri mevcuttur ve aynı türdeki oda tiplerini sistemde tekrar tekrar eklememek için stok mantığı kullanılmaktadır. Ayrıca odalara ait özelliklerde girilmelidir.

### Sistemde olması beklenen Oda Özellikleri :
- Yatak sayısı dışında ki diğer bilgileri opsiyoneldir.
- Yatak Sayısı
- Televizyon (Var, Yok)
- Minibar (Var ,Yok)
- Oyun Konsolu (Var, Yok)
- Metrekare
- Kasa
- Projeksiyon

### Oda Arama ve Rezervasyon İşlemleri
Acente çalışanı sistem üzerinden girdiği tarih aralığına , Bölgeye veya Otellere ve misafir bilgisine göre oda arayabilmeli.

### Oda Arama Algoritması
Acente gerekli oda arama bilgileri girdikten sonra, sistemde tanımlı olan odaları listeleyebilir.

Odaların listede çıkması için :

- Odaya ait otelin istenilen bölge içinde olmalı.
- İstenilen tarih aralığına göre otelin dönem bilgisi olmalıdır. Örneğin , Giriş Tarihi : 09/06/2021 , Çıkış Tarihi : 12/06/2021 olan bir arama için, otellerin o tarih aralığında bir dönemleri olmalı.
- Eğer otele ait dönem var ise, odanın da ilgili dönemlerde pansiyon tiplerine göre fiyat bilgisi olmalıdır ki müşteriye fiyat bilgisi verilebilsin.
- Odanın stok sayısı 0'dan büyük olmalı.

## Fiyat Hesaplama
Fiyatlar misafir bilgisi , kalınacak gece sayısı ve pansiyon üzerinden hesaplanır.

Bu bilgilere göre;

- İlk önce bölgede ki oteller bulunur.
- Bulunan otele ait stoğu olan odalar bulunur.
- Odaların ilgili tarih aralığında dönem fiyat bilgisi var ise fiyat hesaplaması yapılır.

## Rezervasyon İşlemi
Acente kullanıcısı listelediği otellere ait oda tiplerinden uygun olan pansiyon tipini seçerek rezervasyonu tamamlar.

Rezervasyon tamamlamak için :

- Müşteri iletişim bilgileri
- Misafir Ad Soyad ve T.C. Kimlik Bilgileri

girerek sistem üzerinden satışı tamamlar. Eğer satış tamamlanırsa ilgili odanın stoğu 1 azalmış olur.

## Rezervasyon Listesi
Acente çalışanları sistem üzerinde yapılan rezervasyonları listeleyebilir.

## Üye Yetkileri
- Teknik Gereksinimler
- Projede veritabanı kullanılmalıdır.
- GUI (Arayüz) tasarlanmalı. (Swing, JavaFX vb. kullanabilirsiniz.)
- Örnek resimlerdeki arayüzden bağımsız şekilde tasarlayın.
- Arayüz tasarımı kullanıcı dostu olmalı.
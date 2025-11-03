package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Personel {
    private int id;
    private String ad;
    private String soyad;
    private String pozisyon;
    private BigDecimal maas;
    private LocalDate girisTarihi;

    public Personel() {}

    public Personel(int id, String ad, String soyad, String pozisyon, BigDecimal maas, LocalDate girisTarihi) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.pozisyon = pozisyon;
        this.maas = maas;
        this.girisTarihi = girisTarihi;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getPozisyon() { return pozisyon; }
    public void setPozisyon(String pozisyon) { this.pozisyon = pozisyon; }

    public BigDecimal getMaas() { return maas; }
    public void setMaas(BigDecimal maas) { this.maas = maas; }

    public LocalDate getGirisTarihi() { return girisTarihi; }
    public void setGirisTarihi(LocalDate girisTarihi) { this.girisTarihi = girisTarihi; }
}

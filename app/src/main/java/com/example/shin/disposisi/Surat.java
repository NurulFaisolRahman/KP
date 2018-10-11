package com.example.shin.disposisi;

public class Surat {

    private String nomor_surat,url,surat_dari,tanggal_surat,diterima_tanggal,nomor_agenda,sifat,perihal,status,isi_disposisi,bidang;

    public Surat(String nomor_surat, String url, String surat_dari, String tanggal_surat, String diterima_tanggal, String nomor_agenda, String sifat, String perihal, String status, String isi_diposisi, String bidang) {
        this.nomor_surat = nomor_surat;
        this.url = url;
        this.surat_dari = surat_dari;
        this.tanggal_surat = tanggal_surat;
        this.diterima_tanggal = diterima_tanggal;
        this.nomor_agenda = nomor_agenda;
        this.sifat = sifat;
        this.perihal = perihal;
        this.status = status;
        this.isi_disposisi = isi_diposisi;
        this.bidang = bidang;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public String getUrl() {
        return url;
    }

    public String getSurat_dari() {
        return surat_dari;
    }

    public String getTanggal_surat() {
        return tanggal_surat;
    }

    public String getDiterima_tanggal() {
        return diterima_tanggal;
    }

    public String getNomor_agenda() {
        return nomor_agenda;
    }

    public String getSifat() {
        return sifat;
    }

    public String getPerihal() {
        return perihal;
    }

    public String getStatus() {
        return status;
    }

    public String getIsi_diposisi() {
        return isi_disposisi;
    }

    public String getBidang() {
        return bidang;
    }

}

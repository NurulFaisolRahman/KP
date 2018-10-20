package com.example.shin.disposisi.FileBidang;

public class DataArsipBidangSekretaris {
    private String nomor_surat,status,spk,suk,ikp,aptika,sdtik;

    public DataArsipBidangSekretaris(String nomor_surat, String status, String spk, String suk, String ikp, String aptika, String sdtik) {
        this.nomor_surat = nomor_surat;
        this.status = status;
        this.spk = spk;
        this.suk = suk;
        this.ikp = ikp;
        this.aptika = aptika;
        this.sdtik = sdtik;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public String getStatus() {
        return status;
    }

    public String getSpk() {
        return spk;
    }

    public String getSuk() {
        return suk;
    }

    public String getIkp() {
        return ikp;
    }

    public String getAptika() {
        return aptika;
    }

    public String getSdtik() {
        return sdtik;
    }
}

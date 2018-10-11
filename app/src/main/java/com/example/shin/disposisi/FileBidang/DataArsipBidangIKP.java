package com.example.shin.disposisi.FileBidang;

public class DataArsipBidangIKP {
    private String nomor_surat,status,spkp,spip,spmk;

    public DataArsipBidangIKP(String nomor_surat, String status, String spkp, String spip, String spmk) {
        this.nomor_surat = nomor_surat;
        this.status = status;
        this.spkp = spkp;
        this.spip = spip;
        this.spmk = spmk;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public String getStatus() {
        return status;
    }

    public String getSpkp() {
        return spkp;
    }

    public String getSpip() {
        return spip;
    }

    public String getSpmk() {
        return spmk;
    }
}

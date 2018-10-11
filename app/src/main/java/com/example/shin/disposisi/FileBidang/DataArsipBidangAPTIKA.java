package com.example.shin.disposisi.FileBidang;

public class DataArsipBidangAPTIKA {
    private String nomor_surat,status,skp,spe,sijt;

    public DataArsipBidangAPTIKA(String nomor_surat, String status, String skp, String spe, String sijt) {
        this.nomor_surat = nomor_surat;
        this.status = status;
        this.skp = skp;
        this.spe = spe;
        this.sijt = sijt;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public String getStatus() {
        return status;
    }

    public String getSkp() {
        return skp;
    }

    public String getSpe() {
        return spe;
    }

    public String getSijt() {
        return sijt;
    }
}

package com.example.shin.disposisi.FileBidang;

public class DataArsipBidangSDTIK {
    private String nomor_surat,status,sst,set,sds;

    public DataArsipBidangSDTIK(String nomor_surat, String status, String sst, String set, String sds) {
        this.nomor_surat = nomor_surat;
        this.status = status;
        this.sst = sst;
        this.set = set;
        this.sds = sds;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public String getStatus() {
        return status;
    }

    public String getSst() {
        return sst;
    }

    public String getSet() {
        return set;
    }

    public String getSds() {
        return sds;
    }
}

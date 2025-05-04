package com.example.api.dto;

import com.example.api.entity.Clnt;

public class ClntUpdate {

    private Clnt clntOri;
    private Clnt clntNew;

    public Clnt getClntOri() {
        return clntOri;
    }

    public void setClntOri(Clnt clntOri) {
        this.clntOri = clntOri;
    }

    public Clnt getClntNew() {
        return clntNew;
    }

    public void setClntNew(Clnt clntNew) {
        this.clntNew = clntNew;
    }
}

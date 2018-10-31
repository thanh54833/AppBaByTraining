package com.example.thanh.appbabytraining.main.object;

public class IteamMusic {
    private String name;
    private boolean flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public IteamMusic(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }
}

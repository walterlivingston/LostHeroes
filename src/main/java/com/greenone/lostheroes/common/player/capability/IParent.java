package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.common.deity.Deity;

public interface IParent {
    Deity getParent();
    void setParent(Deity parent_);
    void copy(IParent parentCap);
}
